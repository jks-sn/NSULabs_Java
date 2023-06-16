package ru.nsu.ccfit.berkaev.ctsmessages;

import ru.nsu.ccfit.berkaev.constants.SharedConstants;

import java.io.Serializable;
import java.util.ArrayList;

public class LogoutMessage implements Serializable, CTSMessage {

    public LogoutMessage() {}

    @Override
    public String getName() {
        return SharedConstants.LOGOUT_MESSAGE;
    }

    @Override
    public ArrayList<Object> getData() {
        return null;
    }
}
