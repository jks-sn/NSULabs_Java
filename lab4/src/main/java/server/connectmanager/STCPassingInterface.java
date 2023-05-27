package server.connectmanager;

import stcmessages.STCMessage;

public interface STCPassingInterface {
    public void sendMessage(Integer sessionID, STCMessage message);
}
