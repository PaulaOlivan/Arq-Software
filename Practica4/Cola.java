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
    
    //Funcion que elimina un mensaje que se ha caducado
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

    //Funcion que busca en la cola un mensaje y lo consume
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
}