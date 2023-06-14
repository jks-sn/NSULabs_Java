package ru.nsu.ccfit.berkaev.client;

import ru.nsu.ccfit.berkaev.client.connection.ClientSocket;
import ru.nsu.ccfit.berkaev.client.view.ClientGUI;
import ru.nsu.ccfit.berkaev.ctsmessages.ListMessage;
import ru.nsu.ccfit.berkaev.ctsmessages.LoginMessage;
import ru.nsu.ccfit.berkaev.ctsmessages.LogoutMessage;
import ru.nsu.ccfit.berkaev.ctsmessages.TextMessage;
import ru.nsu.ccfit.berkaev.exceptions.ConnectionError;
import ru.nsu.ccfit.berkaev.exceptions.NoActiveSocketException;
import ru.nsu.ccfit.berkaev.exceptions.SocketStillOpenedException;
import ru.nsu.ccfit.berkaev.properties.PropertiesReader;

import java.io.IOException;
import java.util.ArrayList;

import static ru.nsu.ccfit.berkaev.constants.ClientConstants.*;
import static ru.nsu.ccfit.berkaev.constants.ClientSocketConstants.BEGINNING_DATA;

public class Client {

    private LoginMessage userInfo = null;
    private String host = null;
    private int port = DEFAULT_PORT;
    private boolean isConnected = false;

    private final ClientSocket socket;

    private final ClientGUI clientGUI;

    public Client() {
        Thread.currentThread().setName(CLIENT_NAME);
        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.getAllProperties(PATH_TO_CLIENT_PROPERTIES_FILE);
        String protocol = propertiesReader.getProtocol();
        this.socket = new ClientSocket(this, protocol);
        this.socket.start();
        clientGUI = new ClientGUI(this);
    }

    public synchronized void connect() throws ConnectionError {
        try {
            if (!socket.isConnected()) {
                socket.initNewConnection(host, port);
                socket.sendMessage(userInfo);
            }
            wait();
        }
        catch (SocketStillOpenedException e) {
            throw new ConnectionError(ACTIVE_CONNECTION_CLIENT_MESSAGE);
        } catch (IOException e) {
            throw new ConnectionError(UNKNOWN_PORT_HOST_CLIENT_MESSAGE);
        } catch (NoActiveSocketException ignored) {}
        catch (InterruptedException e) {
            closeClient();
            Thread.currentThread().interrupt();
            return;
        }
        if (isConnected) openChat();
        else processError(LOCKED_USER_NAME_ERROR_1);
    }

    public synchronized void setRegistrationStatus(Boolean status) {
        isConnected = status;
        notify();
    }

    public void processError(String err) {
        clientGUI.displayError(err);
    }
    public void closeError(String err)
    {
        System.out.println(RESET_MESSAGE);
        try {
        clientGUI.closeChat();
        socket.closeConnection();
        } catch (IOException | NoActiveSocketException e) {
            processError(err);
        }
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
        return (String) userInfo.getData().get(BEGINNING_DATA);
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
