package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Pop extends Operation {
    public Pop(CalculatorStack context, Object... args) {
        super(context, args);
    }

    @Override
    public void exec() throws OperatorException {
        if(args.length != 0)
        {
            throw new OperatorException("Too many arguments in " + this.getClass().getSimpleName().toLowerCase() + " operation");
        }
        if(context.getStackLength() < 1) {
            throw new OperatorException("Not enough variables in stack!!!");
        }
        context.pop();
    }
}
