import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Broker extends Remote 
{
    public void declarar_cola(String nombre_cola) throws RemoteException; //Para productores
    void declarar_cola(String nombre_cola, String nombre_consumidor, String ip) throws RemoteException; //Para consumidores
    String consumir (String nombre_cola) throws RemoteException;
    void publicar (String nombre_cola, String mensaje) throws RemoteException;
}


    