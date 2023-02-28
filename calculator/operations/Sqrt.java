package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Sqrt extends Operation {
    public Sqrt(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 0;
        this.numberVariablesFromStack = 1;
    }

    public void exec() throws OperatorException {
        if(args.length > numberArguments)
            throw new OperatorException("Too many arguments in " + this.getClass().getSimpleName().toLowerCase() + " operation");
        if(context.getStackLength() < numberVariablesFromStack)
            throw new OperatorException("Not enough variables in stack!!!");
        context.push(Math.sqrt(context.pop()));
    }
}
