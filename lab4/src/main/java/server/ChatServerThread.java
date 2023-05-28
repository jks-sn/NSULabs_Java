package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import XMLConverter.ConverterFactory;
import XMLConverter.ClientToXML.ClientMessageConvFactory;
import XMLConverter.ServerToXML.ServerMessageConvFactory;
import ctsmessages.CTSMessage;
import exceptions.ConnectionError;
import exceptions.ConvertionException;
import server.connectmanager.ConnectionsManager;
import stcmessages.STCMessage;

public class ChatServerThread extends Thread {

    private ConnectionsManager connectionsManager;
    private Integer sessionID;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    private Map<String, Runnable> reactions = new HashMap<>();
    private ArrayList<Object> clientMessageData;

    private final String protocol;

    public ChatServerThread(ConnectionsManager connectionsManager, Socket client, Integer sessionID, String protocol) throws ConnectionError {
        setName("chatThread" + sessionID.toString());
        this.sessionID = sessionID;
        this.protocol = protocol;
        this.connectionsManager = connectionsManager;
        try {
            objectInputStream = new ObjectInputStream(client.getInputStream());
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new ConnectionError("Cannot connect to new client : " + getName());
        }
        initReactions();
    }

    private void initReactions() {
        reactions.put("login", () -> connectionsManager.connectUser((String) clientMessageData.get(0), sessionID));
        reactions.put("logout", () -> connectionsManager.disconnectUser(sessionID));
        reactions.put("list", () -> connectionsManager.requestForParticipantsList(sessionID));
        reactions.put("text", () -> connectionsManager.chatMessageNotification((String) clientMessageData.get(0), sessionID));
    }

    private CTSMessage readClientMessage() throws Exception {
        CTSMessage message = null;
        if (protocol.equals("Basic")) {
            try {
                message = (CTSMessage) objectInputStream.readObject();
            } catch (SocketException e) {
                e.printStackTrace();
            } 
            catch (ClassNotFoundException | IOException | NullPointerException e) {
                // e.printStackTrace();
                throw e;
            }
        }
        if (protocol.equals("XML")) {
            String XMLMessage = (String) objectInputStream.readObject();
            ConverterFactory converterFactory = new ClientMessageConvFactory();
            message = converterFactory.convertFromSerializableXMLtoCM(XMLMessage);
        }
        return message;
    }

    @Override
    public void run() {
        while (! this.isInterrupted()) {
            try {
                CTSMessage message = readClientMessage();
                clientMessageData = message.getData();
                reactions.get(message.getName()).run();
            } catch (Exception e) {
                connectionsManager.disconnectUser(sessionID);
            }
        }
        System.out.println("connection " + sessionID + " interrupted");
    }

    public void sendMessage(STCMessage message) {
        if (protocol.equals("Basic")) {
            try {
                objectOutputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (protocol.equals("XML")) {
            ConverterFactory converterFactory = new ServerMessageConvFactory();
            String strMessage;
            try {
                strMessage = converterFactory.convertToSerializableXML(message.getName(), message.getData());
            } catch (ConvertionException e) {
                e.printStackTrace();
                return;
            }
            try {
                objectOutputStream.writeObject(strMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
