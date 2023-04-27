import java.rmi.RemoteException;
import java.rmi.Remote;

public interface Cadenas extends Remote
{
    // métodos de la interface
    String concatenar(String a, String b) throws RemoteException;
    String invertir(String a) throws RemoteException;
    String mayusculas(String a) throws RemoteException;
    String minusculas(String a) throws RemoteException;
}
