package ru.nsu.ccfit.berkaev.constants;

public class SharedConstants {
    public static final String SERVER_PROPERTIES_PATH = "/serverConfig.properties";
    public static final String CLIENT_PROPERTIES_PATH = "/clientConfig.properties";
    public static final String CHAT_HISTORY_PATH = "serverHistory.csv";
    public static final String SERVER_LOG_PATH = "Server.log";
    public static final String PATH_TO_XML_CHAT_TEMPLATE = "src/main/XMLTemplates/chat/clientMessage.xml";
    public static final String PATH_TO_XML_LIST_MESSAGE_TEMPLATE = "src/main/XMLTemplates/participantsList/clientMessage.xml";
    public static final String PATH_TO_XML_LOGIN_MESSAGE_TEMPLATE = "src/main/XMLTemplates/registration/clientMessage.xml";
    public static final String PATH_TO_XML_LOGOUT_TEMPLATE = "src/main/XMLTemplates/logout/clientMessage.xml";
    public static final String PATH_TO_XML_ERROR_TEMPLATE = "src/main/XMLTemplates/errors/serverErrorMessage.xml";
    public static final String PATH_TO_XML_BROADCAST_TEMPLATE = "src/main/XMLTemplates/chat/serverBroadcast.xml";
    public static final String PATH_TO_XML_SERVER_REPLY_TEMPLATE = "src/main/XMLTemplates/registration/serverReply.xml";
    public static final String PATH_TO_XML_PARTICIPANTS_LIST_SERVER_REPLY_TEMPLATE = "src/main/XMLTemplates/participantsList/serverreply.xml";
    public static final String PROTOCOL_BASIC_NAME = "Basic";
    public static final String PROTOCOL_XML_NAME = "XML";
    public static final String LIST_MESSAGE = "list";
    public static final String LOGIN_MESSAGE = "login";
    public static final String LOGIN_STATUS_MESSAGE = "LoginStatus";
    public static final String CHAT_HISTORY_MESSAGE = "chatHistory";
    public static final String LOGOUT_MESSAGE = "logout";
    public static final String TEXT_MESSAGE = "text";
    public static final String LOG_SALE_PROPERTY_NAME = "logSale";
    public static final String PORT_PROPERTY_NAME = "port";
    public static final String RECENT_MESSAGES_PROPERTY_NAME = "recentMessages";
    public static final String PROTOCOL_NAME = "protocol";
    public static final String CONNECTIONS_MANAGER_NAME = "Manager";
    public static final String CANT_CREATE_CONNECT_MESSAGE = "Cannot create connection";
    public static final String LOGIN_SUCCESS_MESSAGE = "success";
    public static final String DATA_SUCCESSFUL_NAME = "success";
    public static final String DATA_NAME = "data";
    public static String interruptedConnectionMessage(Integer id)
    {
        return "connection " + id + " interrupted";
    }
    public static String newUserConnectedMessage(String username, Integer sessionID){return "New user with name " + username + " and ID " + sessionID + " had connected to server";}
    public static String newUserDisconnectedMessage(String username, Integer sessionID){return "New user with name " + username + " and ID " + sessionID + " had disconnected from server";}
    public static final String GET_CHAT_HISTORY_COMMAND_NAME = "chatHistory";
    public static final String LOGIN_STATUS_COMMAND_NAME = "LoginStatus";
    public static final String GET_PARTICIPANT_LIST_COMMAND_NAME = "filledList";
    public static final String ERROR_COMMAND_NAME = "error";
    public static final String DELIMITER_NEW_LINE = "\n";
    public static final String DELIMITER_NEW_WORD = " ";
    public static final String DELIMITER_DATA = ":";
    public static final int FIRST_ELEMENT = 0;
    public static final String NOTHING = "";
    public static final int DEFAULT_CURRENT_CHAT_POINTER = 0;

}
