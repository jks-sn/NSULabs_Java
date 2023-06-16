package ru.nsu.ccfit.berkaev.XMLConverter.ServerToXML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ru.nsu.ccfit.berkaev.constants.SharedConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.nsu.ccfit.berkaev.XMLConverter.Converter;
import ru.nsu.ccfit.berkaev.ctsmessages.CTSMessage;
import ru.nsu.ccfit.berkaev.exceptions.ConvertionException;
import ru.nsu.ccfit.berkaev.stcmessages.FilledListMessage;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

import static ru.nsu.ccfit.berkaev.constants.ErrorConstants.UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE;
import static ru.nsu.ccfit.berkaev.constants.SharedConstants.*;

public class ParticipantsListConverter extends Converter {

    @Override
    public String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException {
        File xmlFile = new File(PATH_TO_XML_PARTICIPANTS_LIST_SERVER_REPLY_TEMPLATE);
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
            Element newDataElement = document.createElement(DATA_NAME);
            newDataElement.setTextContent((String) object);
            root.appendChild(newDataElement);
        }
        return serializeDocument(document);
    }

    @Override
    public CTSMessage convertFromSerializableXMLtoCM(Document serializedXML) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE);
    }

    @Override
    public STCMessage convertFromSerializableXMLtoSM(Document serializedXML) {
        ArrayList<String> list = new ArrayList<>();
        NodeList children = serializedXML.getDocumentElement().getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            String tmp = children.item(i).getTextContent();
            if (!tmp.contains(DELIMITER_NEW_WORD) && tmp.length() > 0 && !tmp.contains(SharedConstants.DELIMITER_NEW_LINE))
                list.add(tmp);
        }
        return new FilledListMessage(list);
    }
    
}
