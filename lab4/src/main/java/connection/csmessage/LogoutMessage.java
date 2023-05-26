package connection.csmessage;

import connection.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class LogoutMessage implements Serializable, Message {

    private final String messageName = "logout";

    public LogoutMessage() {}

    @Override
    public String getName() {
        return messageName;
    }

    @Override
    public ArrayList<Object> getData() {
        return null;
    }
}
