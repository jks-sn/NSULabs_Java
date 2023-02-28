package calculator.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import calculator.exceptions.FactoryException;
import calculator.logic.CalculatorStack;
import calculator.operations.Operation;

public class ConfigParser {
    public Operation getOperationClass(CalculatorStack context, String nameOperation, Object... args) throws FactoryException {
            Properties prop = new Properties();
            try {

                InputStream stream = ConfigParser.class.getResourceAsStream("Operations.properties");
                prop.load(stream);
            }
            catch(IOException e)
            {
                throw new FactoryException();
            }

        try {
            String className = prop.getProperty(nameOperation);
            Class<Operation> commandClass = (Class<Operation>) Class.forName(className);
            Constructor<Operation> constructor = commandClass.getConstructor(CalculatorStack.class, Object[].class);
            return constructor.newInstance(context, args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
