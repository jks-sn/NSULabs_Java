package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

import java.util.Arrays;

public abstract class Operation {
    protected Object[] args;
    protected CalculatorStack context;
    public Operation (CalculatorStack context,Object... args) {
        this.context = context;
        this.args = args;
    }

    public abstract void exec() throws OperatorException;
}
