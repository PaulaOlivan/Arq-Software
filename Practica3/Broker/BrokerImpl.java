package Broker;
/*
 * Autores: Paula Olivan Usieto (771938) & Juan Pellicer Barco (818138)
 * Fecha de creación: 21/02/2023
 * Descripción: fichero que implementa la librería de funciones de RMI para la
 *              creación de un broker con RMI para cliente y servidor.
 */

import java.util.List;

import Servicio;
import Servidor;

import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BrokerImpl extends UnicastRemoteObject implements Broker{
    // Cada servidor se identificará dentro del Broker por el número que ocupe
    // en la lista de servidores. En la matriz servicio se almacenará la lista
    // de servicio de cada servidor, los servicio de un mismo servidor irán
    // en la misma fila de la matriz y coincidirá con el número del servidor en
    // el vector de servidores.
    private List<Servidor> servidores = new ArrayList<>();
    // Matriz para almacenar los servicio de los servidores
    private List<Servicio> servicios = new ArrayList<>();

    // Constructor de la clase
    public BrokerImpl() throws RemoteException{
        super();
    }

    // Función que devuelve el índice de un Servicio en caso de existir para un
    // servidor registrado concreto, en caso de no existir devuelve -1
    private int existeServicioEnServidor (String nombreServidor, String nombreServicio){
        int indice = -1;
        // Buscamos el servidor en la lista de servidores
        for (int i = 0; i < servidores.size(); i++) {
            if (servidores.get(i).nombreServidor == nombreServidor) {
                // Buscamos el servicio en la lista de servicio
                for (int j = 0; j < servicios.size(); j++) {
                    if (servicios.get(j).nombreServicio == nombreServicio && servicios.get(j).nombreServidor == nombreServidor) {
                        indice = j;
                    }
                }
            }
        }
        return indice;
    }

    // Función que devuelve true si en la lista de servicios hay un servicio
    // con el nombre nombreServicio, en caso de no existir devuelve false.
    // También se comprueba que los parámetros coincidas con los del servicio.
    private boolean existeServicio (String nombreServicio, List<String> lista_param, String nombreServidor){
        boolean existe = false;
        // Buscamos el servicio en la lista de servicio
        for (int j = 0; j < servicios.size(); j++) {
            if (servicios.get(j).nombreServicio == nombreServicio) {
                // Comprobamos que los parámetros coincidan
                if (servicios.get(j).lista_param == lista_param) {
                    existe = true;
                    nombreServidor = servicios.get(j).nombreServidor;
                }
            }
        }
        return existe;
    }

    /* API PARA LOS SERVIDORES QUE QUIERAN CONECTARSE AL BROKER */

    // Añade un servidor al broker para que pueda ser utilizado por los clientes,
    // el servidor se identifica por su nombre y su dirección IP y puerto. Aunque
    // el servidor se añada al broker, no se conocerá la lista de sus funciones 
    public void registrar_servidor(String nombreServidor, String host_remoto_IP_puerto) throws RemoteException{
        Servidor servidor = new Servidor(nombreServidor, host_remoto_IP_puerto);
        servidores.add(servidor);
    }

    // Función que añade un servicio a un servidor, para poder añadirlo al broker
    // el servidor nombreServidor debe estar registrado en el broker. 
    public void alta_servicio (String nombreServidor, String nombreServicio, List<String> lista_param, String tipo_retorno) throws RemoteException{
        Servicio servicio = new Servicio(nombreServidor, nombreServicio, lista_param, tipo_retorno);
        servicios.add(servicio);
    }

    // Función que elimina un servicio de un servidor, para poder eliminarlo del
    // broker el servidor nombreServidor debe estar registrado en el broker.
    // También se debe cumplir que el servicio nombreServicio posea un servicio
    // con el nombre nombreServicio.
    public void baja_servicio (String nombreServidor, String nombreServicio) throws RemoteException{
        // Buscamos el servidor en la lista de servidores
        int indice = existeServicioEnServidor(nombreServidor, nombreServicio);
        if (indice != -1) {
            servicios.remove(indice);
        }
    }

    /* API PARA LOS CLIENTES QUE QUIERAN HACER USO DEL BROKER */

    // Función que devuelve la lista de servicio dados de alta en el broker
    public List<Servicio> listar_servicio() throws RemoteException{
        List<Servicio> lista_servicio = new ArrayList<>();
        for (int i = 0; i < servicios.size(); i++) {
            // Creamos un nuevo servicio con los datos de interés para el cliente
            Servicio funprod = new Servicio(servicios.get(i).nombreServicio, servicios.get(i).lista_param, servicios.get(i).tipo_retorno);
            lista_servicio.add(funprod);
        }
        return lista_servicio;
    }

    
/*     // Función que permite ejecutar un servicio por parte del cliente
    public <T> Resultado<String> ejecutar_servicio(String nombreServicio, List<String> lista_param){
        String nombreServidor = "";
        Boolean existe = existeServicio(nombreServicio, lista_param, nombreServidor);
        if (existe) {
            // Si el servicio existe miramos el servidor que lo posee y lo ejecutamos


            // Creamos el resultado
            Resultado<String> resultado = new Resultado<String>("Error, sin definir la función");

            return resultado;

        }
        else {
            System.out.println("El servicio no existe con los parámetros indicados");
            // Creamos el resultado
            Resultado<String> resultado = new Resultado<String>("Error, no existe el servicio con los parámetros indicados");
            return resultado;
        }
    }
    
    // Función que permite ejecutar asincronamente un servicio por parte del cliente
    public void ejecutar_servicio_asincrono(String nombreServicio, List<String> lista_param){
        Boolean existe = existeServicio(nombreServicio, lista_param, "nombreServidor";
        if (existe) {

        }
        else {
            System.out.println("El servicio no existe con los parámetros indicados");
        }

    }

    // Función que permite obtener el resultado de un servicio asíncrono
    public <T> Resultado<T> obtener_respuesta_asincrono(String nombreServicio){

    } */

    public static void main(String args[]) {
        //Fijar el directorio donde se encuentra el java.policy
        //El segundo argumento es la ruta al java.policy
        //System.setProperty("java.security.policy", "./java.policy");
        //Crear administrador de seguridad
        //System.setSecurityManager(new SecurityManager());
        //nombre o IP del host donde reside el objeto servidor
        String hostName = "155.210.154.204:32004"; //se puede usar "IPhostremoto:puerto"
        //Por defecto RMI usa el puerto 1099

    try
    {
        // Crear objeto remoto
        BrokerImpl obj = new BrokerImpl();
        System.out.println("Creado!");
        //Registrar el objeto remoto
        Naming.rebind("//" + hostName + "/Broker_Radiactividad", obj);
        System.out.println("Estoy registrado!");
    }
        catch (Exception ex)
    {
        System.out.println(ex);
    }
}
}

