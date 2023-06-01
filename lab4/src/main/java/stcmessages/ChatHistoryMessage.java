package stcmessages;

import java.io.Serializable;
import java.util.ArrayList;

import server.chathistory.FileData;

import static constants.SharedConstants.GET_CHAT_HISTORY_COMMAND_NAME;

public class ChatHistoryMessage implements STCMessage, Serializable {

    private ArrayList<Object> data = new ArrayList<>();

    public ChatHistoryMessage(ArrayList<String> newData) {
        this.data.addAll(newData);
    }

    public ChatHistoryMessage(FileData fileData, Integer offset) {
        data = fileData.getDataWithOffset(offset);
    }

    @Override
    public String getName() {
        return GET_CHAT_HISTORY_COMMAND_NAME;
    }

    @Override
    public ArrayList<Object> getData() {
        return data;
    }
    
}
