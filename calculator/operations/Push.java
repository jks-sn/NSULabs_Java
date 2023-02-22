package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Push extends Operation {
    @Override
    public void exec() throws OperatorException {
        if (args[0] instanceof String)
            CalculatorStack.push((String) args[0]);
        else
            CalculatorStack.push((double) args[0]);
    }
}
