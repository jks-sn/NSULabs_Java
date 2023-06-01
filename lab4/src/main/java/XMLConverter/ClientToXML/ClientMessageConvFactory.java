package XMLConverter.ClientToXML;

import java.util.ArrayList;

import XMLConverter.Converter;
import XMLConverter.ConverterFactory;
import exceptions.ConvertionException;

import static constants.ErrorConstants.NAME_ATTRIBUTE_NAME;
import static constants.ServerSocketConstants.*;

public class ClientMessageConvFactory extends ConverterFactory {

    @Override
    protected Converter createConverter(String messageName, ArrayList<Object> params) {
        return parseConverterName(messageName);
    }

    @Override
    protected Converter createConverter(String serializedXML) throws ConvertionException {
        return parseConverterName(Converter.deserializeDocument(serializedXML).getDocumentElement().getAttribute(NAME_ATTRIBUTE_NAME));
    }
    protected Converter parseConverterName(String converterName){
        switch (converterName) {
            case LOGIN_COMMAND_NAME -> {
                return new LoginMessageConverter();
            }
            case LOGOUT_COMMAND_NAME -> {
                return new LogoutMessageConverter();
            }
            case TEXT_COMMAND_NAME -> {
                return new ChatMessageConverter();
            }
            case LIST_COMMAND_NAME -> {
                return new ListMessageConverter();
            }
            default -> {
                return null;
            }
        }
    }
}
