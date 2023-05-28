package XMLConverter.ServerToXML;

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
import exceptions.ConvertionException;
import stcmessages.LoginStatus;
import stcmessages.STCMessage;

import static constants.ErrorConstants.unsupportedOperationConversionExceptionMessage;
import static constants.SharedConstants.dataSuccessfulName;
import static constants.SharedConstants.pathToXMLServerReplyTemplate;

public class LoginStatusConverter extends Converter {

    @Override
    public String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException {
        File xmlFile = new File(pathToXMLServerReplyTemplate);
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
        children.item(0).setTextContent(params.get(0).toString());
        return serializeDocument(document);
    }

    @Override
    public STCMessage convertFromSerializableXMLtoSM(Document serializedXML) {
        String is_successful = serializedXML.getDocumentElement().getElementsByTagName(dataSuccessfulName).item(0).getTextContent();
        return new LoginStatus("", Boolean.parseBoolean(is_successful));
    }

    @Override
    public CTSMessage convertFromSerializableXMLtoCM(Document serializedXML) {
        throw new UnsupportedOperationException(unsupportedOperationConversionExceptionMessage);
    }
}
