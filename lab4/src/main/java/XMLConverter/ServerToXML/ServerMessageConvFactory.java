package XMLConverter.ServerToXML;

import java.util.ArrayList;

import XMLConverter.Converter;
import XMLConverter.ConverterFactory;
import exceptions.ConvertionException;

import static constants.ErrorConstants.NAME_ATTRIBUTE_NAME;
import static constants.SharedConstants.*;

public class ServerMessageConvFactory extends ConverterFactory {
    
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
            case LOGIN_STATUS_MESSAGE -> {
                return new LoginStatusConverter();
            }
            case CHAT_HISTORY_MESSAGE -> {
                return new ChatHistoryConverter();
            }
            case ERROR_COMMAND_NAME -> {
                return new ErrorMessageConverter();
            }
            case GET_PARTICIPANT_LIST_COMMAND_NAME -> {
                return new ParticipantsListConverter();
            }
            default -> {
                return null;
            }
        }
    }
}
