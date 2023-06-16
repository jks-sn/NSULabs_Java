package ru.nsu.ccfit.berkaev.server.chathistory;

import ru.nsu.ccfit.berkaev.constants.ServerConstants;
import ru.nsu.ccfit.berkaev.constants.SharedConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class FileData implements Serializable {

    private final byte[] data;
    private final int messagesCount;

    public FileData(byte[] data) {
        this.data = data;
        String s = new String(data);
        String[] arrBuf = s.split(SharedConstants.DELIMITER_NEW_LINE);
        messagesCount = arrBuf.length;
    }

    public ArrayList<Object> getDataWithOffset(int offset) {
        if (offset < ServerConstants.ZERO_OFFSET) offset = ServerConstants.ZERO_OFFSET;
        if (offset > messagesCount) offset = messagesCount;
        String s = new String(data);
        String[] buf = s.split(SharedConstants.DELIMITER_NEW_LINE);
        return new ArrayList<>(Arrays.asList(buf).subList(offset, buf.length));
    }
}
