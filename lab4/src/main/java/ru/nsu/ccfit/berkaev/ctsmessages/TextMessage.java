package ru.nsu.ccfit.berkaev.ctsmessages;

import ru.nsu.ccfit.berkaev.constants.SharedConstants;

import java.io.Serializable;
import java.util.ArrayList;

public class TextMessage implements CTSMessage, Serializable{

    private final ArrayList<Object> data = new ArrayList<>();

    public TextMessage(String text) {
        data.add(text);
    }

    @Override
    public String getName() {
        return SharedConstants.TEXT_MESSAGE;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
}
