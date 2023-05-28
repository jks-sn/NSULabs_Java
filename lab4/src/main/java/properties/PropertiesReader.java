package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static constants.ErrorConstants.loadingDataExceptionMessage;
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
            System.out.println(loadingDataExceptionMessage + inputPropFile);
        }
    }

    public boolean getLogFlag() {
        return Boolean.parseBoolean(prop.getProperty(logSalePropertyName));
    }

    public int getPort() {
        return Integer.parseInt(prop.getProperty(portPropertyName));
    }

    public int getRecentMessagesCount() {
        return Integer.parseInt(prop.getProperty(recentMessagesPropertyName));
    }

    public String getProtocol() {
        return String.valueOf(prop.getProperty(protocolName));
    }
}
