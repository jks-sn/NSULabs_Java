package XMLConverter.ClientToXML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import XMLConverter.Converter;
import ctsmessages.CTSMessage;
import ctsmessages.LoginMessage;
import exceptions.ConvertionException;
import stcmessages.STCMessage;

import static constants.ErrorConstants.UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE;
import static constants.SharedConstants.FIRST_ELEMENT;
import static constants.SharedConstants.PATH_TO_XML_LOGIN_MESSAGE_TEMPLATE;

public class LoginMessageConverter extends Converter {

    @Override
    public String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException {
        File xmlFile = new File(PATH_TO_XML_LOGIN_MESSAGE_TEMPLATE);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConvertionException(e.getMessage());
        }

        Element root = document.getDocumentElement();
        NodeList children = root.getChildNodes();
        children.item(FIRST_ELEMENT).setTextContent((String) params.get(FIRST_ELEMENT));
        return serializeDocument(document);
    }

    @Override
    public CTSMessage convertFromSerializableXMLtoCM(Document serializedXML) {
        String userName = serializedXML.getDocumentElement().getChildNodes().item(FIRST_ELEMENT).getTextContent();
        return new LoginMessage(userName);
    }

    @Override
    public STCMessage convertFromSerializableXMLtoSM(Document serializedXML) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE);
    }
}
