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
        this.IPBroker = "155.210.154.203:32004";
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

    // Función que devuelve true si y solo si los datos de server y los parámetros introducidos son iguales
    private Boolean sonIguales (String nombreServidor, String nombreServicio, Servicio service){
        Boolean iguales = false;
        if (service.getNombre().equals(nombreServicio) && service.getServidor().equals(nombreServidor)){
            iguales = true;
        }
        return iguales;
    }

    //Función que devuelve un string con el nombre del servidor que contiene el servicio
    private String getServidor(String nombreServicio){
        String nombreServidor = "";
        for (Servicio servicio : servicios) {
            if (servicio.getNombre().equals(nombreServicio)){
                nombreServidor = servicio.getServidor();
            }
        }
        return nombreServidor;
    }

    //Función que devuelve un string con el nombre del servidor que contiene el servicio
    private String getIpAdress(String nombreServicio){
        String ipServidor = "";
        for (Servidor servidorIt : servidores) {
            if (servidorIt.getNombre().equals(nombreServicio)){
                ipServidor = servidorIt.getIP();
            }
        }
        return ipServidor;
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

    // Funcion que borra un servicio al ArrayList de servicios del broker, si
    // se ha podido borrar el servicio devuelve true, en caso contrario false
    public Boolean baja_servicio(String nombreServidor, String nombreServicio){
        Boolean borrado = false;
        //Comprobamos que en la lista de servicios hay un servicio con igual nombre e igual servidor asociado
        for (Servicio servicio : servicios) 
        {
            if (sonIguales(nombreServidor, nombreServicio, servicio)) 
            {
                borrado = true;
                servicios.remove(servicio);
                break;
            }
        }
        if (borrado)
        {
            System.out.println("Servicio " + nombreServicio + " borrado de " + nombreServidor + "\n");
            return true;
        }
        else{
            System.out.println("Servicio " + nombreServicio + " no encontrado en " + nombreServidor + "\n");
            return false;
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

    public String ejecutar_servicio(String functionName, String a, String b, String c, String d, String f) throws RemoteException
    {
        try{

            String servername = getServidor(functionName);
            String hostname = getIpAdress(servername);
            Mates server = (Mates) Naming.lookup("//" + hostname + "/" + servername);
    
            System.out.println("Conectando al servidor corresponciente");

            //Probamos a ver que da ^
            //hay un chat en el boton de liveshare
            // Get the method by passing the name and parameter class as arguments

            int a_ = Integer.parseInt(a);
            int b_ = Integer.parseInt(b);
            int c_ = Integer.parseInt(c);
            int d_ = Integer.parseInt(d);
            int f_ = Integer.parseInt(f);
            int[] args = {a_, b_, c_, d_, f_};
            Object trueInt = Integer.valueOf(1);
            Object trueString = "truth";
            ArrayList<Object> argsObjList = new ArrayList<>();

            // Recorre los argumentos y agrega los que no sean nulos a una lista de objetos
            for (int arg : args) {
                if (arg != 0) {
                    argsObjList.add(arg);
                }
            }

            Object[] argsObj = argsObjList.toArray();

            Class<?>[] argsType = new Class[argsObj.length];

            // Recorre los objetos de argumentos y asigna sus clases a la matriz de clases de argumentos
            for (int i = 0; i < argsObj.length; i++) {
                if (argsObj[i] == null) {
                    argsType[i] = null;
                } else {
                    
                    if( argsObj[i].getClass() == trueInt.getClass()){
                        argsType[i] = int.class;
                    }
                    else if( argsObj[i].getClass() == trueString.getClass()){
                        argsType[i] = String.class;
                    }
                    else{
                        System.out.println("I am error\n");
                    }
                }
            }
                //Mostramos los valores de argsType
                for (int i = 0; i < argsType.length; i++) {
                    System.out.println("argsType[" + i + "] = " + argsType[i]);
                }

            // Obtiene el método correspondiente y lo invoca con los argumentos proporcionados
            Method functionOverForm = server.getClass().getMethod(functionName, argsType);
            // Mostramos por pantalla el servicio que se va a ejecutar
            System.out.println("Ejecutando servicio: " + functionName + " con los parametros: " + argsObjList + "\n");
            int resultado = (int) functionOverForm.invoke(server, argsObj);
            return String.valueOf(resultado);

        }
        catch (ClassCastException e){//Falla al usar ints, empleamos strings
            try{
                String servername = getServidor(functionName);
                String hostname = getIpAdress(servername);
                Cadenas server = (Cadenas) Naming.lookup("//" + hostname + "/" + servername);
        
                System.out.println("Conectando al servidor corresponciente");

                //Probamos a ver que da ^
                //hay un chat en el boton de liveshare
                // Get the method by passing the name and parameter class as arguments

                
                String[] args = {a, b, c, d, f};
                Object trueInt = Integer.valueOf(1);
                Object trueString = "truth";
                ArrayList<Object> argsObjList = new ArrayList<>();

                // Recorre los argumentos y agrega los que no sean nulos a una lista de objetos
                for (String arg : args) {
                    if (!arg.equals("0")) {
                        argsObjList.add(arg);
                    }
                }

                Object[] argsObj = argsObjList.toArray();

                Class<?>[] argsType = new Class[argsObj.length];

                // Recorre los objetos de argumentos y asigna sus clases a la matriz de clases de argumentos
                for (int i = 0; i < argsObj.length; i++) {
                    if (argsObj[i] == null) {
                        argsType[i] = null;
                    } else {
                        
                        if( argsObj[i].getClass() == trueInt.getClass()){
                            argsType[i] = int.class;
                        }
                        else if( argsObj[i].getClass() == trueString.getClass()){
                            argsType[i] = String.class;
                        }
                        else{
                            System.out.println("I am error\n");
                        }
                    }
                }
                    //Mostramos los valores de argsType
                    for (int i = 0; i < argsType.length; i++) {
                        System.out.println("argsType[" + i + "] = " + argsType[i]);
                    }

                // Obtiene el método correspondiente y lo invoca con los argumentos proporcionados
                Method functionOverForm = server.getClass().getMethod(functionName, argsType);
                // Mostramos por pantalla el servicio que se va a ejecutar
                System.out.println("Ejecutando servicio: " + functionName + " con los parametros: " + argsObjList);
                String resultado = (String) functionOverForm.invoke(server, argsObj);
                return String.valueOf(resultado);
            }
            catch (Exception ee){
                System.err.println("Servidor exception:");
                ee.printStackTrace();
                return String.valueOf(-1);
            }    
        }
        catch (Exception e){
            System.err.println("Servidor exception:");
            e.printStackTrace();
            return String.valueOf(-1);
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
