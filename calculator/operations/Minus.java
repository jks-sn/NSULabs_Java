package calculator.operations;

import calculator.logic.CalculatorStack;
import calculator.exceptions.OperatorException;

public class Minus extends Operation {
    @Override
    public void exec() throws OperatorException {
        if(args.length > 0)
            throw new OperatorException("Error: Wrong numbers of arguments " + this.getClass().getSimpleName().toLowerCase() + " operation");
        if(CalculatorStack.getStackLength() < 2)
            throw new OperatorException("Error: Not enough value in stack");
        double valueFirst = CalculatorStack.pop();
        double valueSecond = CalculatorStack.pop();
        CalculatorStack.push(valueSecond - valueFirst);
    }
}
