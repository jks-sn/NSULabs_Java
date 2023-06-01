package ctsmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static constants.SharedConstants.LOGOUT_MESSAGE;

public class LogoutMessage implements Serializable, CTSMessage {

    public LogoutMessage() {}

    @Override
    public String getName() {
        return LOGOUT_MESSAGE;
    }

    @Override
    public ArrayList<Object> getData() {
        return null;
    }
}
