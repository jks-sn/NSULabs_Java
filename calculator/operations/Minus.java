package calculator.operations;

import calculator.logic.CalculatorStack;
import calculator.exceptions.OperatorException;

public class Minus extends Operation {
    public Minus(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 0;
        this.numberVariablesFromStack = 2;
    }

    @Override
    public void exec() throws OperatorException {
        if (args.length > numberArguments)
            throw new OperatorException("Error: Wrong numbers of arguments " + this.getClass().getSimpleName().toLowerCase() + " operation");
        if (context.getStackLength() < numberVariablesFromStack)
            throw new OperatorException("Error: Not enough value in stack");
        double valueFirst = context.pop();
        double valueSecond = context.pop();
        context.push(valueSecond - valueFirst);
    }
}
