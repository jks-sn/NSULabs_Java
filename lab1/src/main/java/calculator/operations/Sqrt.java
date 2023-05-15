package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

import static calculator.exceptions.ExceptionConstants.*;

public class Sqrt extends Operation {
    public Sqrt(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 0;
        this.numberVariablesFromStack = 1;
    }

    public void exec() throws OperatorException {
        if(args.length > numberArguments)
            throw new OperatorException(OPERATION, WRONG_NUMBER_ARGUMENTS);
        if(context.getStackLength() < numberVariablesFromStack)
            throw new OperatorException(OPERATION, LOW_STACK);
        context.push(Math.sqrt(context.pop()));
    }
}