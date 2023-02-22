package calculator.operations;

import calculator.exceptions.OperatorException;

public abstract class Operation {
    protected Object[] args = new Object[0];
    public Operation setArgs(Object... args)
    {
        this.args = args;
        return this;
    }

    public abstract void exec() throws OperatorException;
}