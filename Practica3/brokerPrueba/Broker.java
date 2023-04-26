
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Broker extends Remote
{
    // métodos de la interface
    void registrar_servidor(String nombreServidor, String host_remoto_IP_puerto) throws RemoteException;
    void alta_servicio(String nombreServidor, String nombreServicio, List<String> lista_param, String tipo_retorno) throws RemoteException;
}