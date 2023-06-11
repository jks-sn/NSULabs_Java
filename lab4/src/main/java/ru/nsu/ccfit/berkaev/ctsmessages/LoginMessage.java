package ru.nsu.ccfit.berkaev.ctsmessages;

import ru.nsu.ccfit.berkaev.constants.SharedConstants;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginMessage implements Serializable, CTSMessage {

    private final ArrayList<Object> data = new ArrayList<>();

    public LoginMessage(String username) {
        data.add(username);
    }

    public LoginMessage() {}

    @Override
    public String getName() {
        return SharedConstants.LOGIN_MESSAGE;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
