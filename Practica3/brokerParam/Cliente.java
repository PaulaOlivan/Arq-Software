import java.rmi.Naming;
//import java.rmi.RemoteException;
//import java.util.Collection;
//import java.util.Iterator;
//import Collection;
//TODO: imports necesarios

public class Cliente{
    public static void main(String[] args){
        // Si no tenemos ningun argumento listamos los servicios
        if (args.length == 0){
            try{
                //Paso 1 - Obtener una referencia al objeto servidor creado anteriormente
                //Nombre del host servidor o su IP. Es donde se buscar al objeto remoto
                String hostname = "155.210.154.204:32004"; //se puede usar "IP:puerto"
                Broker server = (Broker) Naming.lookup("//"+ hostname + "/Broker_771");

                //Paso 2 - Invocar remotamente los metodos del objeto servidor:
                //Obtenemos el nombre de la colección y el número de libros
                System.out.println("Estoy conectado");

                //Le pedimos al Broker las operaciones que tiene registradas
                String lista_servicios = server.listar_servicios();
                System.out.println("Lista de servicios: \n" + lista_servicios);
            }
            catch (Exception ex) {
                System.out.println(ex);
            }        
        }
        // Si nos han pasado argumentos, lanzamos la operación correspondiente
        else{
            try{
                //Paso 1 - Obtener una referencia al objeto servidor creado anteriormente
                //Nombre del host servidor o su IP. Es donde se buscar al objeto remoto
                String hostname = "155.210.154.204:32004"; //se puede usar "IP:puerto"
                Broker server = (Broker) Naming.lookup("//"+ hostname + "/Broker_771");

                //Paso 2 - Invocar remotamente los metodos del objeto servidor:
                //Obtenemos el nombre de la colección y el número de libros
                System.out.println("Estoy conectado");

                //Le pedimos al Broker las operaciones que tiene registradas
                int resultado = server.ejecutar_servicio(args[0], Integer.parseInt(args[1]) , Integer.parseInt(args[2]));
                System.out.println("El resultado obtenido es: " + resultado);

                //Le pedimos al Broker las operaciones que tiene registradas
                String lista_servicios = server.listar_servicios();
                System.out.println("Lista de servicios: \n" + lista_servicios);
            }
            catch (Exception ex) {
                System.out.println(ex);
            }        
        }
    }   
}
