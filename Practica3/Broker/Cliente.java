/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 21/02/2023
 * Descripción: fichero que implementa la clase Cliente para que se reconozca
 *              como un objeto de tipo Cliente en el broker RMI.
 */

package Broker;

import java.rmi.Naming;
import java.util.List;
import Broker.Resultado;

public class Cliente{
    String nombreCliente;
    String IP;
    String IP_broker;

    public Cliente(String nombreCliente, String IP, String IP_broker) {
        this.nombreCliente = nombreCliente;
        this.IP = IP;
        this.IP_broker = IP_broker;
    }

    // Función para realizar la conexión al broker y obtener el listado de servicios
    public void suscripcion() {
        try {
            // Nos conectamos al broker, revisar como se hace la conexion desde el servidor
            Broker broker = (Broker) Naming.lookup("rmi://" + this.IP_broker + "/Broker");

            // Se obtiene el listado de servicios del broker
            List<Servicio> listadoServicios = broker.listar_servicio();

            // Se imprime el listado de servicios
            System.out.println("Listado de servicios disponibles:");
            for (Servicio servicio : listadoServicios) {
                System.out.println(servicio.nombreServicio);
            }
        } catch (Exception e) {
            System.out.println("Error en la conexión con el broker");
        }
    }

/*     // Función para realizar la conexión al broker y realizar un servicio
    public void realizar_servicio(String nombreServicio, List<String> parametros) {
        try {
            // Nos conectamos al broker, revisar como se hace la conexion desde el servidor
            Broker broker = (Broker) Naming.lookup("rmi://" + this.IP_broker + "/Broker");

        } catch (Exception e) {
            System.out.println("Error en la conexión con el broker");
        }
    } */

}