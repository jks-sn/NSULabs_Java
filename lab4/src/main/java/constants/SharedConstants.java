package constants;

public class SharedConstants {
    public static final String serverPropertiesPath = "/serverConfig.properties";
    public static final String clientPropertiesPath = "/clientConfig.properties";
    public static final String chatHistoryPath = "serverHistory.csv";
    public static final String serverLogPath = "Server.log";
    public static final String pathToXMLChatTemplate = "src/main/XMLTemplates/chat/clientMessage.xml";
    public static final String pathToXMLListMessageTemplate = "src/main/XMLTemplates/participantsList/clientMessage.xml";
    public static final String pathToXMLLoginMessageTemplate = "src/main/XMLTemplates/registration/clientMessage.xml";
    public static final String pathToXMLLogoutTemplate = "src/main/XMLTemplates/logout/clientMessage.xml";
    public static final String pathToXMLErrorTemplate = "src/main/XMLTemplates/errors/serverErrorMessage.xml";
    public static final String pathToXMLBroadcastTemplate = "src/main/XMLTemplates/chat/serverBroadcast.xml";
    public static final String pathToXMLServerReplyTemplate = "src/main/XMLTemplates/registration/serverReply.xml";
    public static final String pathToXMLParticipantsListServerReplyTemplate = "src/main/XMLTemplates/participantsList/serverreply.xml";
    public static final String protocolBasicName = "Basic";
    public static final String protocolXMLName = "XML";
    public static final String listMessage = "list";
    public static final String loginMessage = "login";
    public static final String loginStatusMessage = "LoginStatus";
    public static final String chatHistoryMessage = "chatHistory";
    public static final String logoutMessage = "logout";
    public static final String textMessage = "text";
    public static final String logSalePropertyName = "logSale";
    public static final String portPropertyName = "port";
    public static final String recentMessagesPropertyName = "recentMessages";
    public static final String protocolName = "protocol";
    public static final String connectionsManagerName = "Manager";
    public static final String cantCreateConnectMessage = "Cannot create connection";
    public static final String LoginSuccessMessage = "success";
    public static final String dataSuccessfulName = "success";
    public static final String dataName = "data";
    public static String interruptedConnectionMessage(Integer id)
    {
        return "connection " + id + " interrupted";
    }
    public static String newUserConnectedMessage(String username, Integer sessionID){return "New user with name " + username + " and ID " + sessionID + " had connected to server";}
    public static String newUserDisconnectedMessage(String username, Integer sessionID){return "New user with name " + username + " and ID " + sessionID + " had disconnected from server";}
    public static final String getChatHistoryCommandName = "chatHistory";
    public static final String loginStatusCommandName = "LoginStatus";
    public static final String getParticipantListCommandName = "filledList";
    public static final String errorCommandName = "error";
    public static final String delimiterNewLine = "\n";
    public static final String delimiterNewWord = " ";
    public static final String delimiterData= ":";
    public static final int firstElement = 0;
    public static final String nothing = "";
    public static final int defaultCurrentChatPointer = 0;

}
