package ru.nsu.ccfit.berkaev.XMLConverter;

import java.util.ArrayList;

import ru.nsu.ccfit.berkaev.ctsmessages.CTSMessage;
import ru.nsu.ccfit.berkaev.exceptions.ConvertionException;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

public abstract class ConverterFactory {

    public String convertToSerializableXML(String messageName, ArrayList<Object> params) throws ConvertionException {
        Converter converter = createConverter(messageName, params);
        return converter.convertToSerializableXML(params);
    }

    public CTSMessage convertFromSerializableXMLtoCM(String serializedXML) throws ConvertionException {
        Converter converter = createConverter(serializedXML);
        return converter.convertFromSerializableXMLtoCM(Converter.deserializeDocument(serializedXML));
    }

    public STCMessage convertFromSerializableXMLtoSM(String serializedXML) throws ConvertionException {
        Converter converter = createConverter(serializedXML);
        return converter.convertFromSerializableXMLtoSM(Converter.deserializeDocument(serializedXML));
    }

    protected abstract Converter createConverter(String messageName, ArrayList<Object> params);
    protected abstract Converter createConverter(String serializedXML) throws ConvertionException;
}
