import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

public class BrokerImpl extends UnicastRemoteObject
implements Broker
{
    private List<Cola> colas = new ArrayList<Cola>();
    public BrokerImpl() throws RemoteException
    {
        super();
    }

    public void declarar_cola(String nombre_cola) throws RemoteException
    {
        /* Cola cola = new Cola(nombre_cola);
        colas.add(cola); */
    }

    public String consumir (String nombre_cola, String mensaje) throws RemoteException
    {
        /* String message = "";
        for (int i = 0; i < colas.size(); i++){
            if (colas.get(i).nombre.equals(nombre_cola)){
                message = colas.get(i).consumeMessage(mensaje);
            }
        }
        return message; */
        return "";
    }

    public void publicar (String nombre_cola, String mensaje) throws RemoteException
    {
        /* for (int i = 0; i < colas.size(); i++){
            if (colas.get(i).nombre.equals(nombre_cola)){
                colas.get(i).addMessage(mensaje);
            }
        } */
    }
}