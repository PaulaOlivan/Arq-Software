/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 21/02/2023
 * Descripción: fichero que implementa una serie de funciones sobre cadenas de
 *              caracteres para poder acceder y hacer uso de ellas desde el 
 *              cliente del broker
 */

package Broker;
import java.util.ArrayList;
import java.util.List;
import java.rmi.Naming;



public class Cadenas extends Servidor{
    String IP;
    String nombreServidor;

    public Cadenas(String _ip, String _nombre) {
        super(_nombre, _ip);
    }

    public String mayusculas(String a) {
        return a.toUpperCase();
    }

    public String minusculas(String a) {
        return a.toLowerCase();
    }

    public String invertir(String a) {
        String resultado = "";
        for (int i = a.length() - 1; i >= 0; i--) {
            resultado += a.charAt(i);
        }
        return resultado;
    }

    public int longitud(String a) {
        return a.length();
    }

    public String concatenar(String a, String b) {
        return a + b;
    }

    public String reemplazar(String a, String b, String c) {
        return a.replace(b, c);
    }

    public String subcadena(String a, int inicio, int fin) {
        return a.substring(inicio, fin);
    }

    public void suscripcion(){
        try {
            // Nos conectamos al broker, revisar como se hace la conexion desde el servidor
            Broker broker = (Broker) Naming.lookup("rmi://" + this.IP + "/Broker");

            // Se registra el servidor en el broker
            broker.registrar_servidor(this.nombreServidor, this.IP);
            
            // Se registran los servicios del servidor en el broker
            List<String> param = new ArrayList<>();
            param.add("String");
            broker.alta_servicio(this.nombreServidor, "mayusculas", param, "String");
            broker.alta_servicio(this.nombreServidor, "minusculas", param, "String");
            broker.alta_servicio(this.nombreServidor, "invertir", param, "String");
            broker.alta_servicio(this.nombreServidor, "longitud", param, "int");

            param.add("String");
            broker.alta_servicio(this.nombreServidor, "concatenar", param, "String");

            param.add("String");
            broker.alta_servicio(this.nombreServidor, "reemplazar", param, "String");
            
            param.clear();
            param.add("String");
            param.add("int");
            param.add("int");
            broker.alta_servicio(this.nombreServidor, "subcadena", param, "String");

        } catch (Exception e) {
            System.out.println("Error al registrar el servidor en el broker");
        }
    }

}
