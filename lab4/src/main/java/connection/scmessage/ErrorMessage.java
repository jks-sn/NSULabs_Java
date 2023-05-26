package connection.scmessage;

import connection.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class ErrorMessage implements Serializable, Message {
    private final ArrayList<Object> data = new ArrayList<>();

    public ErrorMessage(String error) {
        this.data.add(error);
    }

    @Override
    public String getName() {
        return "error";
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
