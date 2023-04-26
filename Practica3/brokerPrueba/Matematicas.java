/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 21/02/2023
 * Descripción: fichero que implementa la clase Cliente para que se reconozca
 *              como un objeto de tipo Cliente en el broker RMI.
 */
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List; 

public class Matematicas {
    public static void main (String[] args){
        try{
            String hostname = "155.210.154.204:32002";
            Broker server = (Broker) Naming.lookup("//" + hostname + "/Broker_771");
    
            System.out.println("Registrando servidor...");
            server.registrar_servidor("Matematicas", "155.210.154.206");
            System.out.println("Servidor registrado!");

            System.out.println("Registrando servicios...");
            List<String> lista_param = new ArrayList<>();
            lista_param.add("int");
            lista_param.add("int");
            server.alta_servicio("Matematicas", "suma", lista_param, "int");

            System.out.println("Servicios registrados!");
        }
        catch (Exception e){
            System.err.println("Matematicas exception:");
            e.printStackTrace();
        }
    }
}
