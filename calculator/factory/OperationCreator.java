package calculator.factory;

import calculator.operations.Operation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class OperationCreator {
    public static Operation getOperation(String nameOperation) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return ConfigParser.getOperationClass(nameOperation);
    }
}
