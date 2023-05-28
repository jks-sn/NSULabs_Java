package stcmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static constants.SharedConstants.getParticipantListCommandName;

public class FilledListMessage implements STCMessage, Serializable {

    private final ArrayList<Object> data = new ArrayList<>();

    public FilledListMessage(ArrayList<String> newData) {
        this.data.addAll(newData);
    }

    @Override
    public String getName() {
        return getParticipantListCommandName;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
