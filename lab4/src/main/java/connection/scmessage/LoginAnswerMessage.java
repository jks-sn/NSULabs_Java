package connection.scmessage;

import connection.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginAnswerMessage implements Serializable, Message {
    private final ArrayList<Object> data = new ArrayList<>();

    public LoginAnswerMessage(String reason, Boolean isSuccessful) {
        data.add(isSuccessful);
        data.add(reason);
    }

    @Override
    public String getName() {
        return "LoginStatus";
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
