package ru.nsu.ccfit.berkaev.protocolInterface;

import ru.nsu.ccfit.berkaev.client.Client;
import ru.nsu.ccfit.berkaev.ctsmessages.CTSMessage;
import ru.nsu.ccfit.berkaev.exceptions.NoActiveSocketException;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BasicMessageProtocolFabric implements MessageProtocolFabric {
    @Override
    public void sendMessage(CTSMessage message, Socket socket, ObjectOutputStream objectOutputStream, Client client) throws NoActiveSocketException, IOException {
        if (socket == null) {
            throw new NoActiveSocketException();
        }
        objectOutputStream.writeObject(message);
    }

    @Override
    public STCMessage getMessage(ObjectInputStream objectInputStream, Client client) throws IOException, ClassNotFoundException {
        return (STCMessage) objectInputStream.readObject();
    }
}
