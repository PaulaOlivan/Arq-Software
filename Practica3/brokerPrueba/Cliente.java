import java.rmi.Naming;
//import java.rmi.RemoteException;
//import java.util.Collection;
//import java.util.Iterator;
//import Collection;
//TODO: imports necesarios

public class Cliente{
    public static void main(String[] args){
        //TODO: Fijar el directorio donde se encuentra el java.policy
        //System.setProperty("java.security.policy", "./java.policy");

        //if (System.getSecurityManager() == null) {
	// System.setSecurityManager(new SecurityManager());
            
	// }
        try{
            //Paso 1 - Obtener una referencia al objeto servidor creado anteriormente
            //Nombre del host servidor o su IP. Es donde se buscar al objeto remoto
            String hostname = "155.210.154.204:32004"; //se puede usar "IP:puerto"
            Broker server = (Broker) Naming.lookup("//"+ hostname + "/Broker_771");

            //Paso 2 - Invocar remotamente los metodos del objeto servidor:
            //Obtenemos el nombre de la colección y el número de libros
	        System.out.println("Estoy conectado");

            int resul =server.lanzarSuma(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println("El resultado de la suma es: " + resul);
        }
            catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}
