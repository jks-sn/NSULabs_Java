package ru.nsu.ccfit.berkaev.XMLConverter.ClientToXML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ru.nsu.ccfit.berkaev.XMLConverter.Converter;
import ru.nsu.ccfit.berkaev.ctsmessages.CTSMessage;
import ru.nsu.ccfit.berkaev.ctsmessages.LogoutMessage;
import ru.nsu.ccfit.berkaev.exceptions.ConvertionException;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

import static ru.nsu.ccfit.berkaev.constants.ErrorConstants.UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE;
import static ru.nsu.ccfit.berkaev.constants.SharedConstants.PATH_TO_XML_LOGOUT_TEMPLATE;

public class LogoutMessageConverter extends Converter {

    @Override
    public String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException {
        File xmlFile = new File(PATH_TO_XML_LOGOUT_TEMPLATE);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConvertionException(e.getMessage());
        }
        return serializeDocument(document);
    }

    @Override
    public CTSMessage convertFromSerializableXMLtoCM(Document serializedXML) {
        return new LogoutMessage();
    }

    @Override
    public STCMessage convertFromSerializableXMLtoSM(Document serializedXML) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE);
    }
}
