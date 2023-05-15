package calculator.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import calculator.exceptions.FactoryException;
import calculator.logic.CalculatorStack;
import calculator.operations.Operation;

import static calculator.constants.CalcConstants.PROPETIES_PATH;
import static calculator.exceptions.ExceptionConstants.*;

public class ConfigParser {
    public Operation getOperationClass(CalculatorStack context, String nameOperation, Object... args) throws FactoryException {
        Properties prop = new Properties();
        try {

            InputStream stream = ConfigParser.class.getResourceAsStream(PROPETIES_PATH);
            prop.load(stream);
            String className = prop.getProperty(nameOperation);
            if (className == null) //check
            {
                throw new FactoryException(PROPERTY, WRONG_OPERATION);
            }
            Constructor<Operation> constructor;
            Class<Operation> commandClass = (Class<Operation>) Class.forName(className);
            constructor = commandClass.getConstructor(CalculatorStack.class, Object[].class);
            return constructor.newInstance(context, args);
        } catch (NullPointerException | IOException e) {
            throw new FactoryException(PROPERTY, PROPERTY_PROBLEM);
        } catch (NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new FactoryException(OPERATION, BAD_OPERATION + CANT_GET_OPERATION_CONSTRUCTOR);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FactoryException(OPERATION, BAD_OPERATION + CANT_OPERATION_CREATE);
        }
    }
}
