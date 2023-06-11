package ru.nsu.ccfit.berkaev.server.connectmanager;

public interface CTSPassingInterface {
    void connectUser(String username, Integer sessionID);
    void disconnectUser(Integer sessionID);
    void requestForParticipantsList(Integer sessionID);
    void chatMessageNotification(String message, Integer sessionID);
}
