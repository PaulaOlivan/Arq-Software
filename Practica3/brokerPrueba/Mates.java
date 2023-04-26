import java.rmi.RemoteException;
import java.rmi.Remote;

public interface Mates extends Remote
{
    // métodos de la interface
    int suma() throws RemoteException;    
}
