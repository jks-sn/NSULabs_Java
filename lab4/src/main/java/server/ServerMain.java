package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

import exceptions.DuplicateNameException;
import exceptions.IllegalRequestException;
import properties.PropertiesReader;
import server.chathistory.ChatHistory;
import server.chathistory.FileData;
import server.connectmanager.ConnectionsManager;
import server.participants.ParticipantsList;
import stcmessages.ErrorMessage;
import stcmessages.FilledListMessage;
import stcmessages.LoginStatus;

public class ServerMain {

    private final ConnectionsManager connectionsManager;
    private final ParticipantsList participantsList;
    private final ChatHistory chatHistory;

    private final HashMap<Integer, Integer> offsets;
    private Integer currentChatPointer = 0;

    private final int availableRecentMessagesCount;

    private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ServerMain.class.getName());

    public ServerMain() throws IOException {
        participantsList = new ParticipantsList();
        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.getAllProperties("/serverConfig.properties");
        String protocol = propertiesReader.getProtocol();
        ServerSocket serverSocket = new ServerSocket(propertiesReader.getPort());
        availableRecentMessagesCount = propertiesReader.getRecentMessagesCount();
        if (propertiesReader.getLogFlag()) {
            LogManager.getLogManager().reset();
            java.util.logging.Logger globalLogger = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
            globalLogger.setLevel(Level.FINEST);

            FileHandler fh = new FileHandler("Server.log", 6000, 1, false);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.FINEST);
        }
        logger.info("Server have been started");
        connectionsManager = new ConnectionsManager(serverSocket, this, protocol);
        connectionsManager.start();
        chatHistory = new ChatHistory("serverHistory.csv");
        offsets = new HashMap<>();
    }

    public void registrationUser(Integer sessionID, String username) throws IOException, IllegalRequestException {
        try {
            username = username.replace("\n", "");
            username = username.replace(" ", "");
            participantsList.addNewParcipiant(username, sessionID);
            offsets.put(sessionID, currentChatPointer);
        } catch (DuplicateNameException e) {
            connectionsManager.sendMessage(sessionID, new ErrorMessage("user with this name already exists"));
            return;
        }
        logger.info("New user with name " + username + " and ID " + sessionID + " had connected to server");
        LoginStatus reply = new LoginStatus("success", true);
        connectionsManager.sendMessage(sessionID, reply);
        currentChatPointer++;
        chatHistory.addSystemMessage("New user with name " + username + " and ID " + sessionID + " had connected to server");
        refreshChat();
    }

    public void deleteUser(Integer sessionID) throws IllegalRequestException {
        try {
            logger.info("New user with name " + participantsList.getNameByID(sessionID) + " and ID " + sessionID + " had disconnected from server");
            currentChatPointer++;
            chatHistory.addSystemMessage("User with name " + participantsList.getNameByID(sessionID) + " and ID " + sessionID + " had disconnected from server");
            participantsList.removeParticipant(sessionID);
            offsets.remove(sessionID);
            refreshChat();
        } catch (DuplicateNameException e) {
            throw new IllegalRequestException(Integer.toString(sessionID));
        }
    }

    public void sendParticipantsTable(Integer sessionID) throws IllegalRequestException {
        try {
            logger.info("Send participants list to " + participantsList.getNameByID(sessionID));
        } catch (DuplicateNameException e) {
            e.printStackTrace();
        }
        FilledListMessage message = new FilledListMessage(participantsList.getPrintableParticipantsNames());
        connectionsManager.sendMessage(sessionID, message);
    }

    public void addMessageToChatHistory(String message, Integer sessionID) {
        message = message.replace("\n", "");
        message = message.replace(" ", "");
        try {
            logger.info("User " + participantsList.getNameByID(sessionID) + " sent message");
            currentChatPointer++;
            chatHistory.addMessageFromUser(participantsList.getNameByID(sessionID), message);
            refreshChat();
        } catch (DuplicateNameException e) {
            e.printStackTrace();
        }
    }

    private void refreshChat() {
        FileData fileData = chatHistory.getHistory();
        if (fileData != null) connectionsManager.broadcastMessage(offsets, fileData, availableRecentMessagesCount, chatHistory.getStartLen());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        @SuppressWarnings("unused")
        ServerMain serverMain = new ServerMain();
    }
}