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
        double argument = context.pop();
        if(argument < 0 || Double.isNaN(argument) || argument == Double.POSITIVE_INFINITY) {
            context.push(argument);
            throw new OperatorException(OPERATION, WRONG_ARGUMENT_SQRT);
        }
        context.push(Math.sqrt(context.pop()));
    }
}
