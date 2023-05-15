/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 04/05/2023
 * Descripción: fichero que implementa la interfaz Broker para que se pueda crear
 *              el broker con una API publica pero sin mostrar como se implementan
 *              las funciones. Se permiten crear colas y consumir y publicar mensajes
 */

import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class BrokerImpl extends UnicastRemoteObject
implements Broker
{
    private ArrayList<Cola> colas = new ArrayList<Cola>();
    private ArrayList<Suscriptor> consumidores = new ArrayList<Suscriptor>();
    private Integer CCount = 0;
    private FileOutputStream permanencia;
    private PrintWriter escritor;
    private String nameBroker;
    private String IPBRoker;

    public BrokerImpl() throws RemoteException
    {
        super();
        this.nameBroker = "Broker_771"; // Nombre del broker por defecto
        this.IPBRoker = "155.210.154.203:32003"; // IP:Puerto del broker por defecto
        this.colas = new ArrayList<Cola>();
        this.consumidores = new ArrayList<Suscriptor>();
    }


    // Función que espera 5 minutos a que un mensaje sea consumido, si pasado
    // ese tiempo no ha sido consumido, lo elimina de la cola. Si el mensaje
    // ya había sido consumidor muestra un mensaje de error por pantalla
    private void caducado(String nombre_cola, String mensaje){
        try {
            Thread.sleep(300000); // Esperar 5 minutos hasta que el mensaje caduque
            for (Cola cola : colas){
                if (cola.nombre.equals(nombre_cola)){
                    Boolean consumido = cola.deleteMessage(mensaje);

                    if (!consumido){ // El mensaje había sido consumido por un consumidor
                        System.out.println("El mensaje " + mensaje + " no está en la cola " + nombre_cola);
                    }
                    else{ // El mensaje se ha caducado dentro de la cola
                        System.out.println("El mensaje " + mensaje + " se ha sido caducado");
                    }
                }
            }
        } catch (InterruptedException e) {
            // Manejar la excepción
            System.out.println("Error en la espera");
        }
    }


    // Función que implementa el patrón Singleton para localizar si una cola 
    // nombre_cola existe dentro de Broker de mensajes
    private Boolean existeCola(String nombre_cola){
        Boolean existe = false;
        for (Cola cola : colas){
            if (cola.nombre.equals(nombre_cola)){
                existe = true;
            }
        }
        return existe;
    }


    // Función que será invocada desde los productores para crear una nueva cola.
    // Si la cola ya existe no se hará nada
    public void declarar_cola(String nombre_cola) throws RemoteException{//Versión anónima para productores
        
        if (!existeCola(nombre_cola)) { // Si no existe la cola se crea
            Cola cola = new Cola(nombre_cola);
            colas.add(cola);   
        }
    }  

    // Función que será invocada desde los consumidores para crear una nueva cola.
    // Si la cola ya existe no se creará de nuevo, solo se añadirá el consumidor
    // como suscriptor dentro de la cola que buscaba crear.
    public void declarar_cola(String nombre_cola, String nombre_consumidor, String ip) throws RemoteException
    {

        if (!existeCola(nombre_cola)) { // Si no existe la cola se crea
            Cola cola = new Cola(nombre_cola);
            colas.add(cola);
            
        }

        // Suscribimos al consumidor a la cola para poder llamarle con callback
        Suscriptor consus = new Suscriptor(nombre_consumidor, ip, nombre_cola);
        for (Cola cola : colas){
            if (cola.nombre.equals(nombre_cola)){
                cola.addConsumer(nombre_consumidor, ip);
            }
        }
        System.out.println("El consumidor " + nombre_consumidor + " se ha suscrito a la cola " + nombre_cola);

    }

    // Función que será invocada desde los consumidores para consumir un
    // mensaje de la cola nombre_cola
    public String consumir (String nombre_cola) throws RemoteException
    {
        String message = "";
        for (Cola cola : colas){
            if (cola.nombre.equals(nombre_cola)){
                message = cola.consumeMessage();
            }
        }
        return message;
    }

    //Muestra que el consumer esta libre y listo para consumir otro mensaje
    public void liberar (String consumer, String nombreCola) throws RemoteException{
        for (Cola cola : colas){
            if (cola.nombre.equals(nombreCola)){
                cola.liberarConsumidor(consumer);

                System.out.println("El consumidor " + consumer + " puede recibir mensajes de la cola " + nombreCola);
            }
        }
    }

    // Función que será invocada desde los productores con el objetivo de añadir un
    // mensaje a la cola nombre_cola. Si el booleano durable es falso el mensaje
    // poseerá una caducidad de 5 minutos, si es true el mensaje no caducará y 
    // tendrá persistencia en memoria.
    public void publicar (String nombre_cola, String mensaje, Boolean durable) throws RemoteException
    {   
        System.out.println("Existen un total de " + colas.size() + " colas");
        for (int i = 0; i < colas.size(); i++){
            if (colas.get(i).nombre.equals(nombre_cola)){
                colas.get(i).addMessage(mensaje);
                System.out.println("El mensaje añadido a la cola " + nombre_cola + " es:" + mensaje);

                // Si no hay consumidores suscritos a la cola, el mensaje se eliminará de la misma
                if (colas.get(i).getNumConsumidores() == 0){ 
                    System.out.println("No hay consumidores suscritos a la cola " + nombre_cola);
                    colas.get(i).deleteMessage(mensaje);
                }
            }   
        }

        if (durable == false){
            // LLamar a la función caducado para que el mensaje caduque
            new Thread(new Runnable(){

                public void run(){
                    caducado(nombre_cola, mensaje);
                }  

            }).start();
        }

        // Numeramos los mensajes que hay en la cola actualizada
        System.out.println("Mensajes en la cola " + nombre_cola + ":");
        for (int i = 0; i < colas.size(); i++){
            if (colas.get(i).nombre.equals(nombre_cola)){
                for (int j = 0; j < colas.get(i).mensajes.size(); j++){
                    System.out.println(colas.get(i).mensajes.get(j));
                }
            }
        }

        //Llamamos al consumidor al que le toca procesar el mensaje con callback
        String[] datosConsumidor = new String[2];
        for (Cola cola : colas){
            if (cola.nombre.equals(nombre_cola)){
                datosConsumidor = cola.getConsumidor();
            }
        }
        
        try{

            String consumerName = datosConsumidor[0];
            String consumerIP = datosConsumidor[1];

            System.out.println("Llamando en la dirección " + consumerIP + " al consumer " + consumerName);
            Consumidor consumer = (Consumidor) Naming.lookup("//" + consumerIP + "/" + consumerName);
            System.out.println("Se ha conectado con el servidor correspondiente");
            
            consumer.call_back(this.nameBroker, this.IPBRoker);
            System.out.println("Se ha llamado al consumidor " + datosConsumidor[0] + " para que consuma el mensaje.");
        }
        catch (Exception e){
            System.err.println("Consumer exception:");
            e.printStackTrace();
        }     
    }

    public static void main(String args[]) {

        try{
            // Inicializamos el broker como un servidor para que pueda ser llamado
            BrokerImpl Broke = new BrokerImpl();
            System.out.println("Broker creado\n");

            Naming.rebind("//" + Broke.IPBRoker + "/" + Broke.nameBroker, Broke);
        }
        catch (Exception e){
            // Si alguna función anterior da error lo capturamos y mostramos por pantalla
            System.err.println(e);
        }
    }
}