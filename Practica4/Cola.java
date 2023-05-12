import java.util.ArrayList;

public class Cola 
{
    String nombre = "";
    String nombreFich = nombre+".txt";
    ArrayList<String> mensajes = new ArrayList<String>();
    ArrayList<String> suscriptores = new ArrayList<String>();
    private static Cola instance;

    //Constructor de la clase Cola
    public Cola (String _name){

        nombre = _name;
    }


    //Funcion que dado un String mensaje lo agrega a la cola
    public void addMessage(String _message){
        mensajes.add(_message);
    }
    
    //Funcion que busca en la cola un mensaje y lo consume
    public String consumeMessage(String msg){
        String message = "";
        if (mensajes.size() > 0){
            message = mensajes.get(0);
            mensajes.remove(0);
        }
        else{
            message = "No hay mensajes en la cola";
        }
        return message;
    }
}