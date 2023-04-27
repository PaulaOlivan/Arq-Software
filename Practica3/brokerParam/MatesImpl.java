/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 21/02/2023
 * Descripción: fichero que implementa la clase Cliente para que se reconozca
 *              como un objeto de tipo Cliente en el broker RMI.
 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List; 

public class MatesImpl extends UnicastRemoteObject
implements Mates
{
    //Private member variables
    private String nombreServidor;
    private String IPServidor;

    // Constructor de la clase
    public MatesImpl() throws RemoteException{
        super();
        nombreServidor = "Mates_771";
        IPServidor = "155.210.154.206:32006";
    }  

    // Función que devuelve la suma de dos números
    public int suma(int a, int b, int c) throws RemoteException{
        return a + b + c;
    }

    // Función que devuelve la resta de dos números
    public int resta(int a, int b) throws RemoteException{
        return a - b;
    }

    // Función que devuelve la multiplicación de dos números
    public int multiplicar(int a, int b) throws RemoteException{
        return a * b;
    }

    // Función que devuelve la división de dos números
    public int dividir(int a, int b) throws RemoteException{
        return a / b;
    }

    public static void main (String[] args){
        try{
            //Nos creamos como servidor
            MatesImpl obj = new MatesImpl();
            System.out.println("Creando servidor..." + obj.nombreServidor);
            //Registramos el objeto remoto
            Naming.rebind("//" + obj.IPServidor + "/" + obj.nombreServidor, obj);
            System.out.println("Servidor de operaciones matemáticas listo para recibir operaciones");

            String brokername = "155.210.154.203:32004";
            Broker server = (Broker) Naming.lookup("//" + brokername + "/Broker_771");
    
            System.out.println("Registrando servidor...");
            server.registrar_servidor(obj.nombreServidor, obj.IPServidor);
            System.out.println("Servidor registrado!");

            if (args.length == 0){
                System.out.println("Registrando servicios...");
            List<Class<?>> lista_param = new ArrayList<>();
            lista_param.add(int.class);
            lista_param.add(int.class);

            Boolean anadido = false;
            anadido = server.alta_servicio(obj.nombreServidor, "resta", lista_param, "int");
            if (anadido == true){
                System.out.println("Servicio resta registrado!");
            } 
            else{
                System.out.println("Servicio resta no registrado!");
            }
            anadido = server.alta_servicio(obj.nombreServidor, "multiplicacion", lista_param, "int");
            if (anadido == true){
                System.out.println("Servicio multiplicacion registrado!");
            } 
            else{
                System.out.println("Servicio multiplicacion no registrado!");
            }


            lista_param.add(int.class);
            anadido = server.alta_servicio(obj.nombreServidor, "suma", lista_param, "int");
            if (anadido == true){
                System.out.println("Servicio suma registrado!");
            } 
            else{
                System.out.println("Servicio suma no registrado!");
            }
            anadido = server.alta_servicio(obj.nombreServidor, "suma", lista_param, "int");
            if (anadido == true){
                System.out.println("Servicio suma registrado!");
            } 
            else{
                System.out.println("Servicio suma no registrado!");
            }

            System.out.println("\nPeticiones de añadir servicios al broker realizadas!");
            }
            
            // Eliminamos una función y añadimos una nueva
            else if (args.length == 1){
                System.out.println("Dando de baja el servicio resta...");
                Boolean borrado = server.baja_servicio(obj.nombreServidor, "resta");
                if (borrado == true)
                    System.out.println("Servicio resta dado de baja!");
                else{
                    System.out.println("Servicio resta no dado de baja");
                }

                List<Class<?>> lista_param = new ArrayList<>();
                lista_param.add(int.class);
                lista_param.add(int.class);
                Boolean anadido = server.alta_servicio(obj.nombreServidor, "division", lista_param, "int");
                if (anadido == true){
                    System.out.println("Servicio division registrado!");
                } 
                else{
                    System.out.println("Servicio division no registrado!");
                }
            }
        }
        catch (Exception e){
            System.err.println("Mates exception:");
            e.printStackTrace();
        }
    }
}
