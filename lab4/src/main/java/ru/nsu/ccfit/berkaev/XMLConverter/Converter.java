package ru.nsu.ccfit.berkaev.XMLConverter;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import ru.nsu.ccfit.berkaev.ctsmessages.CTSMessage;
import ru.nsu.ccfit.berkaev.exceptions.ConvertionException;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;

import static ru.nsu.ccfit.berkaev.constants.XMLConstants.XML_YES;

public abstract class Converter {
    
    public abstract String convertToSerializableXML(ArrayList<Object> params) throws ConvertionException;
    public abstract CTSMessage convertFromSerializableXMLtoCM(Document serializedXML);
    public abstract STCMessage convertFromSerializableXMLtoSM(Document serializedXML);
    
    protected String serializeDocument(Document document) throws ConvertionException {
    try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, XML_YES);
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            
            return writer.toString();
        } catch (TransformerException e) {
            throw new ConvertionException(e.getMessage());
        }
    }
    
    public static Document deserializeDocument(String serializedDocument) throws ConvertionException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            return builder.parse(new ByteArrayInputStream(serializedDocument.getBytes()));
        } catch (Exception e) {
            throw new ConvertionException(e.getMessage());
        }
    }
}
