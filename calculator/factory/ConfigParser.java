package calculator.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import calculator.operations.Operation;

public class ConfigParser {
    public static Operation getOperationClass(String nameOperation) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String[] parsed = nameOperation.split(" ");
        String commandName = parsed[0];
        InputStream stream = ConfigParser.class.getResourceAsStream("Operations.properties");
        Properties prop = new Properties();
        prop.load(stream);
        String className = prop.getProperty(nameOperation);
        Class<Operation> commandClass = (Class<Operation>) Class.forName(className);
        Constructor<Operation> constructor = commandClass.getConstructor(Object[].class);
        Object[] args = new Object[parsed.length];
        for (int i = 0; i < parsed.length; i++) {
            args[i] = parsed[i];
        }
        return constructor.newInstance(args);
    }
}
