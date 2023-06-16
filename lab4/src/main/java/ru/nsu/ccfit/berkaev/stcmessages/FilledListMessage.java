package ru.nsu.ccfit.berkaev.stcmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static ru.nsu.ccfit.berkaev.constants.SharedConstants.GET_PARTICIPANT_LIST_COMMAND_NAME;

public class FilledListMessage implements STCMessage, Serializable {

    private final ArrayList<Object> data = new ArrayList<>();

    public FilledListMessage(ArrayList<String> newData) {
        this.data.addAll(newData);
    }

    @Override
    public String getName() {
        return GET_PARTICIPANT_LIST_COMMAND_NAME;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
