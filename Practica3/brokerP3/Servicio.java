

import java.util.List;
import java.util.ArrayList;

public class Servicio {
    String nombreServidor; // Nombre del servidor al que pertenece el servicio
    String nombreServicio;
    List<Class<?>> lista_param = new ArrayList<>();
    String tipo_retorno;
    
    // Constructor para el listado de servidores, constructor básico que añade
    // todos los parámetros para el correcto llamado a la función con el broker
    public Servicio(String _nombreServidor , String _nombreServicio, List<Class<?>> _lista_param, String _tipo_retorno) {
        this.nombreServidor = _nombreServidor;
        this.nombreServicio = _nombreServicio;
        this.lista_param = _lista_param;
        this.tipo_retorno = _tipo_retorno;
    }

    // Función que devuelve un ArrayList con la concatenacion de nombre de
    // servicio, lista de los parámetros y el tipo de retorno
    public String getServicio() {        
        String servicio = "";

        servicio = servicio + nombreServicio + ": (";
        for (int i = 0; i < lista_param.size(); i++) {
            servicio = servicio + lista_param.get(i);
            if (i != lista_param.size() - 1) {
                servicio = servicio + ",";
            }
        }
        servicio = servicio + ") --> " + tipo_retorno;
        
        return servicio;
    }

    // Función que devuelve el nombre de la función asociada al servicio
    public String getNombre() {
        return nombreServicio;
    }

    // Función que devuelve el nombre del servidor asociado al servicio
    public String getServidor() {
        return nombreServidor;
    }

    // Función que devuelve el tipo de retorno de la función asociada al servicio
    public String getRetorno() {
        return tipo_retorno;
    }

    // Función que devuelve un ArrayList con los parámetros de la función
    public List<Class<?>> getParametros() {
        return lista_param;
    }
}
