package client;

import client.connection.ReusableSocket;
import client.view.ClientGUI;
import ctsmessages.ListMessage;
import ctsmessages.LoginMessage;
import ctsmessages.LogoutMessage;
import ctsmessages.TextMessage;
import exceptions.ConnectionError;
import exceptions.NoActiveSocketException;
import exceptions.SocketStillOpenedException;
import properties.PropertiesReader;

import java.io.IOException;
import java.util.ArrayList;

import static constants.ClientConstants.*;
import static constants.ClientSocketConstants.beginningDATA;

public class Client {

    private LoginMessage userInfo = null;
    private String host = null;
    private int port = defaultPort;
    private boolean isConnected = false;

    private final ReusableSocket socket;

    private final ClientGUI clientGUI;

    public Client() {
        Thread.currentThread().setName(clientName);
        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.getAllProperties(pathToClientPropertiesFile);
        String protocol = propertiesReader.getProtocol();
        this.socket = new ReusableSocket(this, protocol);
        this.socket.start();
        clientGUI = new ClientGUI(this);
    }

    public synchronized void connect() throws ConnectionError {
        try {
            if (! socket.isConnected()) {
                socket.initNewConnection(host, port);
                socket.sendMessage(userInfo);
            }
        }
        catch (SocketStillOpenedException e) {
            throw new ConnectionError(activeConnectionClientMessage);
        } catch (IOException e) {
            throw new ConnectionError(unknownPortHostClientMessage);
        } catch (NoActiveSocketException ignored) {}

        try {
            wait();
        } catch (InterruptedException e) {
            closeClient();
            Thread.currentThread().interrupt();
            return;
        }
        if (isConnected) openChat();
        else processError(lockedUserNameError1);
    }

    public synchronized void setRegistrationStatus(Boolean status) {
        isConnected = status;
        notify();
    }

    public void processError(String err) {
        if (err.equals(connectionResetClientMessage)) {
            System.out.println(resetMessage);
            try {
                clientGUI.closeChat();
                socket.closeConnection();
            } catch (IOException | NoActiveSocketException e) {
                processError(err);
            }
        }
        clientGUI.displayError(err);
    }

    private void openChat() {
        clientGUI.openChat();
    }

    public void disconnect() throws IOException, NoActiveSocketException {
        if (socket.isConnected()) {
            clientGUI.closeChat();
            socket.sendMessage(new LogoutMessage());
            socket.closeConnection();
        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUserInfo(LoginMessage userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserName() {
        return (String) userInfo.getData().get(beginningDATA);
    }

    public void closeClient() {
        if (socket.isConnected())
            try {
                socket.closeConnection();
            } catch (IOException | NoActiveSocketException e) {
                clientGUI.displayError(e.getMessage());
            }
        socket.interrupt();
    }

    public void getParticipantsTable() throws IOException, NoActiveSocketException {
        socket.sendMessage(new ListMessage());
    }

    public void showParticipantsTable(ArrayList<Object> participants) {
        clientGUI.showParticipants(participants);
    }

    public void refreshChatView(ArrayList<Object> messages) {
        clientGUI.displayMessages(messages);
    }

    public void sendChatMessageToServer(String message) {
        try {
            socket.sendMessage(new TextMessage(message));
        } catch (IOException | NoActiveSocketException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, ConnectionError {
        Client client = new Client();
    }
}
