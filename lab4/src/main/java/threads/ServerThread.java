package threads;

import exceptions.ConnectionError;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerThread extends Thread {
    private ConnectionsManager connectionsManager;
    private Integer sessionID;
    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    private Map<String, Runnable> reactions = new HashMap<>();
    private ArrayList<Object> clientMessageData;
    private String protocol;
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
        reactions.put("login", new Runnable() {
            @Override
            public void run() {
                connectionsManager.connectUser((String) clientMessageData.get(0), sessionID);
            }
        });
        reactions.put("logout", new Runnable() {
            @Override
            public void run() {
                connectionsManager.disconnectUser(sessionID);
            }
        });
        reactions.put("list", new Runnable() {
            @Override
            public void run() {
                connectionsManager.requestForParticipantsList(sessionID);
            }
        });
        reactions.put("text", new Runnable() {
            @Override
            public void run() {
                connectionsManager.chatMessageNotification((String) clientMessageData.get(0), sessionID);
            }
        });
    }
}
