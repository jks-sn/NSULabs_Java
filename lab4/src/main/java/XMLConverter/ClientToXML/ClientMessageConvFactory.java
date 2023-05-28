package XMLConverter.ClientToXML;

import java.util.ArrayList;

import XMLConverter.Converter;
import XMLConverter.ConverterFactory;
import exceptions.ConvertionException;

public class ClientMessageConvFactory extends ConverterFactory {

    @Override
    protected Converter createConverter(String messageName, ArrayList<Object> params) {
        return parseConverterName(messageName);
    }

    @Override
    protected Converter createConverter(String serializedXML) throws ConvertionException {
        return parseConverterName(Converter.deserializeDocument(serializedXML).getDocumentElement().getAttribute("name"));
    }
    protected Converter parseConverterName(String converterName){
        switch (converterName) {
            case "login" -> {
                return new LoginMessageConverter();
            }
            case "logout" -> {
                return new LogoutMessageConverter();
            }
            case "text" -> {
                return new ChatMessageConverter();
            }
            case "list" -> {
                return new ListMessageConverter();
            }
            default -> {
                return null;
            }
        }
    }
}
