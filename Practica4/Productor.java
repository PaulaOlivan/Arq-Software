/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 04/05/2023
 * Descripción: fichero que implementa la clase Consumidor para que se puedan
 *              crear consumidores y consumir mensajes de las colas  
 */

import java.rmi.Naming;

public class Productor {
    
    private static int random(){
        return (int) (Math.random() * 100);
    }
    public static void main(String[] args){
        if (args.length < 3 || args.length > 4){
            System.out.println("Bad usage, use: java Productor 'nombreBroker' 'IPPuertoBroker 'nameCola'\n");
        }
        else if (args.length == 3){
            try{
                String brokerName = args[0];
                String brokerIP = args[1];
                String colaName = args[2];
                Broker broker = (Broker) Naming.lookup("//" + brokerIP + "/" + brokerName);
                System.out.println("Conectado al broker " + brokerName + " y listo para enviar mensajes");
                
                // Crear cola
                broker.declarar_cola(colaName, "", "");

                // Enviar mensaje
                int num = random();

                String msg = "Mensaje" + num;
                broker.publicar(colaName, msg);
            }
            catch(Exception e){
                System.err.println("Productor exception:");
                e.printStackTrace();
            }
        }
        else if(args.length == 4){
            try{
                String brokerName = args[0];
                String brokerIP = args[1];
                String cola = args[2];
                Broker broker = (Broker) Naming.lookup("//" + brokerIP + "/" + brokerName);

                System.out.println("Conectado al broker " + brokerName + " y listo para enviar mensajes");

                // Enviar mensaje
                int num = random();

                String msg = "Mensaje" + num;
                broker.publicar(cola, msg);
            }
            catch(Exception e){
                System.err.println("Productor exception:");
                e.printStackTrace();
            }
        }
    }
}
