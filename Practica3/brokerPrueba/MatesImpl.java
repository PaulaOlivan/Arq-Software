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
    public int suma(int a, int b) throws RemoteException{
        return a + b;
    }

    public int resta(int a, int b) throws RemoteException{
        return a - b;
    }

    public int multiplicar(int a, int b) throws RemoteException{
        return a * b;
    }

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

            String brokername = "155.210.154.204:32004";
            Broker server = (Broker) Naming.lookup("//" + brokername + "/Broker_771");
    
            System.out.println("Registrando servidor...");
            server.registrar_servidor("Mates_771", "155.210.154.206:32006");
            System.out.println("Servidor registrado!");

            System.out.println("Registrando servicios...");
            List<String> lista_param = new ArrayList<>();
            lista_param.add("int");
            lista_param.add("int");
            server.alta_servicio("Matematicas", "suma", lista_param, "int");

            System.out.println("Servicios registrados!");
        }
        catch (Exception e){
            System.err.println("Matematicas exception:");
            e.printStackTrace();
        }
    }
}
