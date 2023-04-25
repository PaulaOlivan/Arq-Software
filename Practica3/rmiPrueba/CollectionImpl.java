import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CollectionImpl extends UnicastRemoteObject
implements Collection
{
    //Private member variables
    private int m_number_of_books;
    private String m_name_of_collection;
    //Constructor
    public CollectionImpl() throws RemoteException{
        super();//Llama al constructor de UnicastRemoteObject
        m_number_of_books = 0;
        m_name_of_collection = "Colección aburrida";
    }
    
    //TODO: Implementar todos los metodos de la interface remota
    public int number_of_books() throws RemoteException
    {
        return m_number_of_books;
    }

    public String name_of_collection() throws RemoteException {
        return m_name_of_collection;
        // El mensaje de error no se alcanza nunca, se debe hacer un try-catch?
        //throw new UnsupportedOperationException("Unimplemented method 'name_of_collection'");
    }

    public void name_of_collection(String _new_value) throws RemoteException {
        m_name_of_collection = _new_value;
        // El método está implementado, se debe eliminar el throw ya que dice que es un método no implementado?
        //throw new UnsupportedOperationException("Unimplemented method 'name_of_collection'");
    }

    public static void main(String args[]) {
            //Fijar el directorio donde se encuentra el java.policy
            //El segundo argumento es la ruta al java.policy
            //System.setProperty("java.security.policy", "./java.policy");
            //Crear administrador de seguridad
            //System.setSecurityManager(new SecurityManager());
            //nombre o IP del host donde reside el objeto servidor
            String hostName = "155.210.154.204:32004"; //se puede usar "IPhostremoto:puerto"
            //Por defecto RMI usa el puerto 1099

        try
        {
            // Crear objeto remoto
            CollectionImpl obj = new CollectionImpl();
            System.out.println("Creado!");
            //Registrar el objeto remoto
            Naming.rebind("//" + hostName + "/Collection_771", obj);
            System.out.println("Estoy registrado!");
        }
            catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}
