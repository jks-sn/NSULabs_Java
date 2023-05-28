package ctsmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static constants.SharedConstants.textMessage;

public class TextMessage implements CTSMessage, Serializable{

    private final ArrayList<Object> data = new ArrayList<>();

    public TextMessage(String text) {
        data.add(text);
    }

    @Override
    public String getName() {
        return textMessage;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
