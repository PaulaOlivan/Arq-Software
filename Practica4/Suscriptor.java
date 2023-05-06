/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 04/05/2023
 * Descripción: fichero que contiene la clase Suscriptor para que se puedan
 *             crear los ArrayList de suscriptores de las colas en el Broker  
 */

public class Suscriptor {
    String nombreSuscriptor;
    String IPSuscriptor;
    Boolean ocupado; // true si no puede consumir, false si sí puede
    String nombreCola; // Nombre de la cola a la que está suscrito
    
    public Suscriptor(String _nombreS, String _IPSus, String nomCola) {
        nombreSuscriptor = _nombreS;
        IPSuscriptor = _IPSus;
        ocupado = false;
        nombreCola = nomCola;
    }

    public String getNameCola() {
        return nombreCola;
    }
    
    public String getNombreSuscriptor(){
        return nombreSuscriptor;
    }

    public String getIpSuscriptor() {
        return IPSuscriptor;
    }

    public Boolean getOcupado() {
        return ocupado;
    }
    
    public void setOcupado(Boolean _ocupado){
        ocupado = _ocupado;
    }
}
