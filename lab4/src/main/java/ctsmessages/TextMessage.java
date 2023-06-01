package ctsmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static constants.SharedConstants.TEXT_MESSAGE;

public class TextMessage implements CTSMessage, Serializable{

    private final ArrayList<Object> data = new ArrayList<>();

    public TextMessage(String text) {
        data.add(text);
    }

    @Override
    public String getName() {
        return TEXT_MESSAGE;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
