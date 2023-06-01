package client.connection;

import XMLConverter.ClientToXML.ClientMessageConvFactory;
import XMLConverter.ConverterFactory;
import XMLConverter.ServerToXML.ServerMessageConvFactory;
import client.Client;
import ctsmessages.CTSMessage;
import exceptions.ConvertionException;
import exceptions.NoActiveSocketException;
import exceptions.SocketStillOpenedException;
import stcmessages.STCMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static constants.ClientConstants.*;
import static constants.ClientSocketConstants.*;
import static constants.SharedConstants.*;

public class ReusableSocket extends Thread {

    private final Client client;
    private Socket socket = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;

    private final Map<String, Runnable> reactions = new HashMap<>();
    private ArrayList<Object> serverMessageData;

    private final String protocol;

    public ReusableSocket(Client client, String protocol) {
        setName(clientSocketName);
        this.protocol = protocol;
        this.client = client;
        initReactions();
    }

    private void initReactions() {
        reactions.put(loginStatusCommandName, () -> client.setRegistrationStatus(true));
        reactions.put(getParticipantListCommandName, () -> client.showParticipantsTable(serverMessageData));
        reactions.put(getChatHistoryCommandName, () -> client.refreshChatView(serverMessageData));
        reactions.put(errorCommandName, () -> {
            String error = (String) serverMessageData.get(beginningDATA);
            if (error.equals(lockedUserNameError1) || error.equals(lockedUserNameError2)) {
                socket = null;
                client.setRegistrationStatus(false);
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            else client.processError((String) serverMessageData.get(beginningDATA));
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
        if (socket == null) {
            throw new NoActiveSocketException();
        }
        if (protocol.equals(protocolBasicName)) objectOutputStream.writeObject(message);
        if (protocol.equals(protocolXMLName)) {
            ConverterFactory converterFactory = new ClientMessageConvFactory();
            String strMessage = null;
            try {
                strMessage = converterFactory.convertToSerializableXML(message.getName(), message.getData());
            } catch (ConvertionException e) {
                client.processError(e.getMessage());
            }
            objectOutputStream.writeObject(strMessage);
        }
    }

    private STCMessage getMessage() throws ClassNotFoundException, IOException {
        STCMessage serverMessage = null;
        if (protocol.equals(protocolBasicName)) {
            serverMessage = (STCMessage) objectInputStream.readObject();
        }
        if (protocol.equals(protocolXMLName)) {
            String xmlMessage = (String) objectInputStream.readObject();
            ConverterFactory converterFactory = new ServerMessageConvFactory();
            try {
                serverMessage = converterFactory.convertFromSerializableXMLtoSM(xmlMessage);
            } catch (ConvertionException e) {
                client.processError(e.getMessage());
            }
        }
        return serverMessage;
    }

    @Override
    public synchronized void run() {
        while (! this.isInterrupted()) {
            try {
                STCMessage serverMessage = getMessage();
                serverMessageData = serverMessage.getData();
                reactions.get(serverMessage.getName()).run();
            } catch (ClassNotFoundException | IOException e) {
                client.processError(e.getMessage());
            } catch (NullPointerException e) {
                try {
                    wait();
                } catch (InterruptedException err) {
                    this.interrupt();
                }
            }
        }
        System.out.println(clientInterruptedMessage);
    }
}
