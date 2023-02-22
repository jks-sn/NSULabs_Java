package calculator.operations;

import calculator.logic.CalculatorStack;
import calculator.exceptions.OperatorException;

public class Plus extends Operation
{
    @Override
    public void exec() throws OperatorException {
        if(args.length > 0)
            throw new OperatorException("Too many arguments in " + this.getClass().getSimpleName().toLowerCase() + " command!!!");
        if(CalculatorStack.getStackLength() < 2)
            throw new OperatorException("Not enough variables in stack!!!");
        double valueFirst = CalculatorStack.pop();
        double valueSecond = CalculatorStack.pop();
        CalculatorStack.push(valueSecond + valueFirst);
    }
}
