package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

import static calculator.exceptions.ExceptionConstants.*;

public class Pop extends Operation {

    public Pop(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 0;
        this.numberVariablesFromStack = 1;
    }

    @Override
    public void exec() throws OperatorException {
        if(args.length != numberArguments)
        {
            throw new OperatorException(OPERATION, WRONG_NUMBER_ARGUMENTS);
        }
        if(context.getStackLength() < numberVariablesFromStack) {
            throw new OperatorException(OPERATION, LOW_STACK);
        }
        context.pop();
    }
}
