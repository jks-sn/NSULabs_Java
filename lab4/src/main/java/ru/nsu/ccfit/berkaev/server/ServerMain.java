package ru.nsu.ccfit.berkaev.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

import ru.nsu.ccfit.berkaev.exceptions.DuplicateNameException;
import ru.nsu.ccfit.berkaev.exceptions.IllegalRequestException;
import ru.nsu.ccfit.berkaev.properties.PropertiesReader;
import ru.nsu.ccfit.berkaev.constants.ErrorConstants;
import ru.nsu.ccfit.berkaev.constants.LoggerConstants;
import ru.nsu.ccfit.berkaev.constants.SharedConstants;
import ru.nsu.ccfit.berkaev.server.chathistory.ChatHistory;
import ru.nsu.ccfit.berkaev.server.chathistory.FileData;
import ru.nsu.ccfit.berkaev.server.connectmanager.ConnectionsManager;
import ru.nsu.ccfit.berkaev.server.participants.ParticipantsList;
import ru.nsu.ccfit.berkaev.stcmessages.ErrorMessage;
import ru.nsu.ccfit.berkaev.stcmessages.FilledListMessage;
import ru.nsu.ccfit.berkaev.stcmessages.LoginStatus;

public class ServerMain {

    private final ConnectionsManager connectionsManager;
    private final ParticipantsList participantsList;
    private final ChatHistory chatHistory;

    private final HashMap<Integer, Integer> offsets;
    private Integer currentChatPointer = SharedConstants.DEFAULT_CURRENT_CHAT_POINTER;

    private final int availableRecentMessagesCount;

    private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ServerMain.class.getName());

    public ServerMain() throws IOException {
        participantsList = new ParticipantsList();
        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.getAllProperties(SharedConstants.SERVER_PROPERTIES_PATH);
        String protocol = propertiesReader.getProtocol();
        ServerSocket serverSocket = new ServerSocket(propertiesReader.getPort());
        availableRecentMessagesCount = propertiesReader.getRecentMessagesCount();
        if (propertiesReader.getLogFlag()) {
            LogManager.getLogManager().reset();
            java.util.logging.Logger globalLogger = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
            globalLogger.setLevel(Level.FINEST);

            FileHandler fh = new FileHandler(SharedConstants.SERVER_LOG_PATH, LoggerConstants.loggerFileLimit, LoggerConstants.loggerFileNumber, false);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.FINEST);
        }
        logger.info("Server have been started");
        connectionsManager = new ConnectionsManager(serverSocket, this, protocol);
        connectionsManager.start();
        chatHistory = new ChatHistory(SharedConstants.CHAT_HISTORY_PATH);
        offsets = new HashMap<>();
    }

    public void registrateUser(Integer sessionID, String username) throws IOException, IllegalRequestException {
        try {
            username = username.replace(SharedConstants.DELIMITER_NEW_LINE, SharedConstants.NOTHING);
            username = username.replace(SharedConstants.DELIMITER_NEW_WORD, SharedConstants.NOTHING);
            participantsList.addNewParcipiant(username, sessionID);
            offsets.put(sessionID, currentChatPointer);
        } catch (DuplicateNameException e) {
            connectionsManager.sendMessage(sessionID, new ErrorMessage(ErrorConstants.USER_ALREADY_CONNECTED_MESSAGE));
            return;
        }
        logger.info(SharedConstants.newUserConnectedMessage(username,sessionID));
        LoginStatus reply = new LoginStatus(SharedConstants.LOGIN_SUCCESS_MESSAGE, true);
        connectionsManager.sendMessage(sessionID, reply);
        currentChatPointer++;
        chatHistory.addSystemMessage(SharedConstants.newUserConnectedMessage(username,sessionID));
        refreshChat();
    }

    public void deleteUser(Integer sessionID) throws IllegalRequestException {
        try {
            logger.info(SharedConstants.newUserDisconnectedMessage(participantsList.getNameByID(sessionID),sessionID));
            currentChatPointer++;
            chatHistory.addSystemMessage(SharedConstants.newUserDisconnectedMessage(participantsList.getNameByID(sessionID),sessionID));
            participantsList.removeParticipant(sessionID);
            offsets.remove(sessionID);
            refreshChat();
        } catch (DuplicateNameException e) {
            throw new IllegalRequestException(Integer.toString(sessionID));
        }
    }
    public void deleteUser(Integer sessionID, String reason) throws IllegalRequestException {
        try {
            logger.info("New user whith name " + participantsList.getNameByID(sessionID) + " and ID " + String.valueOf(sessionID) + " had disconnected from server by timeout");
            currentChatPointer++;
            chatHistory.addSystemMessage("User whith name " + participantsList.getNameByID(sessionID) + " and ID " + String.valueOf(sessionID) + " had disconnected from server by timeout");
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
        message = message.replace(SharedConstants.DELIMITER_NEW_LINE, SharedConstants.NOTHING);
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
        ServerMain serverMain = new ServerMain();
    }
}