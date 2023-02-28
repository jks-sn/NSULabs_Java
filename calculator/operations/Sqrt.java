package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Sqrt extends Operation {
    public Sqrt(CalculatorStack context, Object... args) {
        super(context, args);
    }

    public void exec() throws OperatorException {
        if(args.length > 0)
            throw new OperatorException("Too many arguments in " + this.getClass().getSimpleName().toLowerCase() + " operation");
        if(context.getStackLength() < 1)
            throw new OperatorException("Not enough variables in stack!!!");
        context.push(Math.sqrt(context.pop()));
    }
}
