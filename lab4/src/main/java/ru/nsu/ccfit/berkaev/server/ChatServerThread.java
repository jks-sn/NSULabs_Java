package ru.nsu.ccfit.berkaev.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.nsu.ccfit.berkaev.XMLConverter.ConverterFactory;
import ru.nsu.ccfit.berkaev.XMLConverter.ClientToXML.ClientMessageConvFactory;
import ru.nsu.ccfit.berkaev.XMLConverter.ServerToXML.ServerMessageConvFactory;
import ru.nsu.ccfit.berkaev.ctsmessages.CTSMessage;
import ru.nsu.ccfit.berkaev.exceptions.ConnectionError;
import ru.nsu.ccfit.berkaev.exceptions.ConvertionException;
import ru.nsu.ccfit.berkaev.constants.ErrorConstants;
import ru.nsu.ccfit.berkaev.constants.ServerSocketConstants;
import ru.nsu.ccfit.berkaev.constants.SharedConstants;
import ru.nsu.ccfit.berkaev.server.connectmanager.ConnectionsManager;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

public class ChatServerThread extends Thread {

    private final ConnectionsManager connectionsManager;
    private final Integer sessionID;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    private final Map<String, Runnable> reactions = new HashMap<>();
    private ArrayList<Object> clientMessageData;

    private final String protocol;

    public ChatServerThread(ConnectionsManager connectionsManager, Socket client, Integer sessionID, String protocol) throws ConnectionError {
        setName(ServerSocketConstants.SERVER_THREAD_NAME + sessionID.toString());
        this.sessionID = sessionID;
        this.protocol = protocol;
        this.connectionsManager = connectionsManager;
        try {
            objectInputStream = new ObjectInputStream(client.getInputStream());
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new ConnectionError(ErrorConstants.CANT_CONNECT_NEW_CLIENT_MESSAGE + getName());
        }
        initReactions();
    }

    private void initReactions() {
        reactions.put(ServerSocketConstants.LOGIN_COMMAND_NAME, () -> connectionsManager.connectUser((String) clientMessageData.get(0), sessionID));
        reactions.put(ServerSocketConstants.LOGOUT_COMMAND_NAME, () -> connectionsManager.disconnectUser(sessionID));
        reactions.put(ServerSocketConstants.LIST_COMMAND_NAME, () -> connectionsManager.requestForParticipantsList(sessionID));
        reactions.put(ServerSocketConstants.TEXT_COMMAND_NAME, () -> connectionsManager.chatMessageNotification((String) clientMessageData.get(0), sessionID));
    }

    private CTSMessage readClientMessage() throws Exception {
        CTSMessage message = null;
        if (protocol.equals(SharedConstants.PROTOCOL_BASIC_NAME)) {
            try {
                message = (CTSMessage) objectInputStream.readObject();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        if (protocol.equals(SharedConstants.PROTOCOL_XML_NAME)) {
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
        System.out.println(SharedConstants.interruptedConnectionMessage(sessionID));
    }

    public void sendMessage(STCMessage message) {
        if (protocol.equals(SharedConstants.PROTOCOL_BASIC_NAME)) {
            try {
                objectOutputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (protocol.equals(SharedConstants.PROTOCOL_XML_NAME)) {
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
