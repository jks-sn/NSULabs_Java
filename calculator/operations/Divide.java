package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

import static calculator.exceptions.ExceptionConstants.DIVISION_BY_ZERO;

public class Divide extends Operation {
    public Divide(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 0;
        this.numberVariablesFromStack = 2;
    }

    @Override
    public void exec() throws OperatorException {
        if(args.length > numberArguments)
            throw new OperatorException("Too many arguments in " + this.getClass().getSimpleName().toLowerCase() + " operation");
        if(context.getStackLength() < numberVariablesFromStack)
            throw new OperatorException("Not enough variables in stack!!!");
        double valueFirst = context.pop();
        double valueSecond = context.pop();
        if(valueSecond == 0)
            throw new OperatorException(DIVISION_BY_ZERO);
        context.push(valueSecond / valueFirst);
    }
}
