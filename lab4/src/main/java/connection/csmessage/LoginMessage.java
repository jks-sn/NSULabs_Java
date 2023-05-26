package connection.csmessage;

import connection.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginMessage implements Serializable, Message {

    private final ArrayList<Object> data = new ArrayList<>();

    public LoginMessage(String username) {
        data.add(username);
    }

    public LoginMessage() {}

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
