/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 04/05/2023
 * Descripción: fichero que implementa la interfaz Broker para que se pueda crear
 *              el broker con una API publica pero sin mostrar como se implementan
 *              las funciones. Se permiten crear colas y consumir y publicar mensajes
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Broker extends Remote 
{
    // Función que permite crear nuevas colas dentro del Broker para los productores
    void declarar_cola(String nombre_cola) throws RemoteException;

    // Función que permite a los consumidores crear nuevas colas dentro de Broker y suscribirse a ellas
    // de manera que cuando aparezca un mensaje puedan ser notificados con el callback y consumir el mensaje
    void declarar_cola(String nombre_cola, String nombre_consumidor, String ip) throws RemoteException;

    // Función que permite a los consumidores consumir el mensaje de la cola a la que están suscritos
    String consumir (String nombre_cola) throws RemoteException;

    // Función que permite a los productores publicar un mensaje en la cola que deseen
    void publicar (String nombre_cola, String mensaje, Boolean durable) throws RemoteException;

    //Función que permite al consumidor comunicarle al broker que ya está listo para consumir otro mensaje
    void liberar (String consumer, String nombreCola) throws RemoteException;
}


    