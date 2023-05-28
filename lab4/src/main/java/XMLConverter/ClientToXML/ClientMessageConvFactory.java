package XMLConverter.ClientToXML;

import java.util.ArrayList;

import XMLConverter.Converter;
import XMLConverter.ConverterFactory;
import exceptions.ConvertionException;

import static constants.ErrorConstants.nameAttributeName;
import static constants.ServerSocketConstants.*;

public class ClientMessageConvFactory extends ConverterFactory {

    @Override
    protected Converter createConverter(String messageName, ArrayList<Object> params) {
        return parseConverterName(messageName);
    }

    @Override
    protected Converter createConverter(String serializedXML) throws ConvertionException {
        return parseConverterName(Converter.deserializeDocument(serializedXML).getDocumentElement().getAttribute(nameAttributeName));
    }
    protected Converter parseConverterName(String converterName){
        switch (converterName) {
            case loginCommandName -> {
                return new LoginMessageConverter();
            }
            case logoutCommandName -> {
                return new LogoutMessageConverter();
            }
            case textCommandName -> {
                return new ChatMessageConverter();
            }
            case listCommandName -> {
                return new ListMessageConverter();
            }
            default -> {
                return null;
            }
        }
    }
}
