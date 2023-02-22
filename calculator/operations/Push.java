package calculator.operations;

import calculator.logic.CalculatorStack;

public class Push extends Operation {
    @Override
    public void exec() {
        if (args[0] instanceof String)
            CalculatorStack.push((String) args[0]);
        else
            CalculatorStack.push((double) args[0]);
    }
}
