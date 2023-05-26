package connection.csmessage;

import connection.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class ListMessage implements Message, Serializable {
    private final String messageName = "list";

    public ListMessage() {}

    @Override
    public String getName() {
        return messageName;
    }

    @Override
    public ArrayList<Object> getData() {
        return null;
    }
}