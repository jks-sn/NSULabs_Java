package ru.nsu.ccfit.berkaev.XMLConverter.ServerToXML;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Properties;

import ru.nsu.ccfit.berkaev.XMLConverter.Converter;
import ru.nsu.ccfit.berkaev.XMLConverter.ConverterFactory;
import ru.nsu.ccfit.berkaev.exceptions.ConvertionException;

import static ru.nsu.ccfit.berkaev.constants.ErrorConstants.LOADING_DATA_EXCEPTION_MESSAGE;
import static ru.nsu.ccfit.berkaev.constants.ErrorConstants.NAME_ATTRIBUTE_NAME;
import static ru.nsu.ccfit.berkaev.constants.XMLConstants.XMLCONVERTERS_PROPETIES_PATH;

public class ServerMessageConvFactory extends ConverterFactory {
    
    @Override
    protected Converter createConverter(String messageName, ArrayList<Object> params) {
        return parseConverterName(messageName);
    }

    @Override
    protected Converter createConverter(String serializedXML) throws ConvertionException {
        return parseConverterName(Converter.deserializeDocument(serializedXML).getDocumentElement().getAttribute(NAME_ATTRIBUTE_NAME));
    }
    protected Converter parseConverterName(String converterName) {
        Properties prop = new Properties();
        try {
            InputStream stream = ServerMessageConvFactory.class.getResourceAsStream(XMLCONVERTERS_PROPETIES_PATH);
            prop.load(stream);
            String className = prop.getProperty(converterName);
            if (className == null)
            {
                throw new ConvertionException(LOADING_DATA_EXCEPTION_MESSAGE);
            }
            return (Converter) Class.forName(className).getConstructor().newInstance();
        }
        catch(IOException | ConvertionException | ClassNotFoundException | InvocationTargetException |
              NoSuchMethodException | IllegalAccessException | InstantiationException e)
        {
            throw new RuntimeException(e);
        }
    }
}
