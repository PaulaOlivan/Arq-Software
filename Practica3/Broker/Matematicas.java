/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 21/02/2023
 * Descripción: fichero que implementa una serie de funciones matemáticas para
 *              poder acceder y hacer uso de ellas desde el cliente del broker
 */

package Broker;
import java.util.ArrayList;
import java.util.List;
import java.rmi.Naming;

public class Matematicas extends Servidor{
    String IP;
    String nombreServidor;
    
    
    public Matematicas(String _ip) {
        super("ServerMath", _ip);
    }

    public float suma(float a, float b) {
        return a + b;
    }
    
    public float resta(float a, float b) {
        return a - b;
    }

    public float multiplicacion(float a, float b) {
        return a * b;
    }

    public float division(float a, float b) {
        return a / b;
    }

    public float potencia(float a, float b) {
        return (float) Math.pow(a, b);
    }

    public float raiz(float a) {
        return (float) Math.sqrt(a);
    }

    public float factorial(float a) {
        float resultado = 1;
        for (float i = 1; i <= a; i++) {
            resultado *= i;
        }
        return resultado;
    }

        // Función para realizar la conexión con el broker y registrar sus servicios
        public void suscripcion () {
            try {
                // Nos conectamos al broker, revisar como se hace la conexion desde el servidor
                Broker broker = (Broker) Naming.lookup("rmi://" + this.IP + "/Broker");
    
                // Se registra el servidor en el broker
                broker.registrar_servidor(this.nombreServidor, this.IP);
                
                // Se registran los servicios del servidor en el broker
                List<String> param = new ArrayList<>();
                param.add("float");
                broker.alta_servicio(this.nombreServidor, "raiz", param, "float");
                broker.alta_servicio(this.nombreServidor, "factorial", param, "float");
    
                param.add("float");
                broker.alta_servicio(this.nombreServidor, "suma", param, "float");
                broker.alta_servicio(this.nombreServidor, "resta", param, "float");
                broker.alta_servicio(this.nombreServidor, "multiplicacion", param, "float");
                broker.alta_servicio(this.nombreServidor, "division", param, "float");
                broker.alta_servicio(this.nombreServidor, "potencia", param, "float");
    
            } catch (Exception e) {
                System.out.println("Error en la suscripción: " + e.getMessage());
            }
        }
}
