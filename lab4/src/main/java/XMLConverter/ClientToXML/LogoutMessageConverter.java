package XMLConverter.ClientToXML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import XMLConverter.Converter;
import ctsmessages.CTSMessage;
import ctsmessages.LogoutMessage;
import exceptions.ConvertionException;
import stcmessages.STCMessage;

import static constants.ErrorConstants.unsupportedOperationConversionExceptionMessage;
import static constants.SharedConstants.pathToXMLLogoutTemplate;

public class LogoutMessageConverter extends Converter {

    @Override
    public String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException {
        File xmlFile = new File(pathToXMLLogoutTemplate);
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
        throw new UnsupportedOperationException(unsupportedOperationConversionExceptionMessage);
    }
}
