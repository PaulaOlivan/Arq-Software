import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;


public class BrokerImpl  extends UnicastRemoteObject
implements Broker
{
    //Private member variables
    private String nameBroker;
    private String IPBroker;
    private ArrayList<Servidor> servidores;
    private ArrayList<Servicio> servicios;


    //Constructor
    public BrokerImpl() throws RemoteException
    {
        super();
        this.nameBroker = "Broker_771";
        this.IPBroker = "155.210.154.204:32004";
        this.servidores = new ArrayList<Servidor>();
        this.servicios = new ArrayList<Servicio>();
    }

    // Función que devuelve true si y solo si los datos de service y los parámetros introducidos son iguales
    private Boolean sonIguales (String nombreServidor, String nombreServicio, List<Class<?>> lista_param, String tipo_retorno, Servicio service){
        Boolean iguales = false;
        if (service.getNombre().equals(nombreServicio) && service.getServidor().equals(nombreServidor) && 
            service.getParametros().equals(lista_param) && service.getRetorno().equals(tipo_retorno)){
            iguales = true;
        }
        return iguales;
    }

    // Función que devuelve true si y solo si los datos de server y los parámetros introducidos son iguales
    private Boolean sonIguales (String nombreServidor, String IPServidor, Servidor server){
        Boolean iguales = false;
        if (server.getNombre().equals(nombreServidor) && server.getIP().equals(IPServidor)){
            iguales = true;
        }
        return iguales;
    }

    //Public methods of the interface
    public Boolean registrar_servidor(String nombreServidor, String host_remoto_IP_puerto) throws RemoteException
    {   
        Boolean registrado = false;
        //Comprobamos que en la lista de servidores no hay un servidor con igual nombre e igual IP
        for (Servidor server : servidores) 
        {
            if (sonIguales(nombreServidor, host_remoto_IP_puerto, server)) 
            {
                registrado = true;
            }
        }

        if (registrado)
        {
            System.out.println("Servidor " + nombreServidor + " ya registrado en " + host_remoto_IP_puerto + "\n");
            return false;
        }   
        else{
            Servidor nuevoServer = new Servidor(nombreServidor, host_remoto_IP_puerto);
            servidores.add(nuevoServer);
    
            System.out.println("Registrado servidor " + nombreServidor + " en " + host_remoto_IP_puerto + " \n");
            return true;
        }    
    }

    // Funcion que añade un servicio al ArrayList de servicios del broker, si
    // se ha podido añadir el servicio devuelve true, en caso contrario false
    public Boolean alta_servicio(String nombreServidor, String nombreServicio, List<Class<?>> lista_param, String tipo_retorno) throws RemoteException
    {
        Boolean registrado = false;
        //Comprobamos que en la lista de servicios no hay un servicio con igual nombre e igual servidor asociado
        for (Servicio servicio : servicios) 
        {
            if (sonIguales(nombreServidor, nombreServicio, lista_param, tipo_retorno, servicio)) 
            {
                registrado = true;
            }
        }
        if (registrado)
        {
            System.out.println("Servicio " + nombreServicio + " ya registrado en " + nombreServidor + "\n");
            return false;
        }
        else{ //El servicio no existia para ese servidor se añade a la lista de servicios        
            Servicio nuevoServicio = new Servicio(nombreServidor, nombreServicio, lista_param, tipo_retorno);
            servicios.add(nuevoServicio);

            System.out.println("Registrado servicio " + nombreServicio + " en " + nombreServidor);
            System. out.println("Parametros: " + lista_param);
            System. out.println("Tipo de retorno: " + tipo_retorno + "\n");

            return true;
        }
    }

    public String listar_servicios(){
        String lista = "";
        for (Servicio servicio : servicios) {
            lista += servicio.getServicio() + "\n";
        }
        return lista;
    }


    private Class<?>[] obtenerTiposParametros(Object[] parametros) {
    Class<?>[] tipos = new Class<?>[parametros.length];
    for (int i = 0; i < parametros.length; i++) {
        tipos[i] = parametros[i].getClass();
    }
        return tipos;
    }

    public int ejecutar_servicio(String functionName, int a, int b) throws RemoteException
    {
        /* return a+b; */
        try{
            String hostname = "155.210.154.206:32006";
            Mates server = (Mates) Naming.lookup("//" + hostname + "/Mates_771");
    
            System.out.println("Conectando al servidor de calculos matematicos...");
            Method functionOverForm = server.getClass().getMethod(functionName, int.class, int.class);

            // Invoke the method with the specified parameters
             int resultado = (int) functionOverForm.invoke(server, a, b);
            return resultado;
        }
        catch (Exception e){
            System.err.println("Matematicas exception:");
            e.printStackTrace();
            return -1;
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
