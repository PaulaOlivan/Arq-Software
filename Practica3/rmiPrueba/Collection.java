import java.rmi.Remote;
import java.rmi.RemoteException;
//TODO: otros imports   

public interface Collection extends Remote
{
    //metodos de la interface
    int number_of_books() throws RemoteException;
    String name_of_collection() throws RemoteException;
    void name_of_collection(String _new_value) throws RemoteException;
}