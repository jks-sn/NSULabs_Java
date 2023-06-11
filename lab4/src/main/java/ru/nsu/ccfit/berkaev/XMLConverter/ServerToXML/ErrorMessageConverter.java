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
import ru.nsu.ccfit.berkaev.stcmessages.ErrorMessage;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

import static ru.nsu.ccfit.berkaev.constants.ErrorConstants.UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE;
import static ru.nsu.ccfit.berkaev.constants.SharedConstants.*;

public class ErrorMessageConverter extends Converter {

    @Override
    public String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException {
        File xmlFile = new File(PATH_TO_XML_ERROR_TEMPLATE);
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
        String reason = serializedXML.getDocumentElement().getChildNodes().item(FIRST_ELEMENT).getTextContent();
        reason = reason.replace(SharedConstants.DELIMITER_NEW_LINE, NOTHING);
        reason = reason.replace(DELIMITER_NEW_WORD, NOTHING);
        return new ErrorMessage(reason);
    }

    @Override
    public CTSMessage convertFromSerializableXMLtoCM(Document serializedXML) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_CONVERSION_EXCEPTION_MESSAGE);
    }
    
}
