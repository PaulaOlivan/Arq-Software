import java.util.ArrayList;

public class Cola 
{
    String nombre = "";
    ArrayList<String> mensajes = new ArrayList<String>();
    Boolean existance = false;

    //Constructor de la clase Cola
    public Cola (String _name){

        if (!existance){//Singleton
            nombre = _name;
        }
        else{
            System.out.println("La cola ya existe");
        }
    }

    //Funcion que dado un String mensaje lo agrega a la cola
    public void addMessage(String _message){
        mensajes.add(_message);
    }
    
    //Funcion que busca en la cola un mensaje y lo consume
    public String consumeMessage(String msg){
        String message = "";
        for (int i = 0; i < mensajes.size(); i++){
            if (mensajes.get(i).equals(msg)){
                message = mensajes.get(i);
                mensajes.remove(i);
            }
        }
        return message;
    }
}