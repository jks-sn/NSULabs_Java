package stcmessages;

import java.io.Serializable;
import java.util.ArrayList;

public class FilledListMessage implements STCMessage, Serializable {

    private final ArrayList<Object> data = new ArrayList<>();

    public FilledListMessage(ArrayList<String> newData) {
        this.data.addAll(newData);
    }

    @Override
    public String getName() {
        return "filledList";
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
