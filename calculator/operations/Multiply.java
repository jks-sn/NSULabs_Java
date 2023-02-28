package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Multiply extends Operation {
    public Multiply(CalculatorStack context, Object... args) {
        super(context, args);
    }

    @Override
    public void exec() throws OperatorException {
        if(args.length > 0)
            throw new OperatorException("Too many arguments in " + this.getClass().getSimpleName().toLowerCase() + " operation");
        if(context.getStackLength() < 2)
            throw new OperatorException("Not enough variables in stack!!!");
        double valueFirst = context.pop();
        double valueSecond = context.pop();
        context.push(valueSecond * valueFirst);
    }
}
