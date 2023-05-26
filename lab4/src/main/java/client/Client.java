package client;

import connection.MessageType;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable, Closeable {
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private List<Observer> observers;
    private Message lastMessage;
    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        this.observers = new ArrayList<>();
        socket = new Socket(ip,port);
        writer = new ObjectOutputStream(socket.getOutputStream());
        reader = new ObjectInputStream(socket.getInputStream());
    }
    public void sendMessage(Message message) throws IOException {
        if (message.getMessage().equals("/exit")){
            writer.writeObject(new Message("/exit", MessageType.SERVER_REQUEST));
            close();
            return;
        }
        writer.writeObject(message);
        writer.flush();
    }


    public boolean isConnected(){
        return !socket.isClosed();
    }

    public Message getLastMessage() {
        return lastMessage;
    }
    @Override
    public void run() {
        try {
            Thread control = new Thread(new ClientController(this));
            control.start();
            while (!socket.isClosed()){
                lastMessage = (Message) reader.readObject();
                notifyObservers();
            }
            writer.close();
            reader.close();
        } catch (Exception ignored) {
        }
        finally {
            close();
        }
    }
    @Override
    public void close() throws IOException {

    }
}
