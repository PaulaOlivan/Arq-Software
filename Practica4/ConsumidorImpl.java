/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 12/05/2023
 * Descripción: fichero que implementa la clase Consumidor para que se puedan
 *              crear consumidores y consumir mensajes de las colas  
 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Consumidor 1: Cons_771 || 155.210.154.206:32006
// Consumidor 2: Cons_928 || 155.210.154.206:32007
public class ConsumidorImpl extends UnicastRemoteObject
implements Consumidor{

    private Boolean activo; // True si está procesando mensaje, false si no
    private String nombre;
    private String IP;
    private String nombreCola;

    // Constructor de la clase
    public ConsumidorImpl(String _nombre, String _IP, String _cola) throws RemoteException {
        nombre = _nombre;
        IP = _IP;
        activo = false;
        nombreCola = _cola;
    }
    
    // Función que será invocada por el broker cuando se deba consumir un mensaje
    public void call_back(String brokerName, String brokerIP){
        // Conectar con el broker
        try{

            Broker broker = (Broker) Naming.lookup("//" + brokerIP + "/" + brokerName);

            // Consumir mensaje
            String msg = broker.consumir(this.nombreCola);
            System.out.println("Procesado el mensaje de la cola " + this.nombre + "\n");
            Thread.sleep(4000); //Espera 4 segundos
            System.out.println("El mensaje procesado ha sido: " + msg + "\n");

            // Liberar consumidor
            broker.liberar(nombre, nombreCola);
            System.out.println("El consumidor " + this.nombre + " ha sido liberado\n");
        }
        catch (Exception e){
            System.err.println("Consumer exception:");
            e.printStackTrace();
        }
    }

    // Función que permite a los consumidores consumir el mensaje de la cola a la que están suscritos
    public void consumir(){
        try{
            String brokerIP = "155.210.154.203:32003";
            String brokerName = "Broker_771";
            Broker broker = (Broker) Naming.lookup("//" + brokerIP + "/" + brokerName);

            // Consumir mensaje
            String nombreCola = "Cola_771";
            String msg = broker.consumir(nombreCola);
            System.out.println("Procesado el mensaje de la cola " + nombreCola + "\n");
            Thread.sleep(500); //Espera medio segundo
            System.out.println("El mensaje procesado ha sido: " + msg + "\n");
            broker.liberar(nombre, nombreCola);
            
        }
        catch (Exception e){
            System.err.println("Consumer exception:");
            e.printStackTrace();
        }    
    }

    public static void main (String[] args){//PRIMERO NOMBRE LUEGO IP

        if (args.length != 3){
            System.out.println("Bad usage, use: java Consumidor 'nombreConsumidor' 'IP:PuertoConsumidor' 'nombreCola'\n");
        }
        else{
            try{
                Consumidor consumer = new ConsumidorImpl(args[0], args[1], args[2]);
                System.out.println("Creando servidor..." + args[0]);
                Naming.rebind("//" + args[1] + "/" + args[0], consumer);
                System.out.println("Servidor consumidor listo para recibir callback");

                // Conectar con el broker
                String brokerName = "Broker_771";
                String brokerIP = "155.210.154.203:32003";
                Broker broker = (Broker) Naming.lookup("//" + brokerIP + "/" + brokerName);
                System.out.println("Conectado al broker " + brokerName + " y suscrito a la cola");
                
                // Crear cola
                broker.declarar_cola(args[2], args[0], args[1]);
                
            }
            catch (Exception e){
                System.err.println("Consumer exception, Server could not be created:");
                e.printStackTrace();
            }
        }
    }
}
