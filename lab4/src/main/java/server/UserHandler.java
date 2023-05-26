package server;

import connection.MessageType;
import connection.User;
import exceptions.InvalidUserName;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class UserHandler implements Runnable {
    private final Logger logger = Logger.getLogger(UserHandler.class.getName());
    private final Socket socket;
    private final Server server;
    private ObjectOutputStream writer;

    public UserHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            writer.writeObject(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            Message loginMessage = (Message) reader.readObject();
            User user = new User(loginMessage.getMessage(), socket.getPort());
            server.addUser(user, this);
            logger.info("User=" + user.getName() + " id=" + user.getId() + " successful registration");
            server.sendMessage(new Message("User= " + user.getName() + " successful registration", MessageType.SERVER_RESPONSE, user));
            while (true){
                Message message = (Message) reader.readObject();
                message.setUser(user);
                logger.info("Get message {"+ message.getMessage()+"}" + " Type=" + message.getType() + " From=" + message.getSenderID());
                server.sendMessage(message);
            }
        }
        catch (InvalidUserName ex){
            sendMessage(new Message("This name is busy", MessageType.SERVER_RESPONSE));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }
}
