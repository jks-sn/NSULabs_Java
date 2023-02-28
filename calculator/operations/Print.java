package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Print extends Operation
{
    public Print(CalculatorStack context, Object... args) {
        super(context, args);
    }

    @Override
    public void exec() throws OperatorException {
        if(args.length > 0)
            throw new OperatorException("Too many arguments in " + this.getClass().getSimpleName().toLowerCase() + " command!!!");

        System.out.println("" + context.peek());
    }
}