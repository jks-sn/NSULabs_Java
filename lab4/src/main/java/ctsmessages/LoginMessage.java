package ctsmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static constants.SharedConstants.loginMessage;

public class LoginMessage implements Serializable, CTSMessage {

    private final ArrayList<Object> data = new ArrayList<>();

    public LoginMessage(String username) {
        data.add(username);
    }

    public LoginMessage() {}

    @Override
    public String getName() {
        return loginMessage;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
