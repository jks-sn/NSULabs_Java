package connection.csmessage;

import connection.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class TextMessage implements Serializable, Message {

    private final ArrayList<Object> data = new ArrayList<>();

    public TextMessage(String text) {
        data.add(text);
    }

    @Override
    public String getName() {
        return "text";
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
