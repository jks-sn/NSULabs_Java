package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Pop extends Operation {
    @Override
    public void exec() throws OperatorException {
        CalculatorStack.pop();
    }
}
