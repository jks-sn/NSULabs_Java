package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

import static calculator.exceptions.ExceptionConstants.*;

public class Divide extends Operation {
    public Divide(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 0;
        this.numberVariablesFromStack = 2;
    }

    @Override
    public void exec() throws OperatorException {
        if(args.length > numberArguments)
            throw new OperatorException(OPERATION, WRONG_NUMBER_ARGUMENTS);
        if(context.getStackLength() < numberVariablesFromStack)
            throw new OperatorException(OPERATION, LOW_STACK);
        double valueFirst = context.pop();
        double valueSecond = context.pop();
        if(valueSecond == 0)
            throw new OperatorException(OPERATION,DIVISION_BY_ZERO);
        context.push(valueSecond / valueFirst);
    }
}
