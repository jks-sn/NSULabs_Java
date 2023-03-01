package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

import static calculator.exceptions.ExceptionConstants.OPERATION;
import static calculator.exceptions.ExceptionConstants.WRONG_NUMBER_ARGUMENTS;

public class Push extends Operation {
    public Push(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 1;
    }

    @Override
    public void exec() throws OperatorException {
        if(args.length != numberArguments)
        {
            throw new OperatorException(OPERATION, WRONG_NUMBER_ARGUMENTS);
        }
        try {
            context.push(Double.parseDouble((String) args[0]));
        } catch (NumberFormatException e) {
            context.push((String) args[0]);
        }
    }
}
