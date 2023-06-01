package ctsmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static constants.SharedConstants.LIST_MESSAGE;

public class ListMessage implements CTSMessage, Serializable {

    public ListMessage() {}

    @Override
    public String getName() {
        return LIST_MESSAGE;
    }

    @Override
    public ArrayList<Object> getData() {
        return null;
    }
}
