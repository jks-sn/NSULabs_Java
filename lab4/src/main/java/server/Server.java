package server;

import connection.Message;
import connection.User;
import exceptions.InvalidUserName;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Server implements Runnable {
    private final Logger logger = Logger.getLogger(Server.class.getName());
    private int port;
    private ServerSocket serverSocket;
    private final ConcurrentHashMap<User, UserHandler> clients= new ConcurrentHashMap<>();

    public Server(int port) {
        this.port = port;
    }
    public void addUser(User user, UserHandler handler) throws InvalidUserName {
        if (!userNameCheck(user)){
            throw new InvalidUserName("This name is busy", user.getName());
        }
        clients.put(user, handler);
    }
    void close(){
        if (serverSocket != null){
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(Message message){
        switch (message.getType()) {
            case BROADCAST_MESSAGE -> sendToAllMessage(message);
            case SERVER_RESPONSE -> sendServerResponseMessage(message);
        }
    }

    private void sendServerResponseMessage(Message message) {
        message.setUser(new User("Server"));
        for (User user: clients.keySet()){
            if (user.getId() == message.getSenderID()){
                clients.get(user).sendMessage(message);
                break;
            }
        }
    }

    private void sendToAllMessage(Message message) {
        for (UserHandler handler: clients.values()){
            handler.sendMessage(message);
        }
    }
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server starting on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected " + clientSocket.getInetAddress() + " " + clientSocket.getPort());
                Thread t = new Thread(new UserHandler(clientSocket, this,));
                t.start();
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            close();
        }
    }
    private boolean userNameCheck(User user) {
        for (User i: clients.keySet()){
            if (i.getName().equals(user.getName())){
                return false;
            }
        }
        return true;
    }
}
