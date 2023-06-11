package ru.nsu.ccfit.berkaev.ctsmessages;

import ru.nsu.ccfit.berkaev.constants.SharedConstants;

import java.io.Serializable;
import java.util.ArrayList;

public class ListMessage implements CTSMessage, Serializable {

    public ListMessage() {}

    @Override
    public String getName() {
        return SharedConstants.LIST_MESSAGE;
    }

    @Override
    public ArrayList<Object> getData() {
        return null;
    }
}
