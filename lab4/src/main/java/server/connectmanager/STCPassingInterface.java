package server.connectmanager;

import stcmessages.STCMessage;

public interface STCPassingInterface {
    void sendMessage(Integer sessionID, STCMessage message);
}
