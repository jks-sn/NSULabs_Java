package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Push extends Operation {
    public Push(CalculatorStack context, Object... args) {
        super(context, args);
        this.numberArguments = 0;
    }

    @Override
    public void exec() throws OperatorException {
        try {
            context.push(Double.parseDouble((String) args[0]));
        } catch (NumberFormatException e) {
            context.push((String) args[0]);
        }
    }
}
