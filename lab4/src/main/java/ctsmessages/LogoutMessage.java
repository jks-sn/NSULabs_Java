package ctsmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static constants.SharedConstants.logoutMessage;

public class LogoutMessage implements Serializable, CTSMessage {

    public LogoutMessage() {}

    @Override
    public String getName() {
        return logoutMessage;
    }

    @Override
    public ArrayList<Object> getData() {
        return null;
    }
}
