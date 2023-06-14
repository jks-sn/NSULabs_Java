package ru.nsu.ccfit.berkaev.client.connection;

import ru.nsu.ccfit.berkaev.client.Client;
import ru.nsu.ccfit.berkaev.ctsmessages.CTSMessage;
import ru.nsu.ccfit.berkaev.exceptions.NoActiveSocketException;
import ru.nsu.ccfit.berkaev.exceptions.SocketPropertyGetError;
import ru.nsu.ccfit.berkaev.exceptions.SocketStillOpenedException;
import ru.nsu.ccfit.berkaev.protocolInterface.MessageProtocolFabric;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.*;

import static ru.nsu.ccfit.berkaev.constants.ClientConstants.*;
import static ru.nsu.ccfit.berkaev.constants.ClientSocketConstants.*;
import static ru.nsu.ccfit.berkaev.constants.SharedConstants.*;

public class ClientSocket extends Thread {

    private final Client client;
    private Socket socket = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;

    private final Map<String, Runnable> reactions = new HashMap<>();
    private ArrayList<Object> serverMessageData;

    private final MessageProtocolFabric messageProtocolFabric;

    public ClientSocket(Client client, String protocol) {
        setName(CLIENT_SOCKET_NAME);
        Properties prop = new Properties();
        try {

            InputStream stream = ClientSocket.class.getResourceAsStream(SOCKET_PROPERTIES_PATH);
            prop.load(stream);
            String className = prop.getProperty(protocol);
            if (className == null) //check
            {
                throw new SocketPropertyGetError();
            }
            messageProtocolFabric = (MessageProtocolFabric) Class.forName(className).getConstructor().newInstance();
            this.client = client;
            initReactions();
        }  catch (IOException | ClassNotFoundException | NoSuchMethodException | SocketPropertyGetError |
                  InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
        private void initReactions() {
        reactions.put(LOGIN_STATUS_COMMAND_NAME, () -> client.setRegistrationStatus(true));
        reactions.put(GET_PARTICIPANT_LIST_COMMAND_NAME, () -> client.showParticipantsTable(serverMessageData));
        reactions.put(GET_CHAT_HISTORY_COMMAND_NAME, () -> client.refreshChatView(serverMessageData));
        reactions.put(ERROR_COMMAND_NAME, () -> {
            String error = (String) serverMessageData.get(BEGINNING_DATA);
            if (error.equals(LOCKED_USER_NAME_ERROR_1) || error.equals(LOCKED_USER_NAME_ERROR_2)) {
                socket = null;
                client.setRegistrationStatus(false);
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            else client.processError((String) serverMessageData.get(BEGINNING_DATA));
        });
    }

    public synchronized void initNewConnection(String host, int port) throws IOException, SocketStillOpenedException {
        if (socket!= null) {
            throw new SocketStillOpenedException();
        }
        socket = new Socket(host, port);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        notify();
    }

    public void closeConnection() throws IOException, NoActiveSocketException {
        if (socket == null) {
            throw new NoActiveSocketException();
        }
        socket.close();
        objectOutputStream.close();
        objectInputStream.close();
        objectInputStream = null;
        objectOutputStream = null;
        socket = null;
    }

    public boolean isConnected() {
        return socket != null;
    }

    public void sendMessage(CTSMessage message) throws IOException, NoActiveSocketException {
        messageProtocolFabric.sendMessage(message,socket,objectOutputStream,client);
    }

    private STCMessage getMessage() throws ClassNotFoundException, IOException {
        return messageProtocolFabric.getMessage(objectInputStream,client);
    }

    @Override
    public synchronized void run() {
        while (! this.isInterrupted()) {
            try {
                STCMessage serverMessage = getMessage();
                serverMessageData = serverMessage.getData();
                reactions.get(serverMessage.getName()).run();
            } catch (ClassNotFoundException e)
            {
                System.out.println(RESET_MESSAGE);
                client.closeError(e.getMessage());
            }
            catch( IOException e) {
                client.processError(e.getMessage());
            } catch (NullPointerException e) {
                try {
                    wait();
                } catch (InterruptedException err) {
                    this.interrupt();
                }
            }
        }
        System.out.println(CLIENT_INTERRUPTED_MESSAGE);
    }
}
