package srs.calculator.operations;

import srs.calculator.logic.CalculatorStack;
import srs.calculator.exceptions.OperatorException;

import static srs.calculator.exceptions.ExceptionConstants.*;

public class Plus extends Operation {
    public Plus(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 0;
    }

    @Override
    public void exec() throws OperatorException {
        if (args.length > numberArguments)
            throw new OperatorException(OPERATION, WRONG_NUMBER_ARGUMENTS);
        if (context.getStackLength() < numberVariablesFromStack)
            throw new OperatorException(OPERATION, LOW_STACK);
        double valueFirst = context.pop();
        double valueSecond = context.pop();
        context.push(valueSecond + valueFirst);
    }
}
