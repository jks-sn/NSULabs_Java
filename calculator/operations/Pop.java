package calculator.operations;

import calculator.logic.CalculatorStack;

public class Pop extends Operation {
    @Override
    public void exec() {
        CalculatorStack.pop();
    }
}
