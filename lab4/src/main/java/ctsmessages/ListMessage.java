package ctsmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static constants.SharedConstants.listMessage;

public class ListMessage implements CTSMessage, Serializable {

    public ListMessage() {}

    @Override
    public String getName() {
        return listMessage;
    }

    @Override
    public ArrayList<Object> getData() {
        return null;
    }
}
