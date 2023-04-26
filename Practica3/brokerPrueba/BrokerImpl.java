import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

public class BrokerImpl  extends UnicastRemoteObject
implements Broker
{
    //Private member variables
    private String nameBroker;
    private String IPBroker;
    private String nameServer;
    private String IPServer;
    private String nameService;
    private List<String> listParam;
    private String typeReturn;


    //Constructor
    public BrokerImpl() throws RemoteException
    {
        super();
        this.nameBroker = "Broker_771";
        this.IPBroker = "155.210.154.204:32004";
    }

    //Public methods of the interface
    public void registrar_servidor(String nombreServidor, String host_remoto_IP_puerto) throws RemoteException
    {
        nameServer = nombreServidor;
        IPServer = host_remoto_IP_puerto;

        System.out.println("Registrado servidor " + nombreServidor + " en " + host_remoto_IP_puerto);
    }

    public void alta_servicio(String nombreServidor, String nombreServicio, List<String> lista_param, String tipo_retorno) throws RemoteException
    {
        nameService = nombreServicio;
        listParam = lista_param;
        typeReturn = tipo_retorno;

        System.out.println("Registrado servicio " + nombreServicio + " en " + nombreServidor);
        System. out.println("Parametros: " + lista_param);
        System. out.println("Tipo de retorno: " + tipo_retorno);
    }

    public void lanzarSuma() throws RemoteException
    {
        try{
            String hostname = "155.210.154.206:32006";
            Mates server = (Mates) Naming.lookup("//" + hostname + "/Mates_771");
    
            System.out.println("Conectando al servidor de calculos matematicos...");

            int resultado = server.suma();
            System.out.println("El resultado de la suma es: " + resultado);
        }
        catch (Exception e){
            System.err.println("Matematicas exception:");
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        try
        {
            // Crear objeto remoto
            BrokerImpl obj = new BrokerImpl();
            System.out.println("Creado!");
            //Registrar el objeto remoto
            Naming.rebind("//" + obj.IPBroker + "/" + obj.nameBroker, obj);
            System.out.println("Broker listo para que clientes y servidores se conecten!");
            
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}
