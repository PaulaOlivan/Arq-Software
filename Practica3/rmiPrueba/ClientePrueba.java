import java.rmi.Naming;
//import java.rmi.RemoteException;
//import java.util.Collection;
//import java.util.Iterator;
//import Collection;
//TODO: imports necesarios

public class ClientePrueba{
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
            Collection server = (Collection) Naming.lookup("//"+ hostname + "/Collection_771");

            //Paso 2 - Invocar remotamente los metodos del objeto servidor:
            //Obtenemos el nombre de la colección y el número de libros
	        System.out.println("Estoy conectado");
            String nombreColeccion = server.name_of_collection();
            int numeroLibros = server.number_of_books();
            System.out.println("Nombre de la coleccion: " + nombreColeccion);
            System.out.println("Numero de libros: " + numeroLibros);

            //Cambiar el nombre de la colección y vemos que ha funcionado
            server.name_of_collection("Colección Remota");
            nombreColeccion = server.name_of_collection();
            System.out.println("Nuevo nombre de la coleccion: " + nombreColeccion);

            server.name_of_collection("Pipipipipipipipipipipipi");
            nombreColeccion = server.name_of_collection();
            System.out.println("Nuevo nombre de la coleccion: " + nombreColeccion);
            
        }
            catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}
