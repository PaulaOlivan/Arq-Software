package Broker;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Servicio;

public interface Broker extends Remote{
    
    //métodos del interface
    public void registrar_servidor(String nombreServidor, String host_remoto_IP_puerto) throws RemoteException;
    public void alta_servicio (String nombreServidor, String nombreServicio, List<String> lista_param, String tipo_retorno) throws RemoteException;
    public void baja_servicio (String nombreServidor, String nombreServicio) throws RemoteException;
    public List<Servicio> listar_servicio() throws RemoteException;
/*     public <T> Resultado<String> ejecutar_servicio(String nombreServicio, List<String> lista_param) throws RemoteException;
    public void ejecutar_servicio_asincrono(String nombreServicio, List<String> lista_param) throws RemoteException;
    public <T> Resultado<T> obtener_respuesta_asincrono(String nombreServicio) throws RemoteException; */
}
