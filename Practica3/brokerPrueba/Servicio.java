

import java.util.List;
import java.util.ArrayList;

public class Servicio {
    String nombreServidor; // Nombre del servidor al que pertenece el servicio
    String nombreServicio;
    List<String> lista_param = new ArrayList<>();
    String tipo_retorno;
    
    // Constructor para el listado de servidores, constructor básico que añade
    // todos los parámetros para el correcto llamado a la función con el broker
    public Servicio(String _nombreServidor , String _nombreServicio, List<String> _lista_param, String _tipo_retorno) {
        this.nombreServidor = _nombreServidor;
        this.nombreServicio = _nombreServicio;
        this.lista_param = _lista_param;
        this.tipo_retorno = _tipo_retorno;
    }

    // Constructor para el listado a los clientes, no necesitan conocer el 
    // servidor que posee la funcion, para ellos es transparente
    public Servicio(String _nombreServicio, List<String> _lista_param, String _tipo_retorno) {
        this.nombreServicio = _nombreServicio;
        this.lista_param = _lista_param;
        this.tipo_retorno = _tipo_retorno;
    }
}
