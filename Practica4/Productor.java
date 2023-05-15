/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 04/05/2023
 * Descripción: fichero que implementa la clase Productor para que se puedan
 *              crear productores de mensajes y añadir mensajes a las colas  
 */

import java.rmi.Naming;

public class Productor {
    
    // Función privada para mandar un mensaje aleatorio a una cola
    private static int random(){
        return (int) (Math.random() * 100);
    }
    public static void main(String[] args){

        // En caso de pasar un número de argumentos incorrecto se muestra un mensaje de error con el modo de
        // uso correcto y se finalizará el programa.
        if (args.length < 3 || args.length > 4){
            System.out.println("Bad usage, use: java Productor 'nombreBroker' 'IPPuertoBroker 'nameCola'\n");
        }
        // Si se ha introducido el nombre del broker, su IP y el nombre de la cola únicamente se creará la cola
        // y se publicará un mensaje en dicha cola. Si se ha pasado el nombre de una cola que ya existe en el
        // broker se publicará un mensaje en dicha cola pero no se creará una nueva.
        else if (args.length == 3){
            try{
                String brokerName = args[0];
                String brokerIP = args[1];
                String colaName = args[2];
                Broker broker = (Broker) Naming.lookup("//" + brokerIP + "/" + brokerName);
                System.out.println("Conectado al broker " + brokerName + " y listo para enviar mensajes");
                
                // Crear cola
                broker.declarar_cola(colaName);

                // Enviar mensaje
                int num = random();

                String msg = "Mensaje" + num;
                System.out.println("Enviando mensaje: " + msg + " a la cola " + colaName + "...");
                broker.publicar(colaName, msg, false);
            }
            catch(Exception e){
                System.err.println("Productor exception:");
                e.printStackTrace();
            }
        }
        // Si se le pasan cuatro argumentos se asume que se quiere enviar un mensaje a una cola ya creada
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
                broker.publicar(cola, msg, false);
            }
            catch(Exception e){
                System.err.println("Productor exception:");
                e.printStackTrace();
            }
        }
    }
}
