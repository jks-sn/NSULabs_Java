package server.connectmanager;

import exceptions.ConnectionError;
import exceptions.IllegalRequestException;
import server.ChatServerThread;
import server.ServerMain;
import server.chathistory.FileData;
import stcmessages.ChatHistoryMessage;
import stcmessages.ErrorMessage;
import stcmessages.STCMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static constants.SharedConstants.cantCreateConnectMessage;
import static constants.SharedConstants.connectionsManagerName;

public class ConnectionsManager extends Thread implements CTSPassingInterface, STCPassingInterface {

    private final ServerMain server;
    private final ServerSocket serverSocket;
    private final Map<Integer, ChatServerThread> connections = new HashMap<>();

    private Integer nextID = 0;
    private final String protocol;

    public ConnectionsManager(ServerSocket serverSock, ServerMain server, String protocol) {
        this.protocol = protocol;
        this.server = server;
        this.serverSocket = serverSock;
        setName(connectionsManagerName);
    }

    @Override
    public void run() {
        while (! this.isInterrupted()) {
            try {
                Socket newClient = serverSocket.accept();
                try {
                    ChatServerThread newConnection = new ChatServerThread(this, newClient, nextID, protocol);
                    connections.put(nextID, newConnection);
                    nextID++;
                    newConnection.start();
                } catch (ConnectionError e) {
                    new ObjectOutputStream(newClient.getOutputStream()).writeObject(new ErrorMessage(cantCreateConnectMessage));
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void connectUser(String username, Integer sessionID) {
        try {
            server.registrationUser(sessionID, username);
        } catch (IOException | IllegalRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void disconnectUser(Integer sessionID) {
        try {
            server.deleteUser(sessionID);
        } catch (IllegalRequestException ignored) {}
        connections.get(sessionID).interrupt();
        connections.remove(sessionID);
    }

    @Override
    public synchronized void requestForParticipantsList(Integer sessionID) {
        try {
            server.sendParticipantsTable(sessionID);
        } catch (IllegalRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void sendMessage(Integer sessionID, STCMessage message) {
        connections.get(sessionID).sendMessage(message);
    }

    @Override
    public synchronized void chatMessageNotification(String message, Integer sessionID) {
        server.addMessageToChatHistory(message, sessionID);
    }

    public void broadcastMessage(HashMap<Integer, Integer> offsets, FileData message, int recentMessagesCount, int startOffset) {
        try {
            for (Integer i : connections.keySet()) {
                if (offsets.get(i) != null) sendMessage(i, new ChatHistoryMessage(message, offsets.get(i) - recentMessagesCount + startOffset));
            }
        } catch (NullPointerException ignored) {}
    }
}
