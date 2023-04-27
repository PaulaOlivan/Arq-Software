import java.rmi.RemoteException;
import java.rmi.Remote;

public interface Cadenas extends Remote
{
    // métodos de la interface
    String concatenar(String a, String b) throws RemoteException;
}
