package ru.nsu.ccfit.berkaev.protocolInterface;

import ru.nsu.ccfit.berkaev.XMLConverter.ClientToXML.ClientMessageConvFactory;
import ru.nsu.ccfit.berkaev.XMLConverter.ConverterFactory;
import ru.nsu.ccfit.berkaev.XMLConverter.ServerToXML.ServerMessageConvFactory;
import ru.nsu.ccfit.berkaev.client.Client;
import ru.nsu.ccfit.berkaev.ctsmessages.CTSMessage;
import ru.nsu.ccfit.berkaev.exceptions.ConvertionException;
import ru.nsu.ccfit.berkaev.exceptions.NoActiveSocketException;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class XMLMessageProtocolFabric implements MessageProtocolFabric {
    @Override
    public void sendMessage(CTSMessage message, Socket socket, ObjectOutputStream objectOutputStream, Client client) throws NoActiveSocketException, IOException {
        if (socket == null) {
            throw new NoActiveSocketException();
        }
        ConverterFactory converterFactory = new ClientMessageConvFactory();
        String strMessage = null;
        try {
            strMessage = converterFactory.convertToSerializableXML(message.getName(), message.getData());
        } catch (ConvertionException e) {
            client.processError(e.getMessage());
        }
        objectOutputStream.writeObject(strMessage);
    }

    @Override
    public STCMessage getMessage(ObjectInputStream objectInputStream, Client client) throws IOException, ClassNotFoundException {
        STCMessage serverMessage = null;
        String xmlMessage = (String) objectInputStream.readObject();
        ConverterFactory converterFactory = new ServerMessageConvFactory();
        try {
            serverMessage = converterFactory.convertFromSerializableXMLtoSM(xmlMessage);
        } catch (ConvertionException e) {
            client.processError(e.getMessage());
        }
        return serverMessage;
    }
}
