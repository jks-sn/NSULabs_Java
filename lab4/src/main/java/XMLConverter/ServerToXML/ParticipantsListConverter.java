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
import stcmessages.FilledListMessage;
import stcmessages.STCMessage;

import static constants.ErrorConstants.unsupportedOperationConversionExceptionMessage;
import static constants.SharedConstants.dataName;
import static constants.SharedConstants.pathToXMLParticipantsListServerReplyTemplate;

public class ParticipantsListConverter extends Converter {

    @Override
    public String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException {
        File xmlFile = new File(pathToXMLParticipantsListServerReplyTemplate);
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
        for (Object object : params) {
            Element newDataElement = document.createElement(dataName);
            newDataElement.setTextContent((String) object);
            root.appendChild(newDataElement);
        }
        return serializeDocument(document);
    }

    @Override
    public CTSMessage convertFromSerializableXMLtoCM(Document serializedXML) {
        throw new UnsupportedOperationException(unsupportedOperationConversionExceptionMessage);
    }

    @Override
    public STCMessage convertFromSerializableXMLtoSM(Document serializedXML) {
        ArrayList<String> list = new ArrayList<>();
        NodeList children = serializedXML.getDocumentElement().getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            String tmp = children.item(i).getTextContent();
            if (!tmp.contains(" ") && tmp.length() > 0 && !tmp.contains("\n")) 
                list.add(tmp);
        }
        return new FilledListMessage(list);
    }
    
}