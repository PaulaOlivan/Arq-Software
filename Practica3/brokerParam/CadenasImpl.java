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

 public class CadenasImpl extends UnicastRemoteObject
 implements Cadenas
 {
    //Private member variables
    private String nombreServidor;
    private String IPServidor;

    // Constructor de la clase
    public CadenasImpl() throws RemoteException{
        super();
        nombreServidor = "Cadenas_771";
        IPServidor = "155.210.154.206:32007";
    }

    // Función que concatena dos cadenas de texto
    public String concatenar(String a, String b) throws RemoteException{
        return a + b;
    }

    // Función que invierte una cadena de texto
    public String invertir(String a) throws RemoteException{
        String invertida = "";
        for(int i = a.length() - 1; i >= 0; i--){
            invertida = invertida + a.charAt(i);
        }
        return invertida;
    }

    // Función que devuelve una cadena de texto en mayúsculas
    public String mayusculas(String a) throws RemoteException{
        return a.toUpperCase();
    }

    // Función que devuelve una cadena de texto en minúsculas
    public String minusculas(String a) throws RemoteException{
        return a.toLowerCase();
    }

    public static void main (String[] args){
        try{
            //Nos creamos como servidor
            CadenasImpl obj = new CadenasImpl();
            System.out.println("Creando servidor..." + obj.nombreServidor);
            //Registramos el objeto remoto
            Naming.rebind("//" + obj.IPServidor + "/" + obj.nombreServidor, obj);
            System.out.println("Servidor de operaciones de cadenas listo para recibir operaciones");

            String brokername = "155.210.154.203:32003";
            Broker server = (Broker) Naming.lookup("//" + brokername + "/Broker_771");

            if (args.length == 0){
            
                System.out.println("Registrando servidor...");
                server.registrar_servidor(obj.nombreServidor, obj.IPServidor);
                System.out.println("Servidor registrado!");
    
    
                System.out.println("Registrando servicios...");
                List<Class<?>> lista_param = new ArrayList<>();
                lista_param.add(String.class);
    
                Boolean anadido = false;
                anadido = server.alta_servicio(obj.nombreServidor, "invertir", lista_param, "String");
                if (anadido == true){
                    System.out.println("Servicio invertir registrado!");
                } 
                else{
                    System.out.println("Servicio invertir no registrado!");
                }
                anadido = server.alta_servicio(obj.nombreServidor, "mayusculas", lista_param, "String");
                if (anadido == true){
                    System.out.println("Servicio mayusculas registrado!");
                } 
                else{
                    System.out.println("Servicio mayusculas no registrado!");
                }
    
                lista_param.add(String.class);
                anadido = server.alta_servicio(obj.nombreServidor, "concatenar", lista_param, "String");
                if (anadido == true){
                    System.out.println("Servicio concatenar registrado!");
                } 
                else{
                    System.out.println("Servicio concatenar no registrado!");
                }
    
                System.out.println("\nPeticiones de añadir servicios al broker realizadas!");
            }
            else if (args.length == 1){
                System.out.println("Dando de baja el servicio invertir...");
                Boolean borrado = server.baja_servicio(obj.nombreServidor, "invertir");
                if (borrado == true)
                    System.out.println("Servicio invertir dado de baja!");
                else{
                    System.out.println("Servicio invertir no dado de baja");
                }

                List<Class<?>> lista_param = new ArrayList<>();
                lista_param.add(String.class);
                Boolean anadido = server.alta_servicio(obj.nombreServidor, "minusculas", lista_param, "String");
                if (anadido == true){
                    System.out.println("Servicio minusculas registrado!");
                } 
                else{
                    System.out.println("Servicio minusculas no registrado!");
                }
            }
        }
        catch (Exception e){
            System.err.println("Cadenas exception:");
            e.printStackTrace();
        }
    }
 }
