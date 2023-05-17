package carfactory.logger;

import carfactory.carparts.Car;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    private static Logger loggerFile;
    private static Logger loggerConsole;

    public MyLogger(String name)
    {
        loggerConsole  = Logger.getLogger(name);
        loggerFile = Logger.getLogger(name);
        FileHandler fh;
        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("log.txt");
            loggerFile.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);


        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
    public void info(String message)
    {

        loggerFile.info(message);
        loggerConsole.info(message);
    }

    public void fine(String message) {
        loggerFile.fine(message);
        loggerConsole.fine(message);
    }
}
