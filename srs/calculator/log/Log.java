package srs.calculator.log;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

import static srs.calculator.constants.LogConstants.*;

public class Log {
    private static Logger info;
    private static Logger err;
    private static Logger debug;

    private static boolean infoEnabled = true;
    private static boolean errEnabled = true;
    private static boolean debugEnabled = true;

    private static boolean logEnabled = true;

    public static void init() {
        File file = null;
        String resource = "/res/log4j2.properties";

        try {
            InputStream input = Log.class.getResourceAsStream(resource);
            file = File.createTempFile("temple", ".tmp");
            OutputStream out = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[BUFFER_SIZE];

            while (true) {
                assert input != null;
                if ((read = input.read(bytes)) == -1) break;
                out.write(bytes, 0, read);
            }
            out.close();
            file.deleteOnExit();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        assert file != null;
        System.setProperty("log4j.properties", file.getPath());


        info = LogManager.getLogger(NAME_LOGGER_INFO);
        err = LogManager.getLogger(NAME_LOGGER_DEBUG);
        debug = LogManager.getLogger(NAME_LOGGER_ERROR);
    }

    void enableLog() {
        Log.logEnabled = true;
    }

    void disableLog() {
        logEnabled = false;
    }

    void enableDebug() {
        debugEnabled = true;
    }

    void disableDebug() {
        debugEnabled = false;
    }

    void enableErr() {
        errEnabled = true;
    }

    void disableErr() {
        errEnabled = false;
    }

    public static <T extends Number> void info(T message) {
        info(message + "");
    }

    public static void info(String message) {
        log(LogType.INFO, message, null);
    }

    public static <T extends Number> void debug(T message) {
        debug(message + "");
    }

    public static void debug(String message) {
        log(LogType.DEBUG, message, null);
    }

    public static <T extends Number> void error(T message) {
        error(message + "");
    }

    public static void error(String message) {
        log(LogType.ERROR, message, null);
    }

    public static void error(String message, Throwable e) {
        log(LogType.ERROR, message, e);
    }

    public static void log(LogType type, String message, Throwable t) {
        if (!logEnabled)
            return;
        switch (type) {
            case DEBUG -> {
                if (!debugEnabled)
                    return;
                debug.debug(message, t);
            }
            case ERROR -> {
                if (!errEnabled)
                    return;
                err.warn(message, t);
            }
            case INFO -> {
                if (!infoEnabled)
                    return;
                info.info(message, t);
            }
        }
    }

    public enum LogType {
        INFO,
        DEBUG,
        ERROR
    }
}
