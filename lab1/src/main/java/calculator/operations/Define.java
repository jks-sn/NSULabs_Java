package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

import static calculator.exceptions.ExceptionConstants.*;
import static calculator.utils.ArgChecker.isDouble;

public class Define extends Operation {

    public Define(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 2;
    }

    @Override
    public void exec() throws OperatorException {
        if (args.length != numberArguments) {
            throw new OperatorException(OPERATION, WRONG_NUMBER_ARGUMENTS);
        }
        try {
            if(isDouble((String)args[0]))
                throw new NumberFormatException();
            context.createVariable((String) args[0], (Double.parseDouble((String) args[1])));
        } catch (NumberFormatException e) {
            throw new OperatorException(OPERATION, WRONG_ARGUMENTS_DEFINE);
        }
    }
}
