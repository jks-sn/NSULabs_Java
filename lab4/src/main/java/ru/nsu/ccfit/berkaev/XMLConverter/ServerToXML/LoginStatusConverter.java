package ru.nsu.ccfit.berkaev.XMLConverter.ServerToXML;

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

import ru.nsu.ccfit.berkaev.XMLConverter.Converter;
import ru.nsu.ccfit.berkaev.ctsmessages.CTSMessage;
import ru.nsu.ccfit.berkaev.exceptions.ConvertionException;
import ru.nsu.ccfit.berkaev.stcmessages.LoginStatus;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

import static ru.nsu.ccfit.berkaev.constants.ErrorConstants.UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE;
import static ru.nsu.ccfit.berkaev.constants.SharedConstants.*;

public class LoginStatusConverter extends Converter {

    @Override
    public String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException {
        File xmlFile = new File(PATH_TO_XML_SERVER_REPLY_TEMPLATE);
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
        children.item(FIRST_ELEMENT).setTextContent(params.get(FIRST_ELEMENT).toString());
        return serializeDocument(document);
    }

    @Override
    public STCMessage convertFromSerializableXMLtoSM(Document serializedXML) {
        String is_successful = serializedXML.getDocumentElement().getElementsByTagName(DATA_SUCCESSFUL_NAME).item(FIRST_ELEMENT).getTextContent();
        return new LoginStatus(NOTHING, Boolean.parseBoolean(is_successful));
    }

    @Override
    public CTSMessage convertFromSerializableXMLtoCM(Document serializedXML) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE);
    }
}
