package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Multipy extends Operation {
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
