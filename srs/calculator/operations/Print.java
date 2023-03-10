package srs.calculator.operations;

import srs.calculator.exceptions.OperatorException;
import srs.calculator.logic.CalculatorStack;

import static srs.calculator.exceptions.ExceptionConstants.OPERATION;
import static srs.calculator.exceptions.ExceptionConstants.WRONG_NUMBER_ARGUMENTS;

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