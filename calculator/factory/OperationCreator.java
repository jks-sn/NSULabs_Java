package calculator.factory;

import calculator.operations.Operation;

public abstract class OperationCreator {
    public static Operation getOperation(String nameOperation)
    {
        return (Operation) Loader.getOperationClass(name).getConstructor().newInstance();
    }
}
