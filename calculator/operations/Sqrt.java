package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Sqrt extends Operation {
    public void exec() throws OperatorException {
        if(args.length > 0)
            throw new OperatorException("Too many arguments in " + this.getClass().getSimpleName().toLowerCase() + " operation");
        if(CalculatorStack.getStackLength() < 1)
            throw new OperatorException("Not enough variables in stack!!!");
        CalculatorStack.push(Math.sqrt(CalculatorStack.pop()));
    }
}
