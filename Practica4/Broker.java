import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Broker extends Remote 
{
    void declarar_cola(String nombre_cola) throws RemoteException;
    String consumir (String nombre_cola, String mensaje) throws RemoteException;
    void publicar (String nombre_cola, String mensaje) throws RemoteException;
}


    