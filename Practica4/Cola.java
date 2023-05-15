/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 04/05/2023
 * Descripción: fichero que implementa la clase Cola para que se pueda crear
 *              colas dentro del Broker y se puedan publicar y consumir mensajes.
 */

import java.util.ArrayList;

public class Cola 
{
    String nombre = "";
    String nombreFich = nombre+".txt";
    ArrayList<String> mensajes = new ArrayList<String>();
    ArrayList<String> suscriptores = new ArrayList<String>();
    ArrayList<String> consumidoresName = new ArrayList<String>();
    ArrayList<String> consumidoresIP = new ArrayList<String>();
    ArrayList<Boolean> consumidoresLibres = new ArrayList<Boolean>();
    int indice = 0;
    int maxIndice = 0;
    private static Cola instance;

    //Constructor de la clase Cola
    public Cola (String _name){

        nombre = _name;
    }


    //Función que dado un String mensaje lo agrega a la cola
    public void addMessage(String _message){
        mensajes.add(_message);
    }
    
    //Función que elimina un mensaje, será llamada únicamente por el hilo de caducidad
    public Boolean deleteMessage(String msg){
        String message = "";
        Boolean consumido;
        if (mensajes.size() > 0){
            message = mensajes.get(0);
            if (message.equals(msg)){
                mensajes.remove(0);
                consumido = true;
            }
            else{
                message = "Error Caducidad, el mensaje buscado no está en la cola";
                consumido = false;
            }
        }
        else{
            message = "Error Caducidad, no hay mensajes en la cola";
            consumido = false;
        }
        return consumido;
    }

    // Función que busca en la cola un mensaje y lo consume, será llamada por los consumidores
    // cuando el Broker haya realizado un callback
    public String consumeMessage(){
        String message = "";
        if (mensajes.size() > 0){
            message = mensajes.get(0);
            mensajes.remove(0);
        }
        else{
            message = "Error, no hay mensajes en la cola";
        }
        return message;
    }

    //Funcion que añade un consumidor, guardando su nombre y su IP
    public void addConsumer(String susName, String susIP){
        consumidoresName.add(susName);
        consumidoresIP.add(susIP);

        consumidoresLibres.add(true);
        maxIndice = maxIndice + 1;
        System.out.println("El consumidor " + susName +" con IP " + susIP +" se ha añadido a la cola " + nombre);
        System.out.println("El número de consumidores de la cola " + nombre + " es " + maxIndice);
    }

    // Función para devolver el número de consumidores asociados a la cola
    public int getNumConsumidores(){
        return maxIndice;
    }

    // Obtiene el primer consumidor libre, contando a partir del último consumidor que consumió
    // un mensaje (no incluido). El primer elemento es el nombre del suscriptor y el segundo su IP
    public String[] getConsumidor(){
        String[] consumidor = new String[2];
        int i = indice;
        Boolean encontrado = false;

        while (!encontrado){
            if (consumidoresLibres.get(i)){
                consumidor[0] = consumidoresName.get(i);
                consumidor[1] = consumidoresIP.get(i);

                consumidoresLibres.set(i, false);
                encontrado = true;               
            }
            i = (i+1) % maxIndice;
        } 
        return consumidor;
    }

    //Función que coloca el consumidor en la posición i como libre
    public void liberarConsumidor(String name){
        for( int i = 0; i < maxIndice; i++){
            if (consumidoresName.get(i).equals(name)){
                consumidoresLibres.set(i, true);
                break;
            }
        }
    }

    //Funcion que devuelve el nombre del consumidor
    public String getConsumidorName(int i){
        return consumidoresName.get(i);
    }

    //Funcion que devuelve la IP del consumidor
    public String getCosumidorIP(int i){
        return consumidoresIP.get(i);
    }

    //Funcion que libera un consumidor
    public Boolean getConsumidorLibre(int i){
        return consumidoresLibres.get(i);
    }
}