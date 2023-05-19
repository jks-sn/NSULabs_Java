package server;

import connection.Message;
import connection.MessageType;
import connection.Parser;
import connection.User;
import exceptions.InvalidUserName;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class UserHandler implements Runnable {
    private final Logger logger = Logger.getLogger(UserHandler.class.getName());
    private final Socket socket;
    private final Server server;
    private final Parser parser;

    public UserHandler(Socket socket, Server server, Parser parser) {
        this.socket = socket;
        this.server = server;
        this.parser = parser;
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            ByteBuffer byteBuffer = parser.convertToByteBuffer(message);
            OutputStream output = socket.getOutputStream();
            output.write(byteBuffer.array());
            output.flush(); //может не работать
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            InputStream reader = socket.getInputStream();
            int messageLen = Integer.parseInt(String.valueOf(ByteBuffer.wrap(reader.readNBytes(8))));
            Message loginMessage = parser.convertToMessage(ByteBuffer.wrap(reader.readNBytes(messageLen)));
            User user = new User(loginMessage.getMessage(), socket.getPort());
            server.addUser(user, this);
            logger.info("User =" + user.getName() + ", id=" + user.getId() + " successful registration");
            server.sendMessage(new Message("User= " + user.getName() + " successful registration", MessageType.SERVER_RESPONSE, user));
            while (true) {
                messageLen = Integer.parseInt(String.valueOf(ByteBuffer.wrap(reader.readNBytes(8))));
                Message message = parser.convertToMessage(ByteBuffer.wrap(reader.readNBytes(messageLen)));
                message.setUser(user);
                logger.info("Get message {" + message.getMessage() + "}" + " Type=" + message.getType() + " From=" + message.getSenderID());
                server.sendMessage(message);
            }
        } catch (InvalidUserName e) {
            sendMessage(new Message("This name is busy", MessageType.SERVER_RESPONSE));
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }
}
