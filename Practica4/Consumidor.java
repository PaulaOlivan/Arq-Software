/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 04/05/2023
 * Descripción: fichero que implementa la clase Consumidor para que se puedan
 *              crear consumidores y consumir mensajes de las colas  
 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Consumidor 1: Cons_771 || 155.210.154.206:32006
// Consumidor 2: Cons_928 || 155.210.154.206:32007
public class Consumidor extends UnicastRemoteObject{
    private Boolean activo; // True si está procesando mensaje, false si no
    private String nombre;
    private String IP;

    public Consumidor(String _nombre, String _IP) throws RemoteException {
        nombre = _nombre;
        IP = _IP;
        activo = false;
    }
    
    // Función que será invocada por el broker cuando se deba consumir un mensaje
    public void call_back(){
        // Conectar con el broker
        try{
            String brokerName = "155.210.154.203:32003";
            Broker broker = (Broker) Naming.lookup("//" + brokerName + "/Broker_771");

            // Consumir mensaje
            StringBuilder msg = new StringBuilder();
            broker.consumir(nombre, msg);
            System.out.println("Procesado el mensaje de la cola " + nombre + "\n");
            Thread.sleep(500); //Espera medio segundo
            System.out.println("El mensaje procesado ha sido: " + msg + "\n");
            
        }
        catch (Exception e){
            System.err.println("Consumer exception:");
            e.printStackTrace();
        }
    }

    public void consumir(){
        try{
            String brokerName = "155.210.154.203:32003";
            Broker broker = (Broker) Naming.lookup("//" + brokerName + "/Broker_771");

            // Consumir mensaje
            String nombreCola = "Cola_771";
            StringBuilder msg = new StringBuilder();
            broker.consumir(nombreCola, msg);
            System.out.println("Procesado el mensaje de la cola " + nombreCola + "\n");
            Thread.sleep(500); //Espera medio segundo
            System.out.println("El mensaje procesado ha sido: " + msg + "\n");
            
        }
        catch (Exception e){
            System.err.println("Consumer exception:");
            e.printStackTrace();
        }    
    }

    public static void main (String[] args){//PRIMERO NOMBRE LUEGO IP

        if (args.length != 2){
            System.out.println("Bad usage, use: java Consumidor 'nombre' 'IP'\n");
        }
        else{
            try{
                Consumidor consumer = new Consumidor(args[0], args[1]);
                System.out.println("Creando servidor..." + consumer.nombre);
                Naming.rebind("//" + consumer.IP + "/" + consumer.nombre, consumer);
                System.out.println("Servidor consumidor listo para recibir callback");

                // TODO - revisar para que no se quede esperando como un servidor y pueda hacer otras cosas

                consumer.consumir();
                
            }
            catch (Exception e){
                System.err.println("Consumer exception, Server could not be created:");
                e.printStackTrace();
            }
        }
    }
}
