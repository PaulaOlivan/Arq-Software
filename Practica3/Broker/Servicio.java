package Broker;
/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 10/04/2023
 * Descripción: fichero que implementa la clase Servicios para la creación de
 *              la matriz dentro del Broker
 * AHORA MISMO NO ES NECESARIO, LA CLASE SE DEFINE EN EL PROPIO FICHERO Broker.java
 */

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
