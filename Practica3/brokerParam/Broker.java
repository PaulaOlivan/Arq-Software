
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Broker extends Remote
{
    // métodos de la interface
    Boolean registrar_servidor(String nombreServidor, String host_remoto_IP_puerto) throws RemoteException;
    Boolean alta_servicio(String nombreServidor, String nombreServicio, List<Class<?>> lista_param, String tipo_retorno) throws RemoteException;
    String listar_servicios() throws RemoteException;
    Boolean baja_servicio(String nombreServidor, String nombreServicio) throws RemoteException;

    String ejecutar_servicio(String functionName, String a, String b, String c, String d, String e) throws RemoteException;
}