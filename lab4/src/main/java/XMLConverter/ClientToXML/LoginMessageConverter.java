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

public class LoginMessageConverter extends Converter {

    private String pathToTemplate = "src/main/XMLTemplates/registration/clientMessage.xml";

    @Override
    public String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException {
        File xmlFile = new File(pathToTemplate);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document document = null;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConvertionException(e.getMessage());
        }

        Element root = document.getDocumentElement();
        NodeList children = root.getChildNodes();
        children.item(0).setTextContent((String) params.get(0));
        return serializeDocument(document);
    }

    @Override
    public CTSMessage convertFromSerializableXMLtoCM(Document serializedXML) {
        String userName = serializedXML.getDocumentElement().getChildNodes().item(0).getTextContent();
        CTSMessage message = new LoginMessage(userName);
        return message;
    }

    @Override
    public STCMessage convertFromSerializableXMLtoSM(Document serializedXML) {
        throw new UnsupportedOperationException("Unsupported conversion");
    }
}
