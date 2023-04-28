import java.rmi.RemoteException;
import java.rmi.Remote;

public interface Mates extends Remote
{
    // métodos de la interface
    int suma(int a, int b, int c) throws RemoteException;
    int resta(int a, int b) throws RemoteException;
    int multiplicar(int a, int b) throws RemoteException;    
    int dividir(int a, int b) throws RemoteException;
}
