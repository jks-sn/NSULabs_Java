package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

import static calculator.exceptions.ExceptionConstants.OPERATION;
import static calculator.exceptions.ExceptionConstants.WRONG_NUMBER_ARGUMENTS;

public class Print extends Operation
{
    public Print(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 0;
    }

    @Override
    public void exec() throws OperatorException {
        if(args.length > numberArguments)
            throw new OperatorException(OPERATION, WRONG_NUMBER_ARGUMENTS);

        System.out.println("" + context.peek());
    }
}