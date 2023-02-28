package calculator.factory;

import calculator.logic.CalculatorStack;
import calculator.operations.Operation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class OperationCreator {
    private CalculatorStack context;

    public OperationCreator(CalculatorStack context) {
        this.context = context;
    }

    public Operation getOperation(String nameOperation, Object... args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ConfigParser configParser = new ConfigParser();
        final var operationClass = configParser.getOperationClass(context, nameOperation, args);
        return operationClass;
    }
}
