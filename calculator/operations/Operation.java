package calculator.operations;

public abstract class Operation {
    protected Object[] args = new Object[0];
    public Operation setArgs(Object... args)
    {
        this.args = args;
    }
}
