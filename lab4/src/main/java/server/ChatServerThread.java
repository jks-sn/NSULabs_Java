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

import static constants.ErrorConstants.CANT_CONNECT_NEW_CLIENT_MESSAGE;
import static constants.ServerSocketConstants.*;
import static constants.SharedConstants.*;

public class ChatServerThread extends Thread {

    private final ConnectionsManager connectionsManager;
    private final Integer sessionID;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    private final Map<String, Runnable> reactions = new HashMap<>();
    private ArrayList<Object> clientMessageData;

    private final String protocol;

    public ChatServerThread(ConnectionsManager connectionsManager, Socket client, Integer sessionID, String protocol) throws ConnectionError {
        setName(SERVER_THREAD_NAME + sessionID.toString());
        this.sessionID = sessionID;
        this.protocol = protocol;
        this.connectionsManager = connectionsManager;
        try {
            objectInputStream = new ObjectInputStream(client.getInputStream());
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new ConnectionError(CANT_CONNECT_NEW_CLIENT_MESSAGE + getName());
        }
        initReactions();
    }

    private void initReactions() {
        reactions.put(LOGIN_COMMAND_NAME, () -> connectionsManager.connectUser((String) clientMessageData.get(0), sessionID));
        reactions.put(LOGOUT_COMMAND_NAME, () -> connectionsManager.disconnectUser(sessionID));
        reactions.put(LIST_COMMAND_NAME, () -> connectionsManager.requestForParticipantsList(sessionID));
        reactions.put(TEXT_COMMAND_NAME, () -> connectionsManager.chatMessageNotification((String) clientMessageData.get(0), sessionID));
    }

    private CTSMessage readClientMessage() throws Exception {
        CTSMessage message = null;
        if (protocol.equals(PROTOCOL_BASIC_NAME)) {
            try {
                message = (CTSMessage) objectInputStream.readObject();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        if (protocol.equals(PROTOCOL_XML_NAME)) {
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
        System.out.println(interruptedConnectionMessage(sessionID));
    }

    public void sendMessage(STCMessage message) {
        if (protocol.equals(PROTOCOL_BASIC_NAME)) {
            try {
                objectOutputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (protocol.equals(PROTOCOL_XML_NAME)) {
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
