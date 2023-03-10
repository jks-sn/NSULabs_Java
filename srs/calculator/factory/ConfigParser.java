package srs.calculator.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import srs.calculator.exceptions.FactoryException;
import srs.calculator.exceptions.OperatorException;
import srs.calculator.logic.CalculatorStack;
import srs.calculator.operations.Operation;

import static srs.calculator.constants.CalcConstants.PROPETIES_PATH;
import static srs.calculator.exceptions.ExceptionConstants.*;

public class ConfigParser {
    public Operation getOperationClass(CalculatorStack context, String nameOperation, Object... args) throws FactoryException {
        Properties prop = new Properties();
        try {

            InputStream stream = ConfigParser.class.getResourceAsStream(PROPETIES_PATH);
            prop.load(stream);
        } catch (NullPointerException | IOException e) {
            throw new FactoryException(PROPERTY, PROPERTY_PROBLEM);
        }

        String className = prop.getProperty(nameOperation);
        if (className == null) //check
        {
            throw new FactoryException(PROPERTY, WRONG_OPERATION);
        }
        Constructor<Operation> constructor;
        try {
            Class<Operation> commandClass = (Class<Operation>) Class.forName(className);
            constructor = commandClass.getConstructor(CalculatorStack.class, Object[].class);
        } catch (NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new FactoryException(OPERATION, BAD_OPERATION + CANT_GET_OPERATION_CONSTRUCTOR);
        }
        try {
            return constructor.newInstance(context, args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FactoryException(OPERATION, BAD_OPERATION + CANT_OPERATION_CREATE);
        }
    }
}
