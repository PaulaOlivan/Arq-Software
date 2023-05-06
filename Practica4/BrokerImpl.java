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

    public void declarar_cola(String nombre_cola) throws RemoteException{//Versión anónima para clientes
        Cola cola = new Cola(nombre_cola);
        colas.add(cola);
    }  

    public void declarar_cola(String nombre_cola, String nombre_consumidor, String ip) throws RemoteException
    {
        Cola cola = new Cola(nombre_cola);
        colas.add(cola);
        Suscriptor consus = new Suscriptor(nombre_consumidor, ip, nombre_cola);
        consumidores.add(consus);
    }

    // Función que será invocada desde los consumidores para consumir un
    // mensaje de la cola nombre_cola
    public String consumir (String nombre_cola, String mensaje) throws RemoteException
    {
        String message = "";
        for (Cola cola : colas){
            if (cola.nombre.equals(nombre_cola)){
                message = cola.consumeMessage("");
            }
        }
        return message;
    }

    public void publicar (String nombre_cola, String mensaje) throws RemoteException
    {   
        // Crear timer para que cuando pasen 5 mins el mensaje se elimine de la cola
        

        for (int i = 0; i < colas.size(); i++){
            if (colas.get(i).nombre.equals(nombre_cola)){
                colas.get(i).addMessage(mensaje);
            }
        }
        System.out.println("Mensaje publicado");
        System.out.println("Mensaje: " + mensaje);
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