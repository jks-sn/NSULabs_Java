package server.chathistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import static constants.ServerConstants.ZERO_OFFSET;
import static constants.SharedConstants.*;

public class FileData implements Serializable {

    private final byte[] data;
    private final int messagesCount;

    public FileData(byte[] data) {
        this.data = data;
        String s = new String(data);
        String[] arrBuf = s.split(DELIMITER_NEW_LINE);
        messagesCount = arrBuf.length;
    }

    public ArrayList<Object> getDataWithOffset(int offset) {
        if (offset < ZERO_OFFSET) offset = ZERO_OFFSET;
        if (offset > messagesCount) offset = messagesCount;
        String s = new String(data);
        String[] buf = s.split(DELIMITER_NEW_LINE);
        return new ArrayList<>(Arrays.asList(buf).subList(offset, buf.length));
    }
}
