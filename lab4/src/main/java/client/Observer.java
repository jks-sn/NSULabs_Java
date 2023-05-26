package client;

import connection.Message;
import connection.MessageType;

public class Observer {
    private Client client;
    public Observer(Client client){
        this.client = client;
    }
    public void update() {
        Message message = client.getLastMessage();
        if (message.getType() == MessageType.SERVER_RESPONSE){
            System.out.println("SERVER RESPONSE:");
        }
        else if (message.getType() == MessageType.BROADCAST_MESSAGE) {
            System.out.print(message.getSenderName() + ": ");
        }
        System.out.println(message.getMessage());
    }
}
