package srs.calculator.factory;

import srs.calculator.exceptions.FactoryException;
import srs.calculator.logic.CalculatorStack;
import srs.calculator.operations.Operation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class OperationCreator {
    private final CalculatorStack context;

    public OperationCreator(CalculatorStack context) {
        this.context = context;
    }


    public Operation getOperation(String nameOperation, Object... args) throws FactoryException {
        ConfigParser configParser = new ConfigParser();
            return configParser.getOperationClass(context, nameOperation, args);
    }
}
