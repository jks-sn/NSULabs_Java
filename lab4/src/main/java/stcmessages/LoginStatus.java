package stcmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static constants.SharedConstants.LOGIN_STATUS_COMMAND_NAME;

public class LoginStatus implements STCMessage, Serializable {

    private final ArrayList<Object> data = new ArrayList<>();

    public LoginStatus(String reason, Boolean isSuccessful) {
        data.add(isSuccessful);
        data.add(reason);
    }

    @Override
    public String getName() {
        return LOGIN_STATUS_COMMAND_NAME;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
