package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
            System.out.println("ERROR DURING LOADING FILE " + inputPropFile);
        }
    }

    public boolean getLogFlag() {
        return Boolean.parseBoolean(prop.getProperty("logSale"));
    }

    public int getPort() {
        return Integer.parseInt(prop.getProperty("port"));
    }

    public int getRecentMessagesCount() {
        return Integer.parseInt(prop.getProperty("recentMessages"));
    }

    public String getProtocol() {
        return String.valueOf(prop.getProperty("protocol"));
    }
}
