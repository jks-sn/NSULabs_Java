package ru.nsu.ccfit.berkaev.stcmessages;

import java.io.Serializable;
import java.util.ArrayList;

import static ru.nsu.ccfit.berkaev.constants.SharedConstants.ERROR_COMMAND_NAME;

public class ErrorMessage implements STCMessage, Serializable {

    private final ArrayList<Object> data = new ArrayList<>();

    public ErrorMessage(String error) {
        this.data.add(error);
    }

    @Override
    public String getName() {
        return ERROR_COMMAND_NAME;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
