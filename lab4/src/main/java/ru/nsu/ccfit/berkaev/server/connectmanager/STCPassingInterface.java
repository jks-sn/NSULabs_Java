package ru.nsu.ccfit.berkaev.server.connectmanager;

import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

public interface STCPassingInterface {
    void sendMessage(Integer sessionID, STCMessage message);
}
