package ru.nsu.ccfit.berkaev.ctsmessages;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientKeepAlive implements CTSMessage, Serializable {

    public ClientKeepAlive() {}

    @Override
    public String getName() {
        return "ClientKeepAlive";
    }

    @Override
    public ArrayList<Object> getData() {
        return null;
    }
}
