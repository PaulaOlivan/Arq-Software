/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 04/05/2023
 * Descripción: fichero que implementa la interfaz Broker para que se pueda crear
 *              el broker con una API publica pero sin mostrar como se implementan
 *              las funciones. Se permiten crear colas y consumir y publicar mensajes
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Consumidor extends Remote 
{
    // Función que será invocada por el broker cuando se deba consumir un mensaje
    void call_back(String brokerName, String brokerIP) throws RemoteException;

    // Función que permite a los consumidores consumir el mensaje de la cola a la que están suscritos
    void consumir() throws RemoteException;
    
}