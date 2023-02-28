package calculator.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import calculator.logic.CalculatorStack;
import calculator.operations.Operation;

public class ConfigParser {
    public Operation getOperationClass(CalculatorStack context, String nameOperation, Object... args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        InputStream stream = ConfigParser.class.getResourceAsStream("Operations.properties");
        Properties prop = new Properties();
        prop.load(stream);
        String className = prop.getProperty(nameOperation);
        Class<Operation> commandClass = (Class<Operation>) Class.forName(className);
        Constructor<Operation> constructor = commandClass.getConstructor(CalculatorStack.class, Object[].class);
        return constructor.newInstance(context,args);
    }
}
