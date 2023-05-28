package XMLConverter.ServerToXML;

import java.util.ArrayList;

import XMLConverter.Converter;
import XMLConverter.ConverterFactory;
import exceptions.ConvertionException;

import static constants.ErrorConstants.nameAttributeName;
import static constants.SharedConstants.*;

public class ServerMessageConvFactory extends ConverterFactory {
    
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
            case loginStatusMessage -> {
                return new LoginStatusConverter();
            }
            case chatHistoryMessage -> {
                return new ChatHistoryConverter();
            }
            case errorCommandName -> {
                return new ErrorMessageConverter();
            }
            case getParticipantListCommandName -> {
                return new ParticipantsListConverter();
            }
            default -> {
                return null;
            }
        }
    }
}
