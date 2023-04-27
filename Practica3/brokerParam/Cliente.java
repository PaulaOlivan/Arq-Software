import java.rmi.Naming;
//import java.rmi.RemoteException;
//import java.util.Collection;
//import java.util.Iterator;
//import Collection;
//TODO: imports necesarios

public class Cliente {
    public static void main(String[] args) {
        // Si no tenemos ningun argumento listamos los servicios
        int max = 6;

        String[] argsProcessed = new String[max];

        for (int i = 0; i < max; i++) {// Por defecto lo dejamos en null
            argsProcessed[i] = "0";
        }

        for (int i = 0; i < args.length; i++) {// Rellenamos el resto con argumentos
            argsProcessed[i] = args[i];
        }

        if (argsProcessed.length == 0) {
            try {
                // Paso 1 - Obtener una referencia al objeto servidor creado anteriormente
                // Nombre del host servidor o su IP. Es donde se buscar al objeto remoto
                String hostname = "155.210.154.203:32004"; // se puede usar "IP:puerto"
                Broker server = (Broker) Naming.lookup("//" + hostname + "/Broker_771");

                // Paso 2 - Invocar remotamente los metodos del objeto servidor:
                // Obtenemos el nombre de la colección y el número de libros
                System.out.println("Estoy conectado");

                // Le pedimos al Broker las operaciones que tiene registradas
                String lista_servicios = server.listar_servicios();
                System.out.println("Lista de servicios: \n" + lista_servicios);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        // Si nos han pasado argumentos, lanzamos la operación correspondiente
        else {
            try {
                // Paso 1 - Obtener una referencia al objeto servidor creado anteriormente
                // Nombre del host servidor o su IP. Es donde se buscar al objeto remoto
                String hostname = "155.210.154.203:32004"; // se puede usar "IP:puerto"
                Broker server = (Broker) Naming.lookup("//" + hostname + "/Broker_771");

                // Paso 2 - Invocar remotamente los metodos del objeto servidor:
                // Obtenemos el nombre de la colección y el número de libros
                System.out.println("Estoy conectado");

                // Le pedimos al Broker las operaciones que tiene registradas
                System.out.println("Nombre de la funcion: " + argsProcessed[0]);
                System.out.println("Argumentos: " + argsProcessed[1] + " " + argsProcessed[2] + " " + argsProcessed[3]
                        + " " + argsProcessed[4] + " " + argsProcessed[5]);
                String resultado = server.ejecutar_servicio(argsProcessed[0], argsProcessed[1],argsProcessed[2],argsProcessed[3],argsProcessed[4],argsProcessed[5]);
                System.out.println("El resultado obtenido es: " + resultado);

                // Le pedimos al Broker las operaciones que tiene registradas
                String lista_servicios = server.listar_servicios();
                System.out.println("Lista de servicios: \n" + lista_servicios);
            } 
            catch (NumberFormatException e) {
                try {
                    String hostname = "155.210.154.203:32004"; // se puede usar "IP:puerto"
                    Broker server = (Broker) Naming.lookup("//" + hostname + "/Broker_771");
                    String resultado = String.valueOf(server.ejecutar_servicio(argsProcessed[0], argsProcessed[1], argsProcessed[2],
                            argsProcessed[3], argsProcessed[4], argsProcessed[5]));
                    System.out.println("El resultado obtenido es: " + resultado);
                    
                    String lista_servicios = server.listar_servicios();
                    System.out.println("Lista de servicios: \n" + lista_servicios);
                } 
                catch (Exception ex) {
                    System.out.println(ex); 
                }
            }
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
