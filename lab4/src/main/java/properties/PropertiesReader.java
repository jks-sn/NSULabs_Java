package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static constants.ErrorConstants.LOADING_DATA_EXCEPTION_MESSAGE;
import static constants.SharedConstants.*;

public class PropertiesReader {

    Properties prop;

    public PropertiesReader() {
        prop = new Properties();
    }

    public void getAllProperties(String propFileName) {

        InputStream inputPropFile;
        inputPropFile = PropertiesReader.class.getResourceAsStream(propFileName);
        try{
            prop.load(inputPropFile);
        }
        catch (IOException err) {
            assert inputPropFile != null;
            System.out.println(LOADING_DATA_EXCEPTION_MESSAGE + inputPropFile);
        }
    }

    public boolean getLogFlag() {
        return Boolean.parseBoolean(prop.getProperty(LOG_SALE_PROPERTY_NAME));
    }

    public int getPort() {
        return Integer.parseInt(prop.getProperty(PORT_PROPERTY_NAME));
    }

    public int getRecentMessagesCount() {
        return Integer.parseInt(prop.getProperty(RECENT_MESSAGES_PROPERTY_NAME));
    }

    public String getProtocol() {
        return String.valueOf(prop.getProperty(PROTOCOL_NAME));
    }
}
