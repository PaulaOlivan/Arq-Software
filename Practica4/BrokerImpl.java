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
        this.nameBroker = "Broker_771";
        this.IPBRoker = "155.210.154.203:32003";
        this.colas = new ArrayList<Cola>();
        this.consumidores = new ArrayList<Suscriptor>();
    }


    // Función que espera 15 segundos a que un mensaje sea consumido, si pasado
    // ese tiempo no ha sido consumido, lo elimina de la cola
    private void caducado(String nombre_cola, String mensaje){
        try {
            Thread.sleep(300000); // Esperar 5 minutos hasta que el mensaje caduque
            for (Cola cola : colas){
                if (cola.nombre.equals(nombre_cola)){
                    Boolean consumido = cola.deleteMessage(mensaje);
                    if (!consumido){
                        System.out.println("El mensaje " + mensaje + " no está en la cola " + nombre_cola);
                    }
                    else{
                        System.out.println("El mensaje " + mensaje + " se ha sido caducado");
                    }
                }
            }
        } catch (InterruptedException e) {
            // Manejar la excepción
            System.out.println("Error en la espera");
        }
    }


    public void declarar_cola(String nombre_cola) throws RemoteException{//Versión anónima para clientes
        Cola cola = new Cola(nombre_cola);
        colas.add(cola);
    }  

    public void declarar_cola(String nombre_cola, String nombre_consumidor, String ip) throws RemoteException
    {
        
        Boolean existe = false;

        for (int i = 0; i < colas.size(); i++){
            if (colas.get(i).nombre.equals(nombre_cola)){
                existe = true;
            }
        }

        if (!existe) {
            Cola cola = new Cola(nombre_cola);
            colas.add(cola);
            Suscriptor consus = new Suscriptor(nombre_consumidor, ip, nombre_cola);
        }

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


    public void publicar (String nombre_cola, String mensaje) throws RemoteException
    {   
        // Crear timer para que cuando pasen 5 mins el mensaje se elimine de la cola 

        System.out.println("Existen un total de " + colas.size() + " colas");
        for (int i = 0; i < colas.size(); i++){
            if (colas.get(i).nombre.equals(nombre_cola)){
                colas.get(i).addMessage(mensaje);
                System.out.println("El mensaje añadido a la cola " + nombre_cola + " es:" + mensaje);
            }
        }
        // LLamar a la función caducado para que el mensaje pueda ser eliminado
        // si pasa un tiempo determinado
        new Thread(new Runnable(){

            public void run(){
                caducado(nombre_cola, mensaje);
            }  

        }).start();

        System.out.println("Mensajes en la cola " + nombre_cola + ":");
        for (int i = 0; i < colas.size(); i++){
            if (colas.get(i).nombre.equals(nombre_cola)){
                for (int j = 0; j < colas.get(i).mensajes.size(); j++){
                    System.out.println(colas.get(i).mensajes.get(j));
                }
            }
        }
    }

    public static void main(String args[]) {

        try{
            BrokerImpl Broke = new BrokerImpl();
            System.out.println("Broker creado\n");

            Naming.rebind("//" + Broke.IPBRoker + "/" + Broke.nameBroker, Broke);
        }
        catch (Exception e){
            System.err.println(e);
        }
    }
}