package calculator.operations;

import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;

public class Define extends Operation{

    public Define(CalculatorStack context, Object... args) {
        super(context, args);
    }

    @Override
    public void exec() throws OperatorException {
        if(args.length != 2)
        {
            throw new OperatorException("Error: wrong number of arguments");
        }
        context.createVariable((String)args[0],(Double.parseDouble((String)args[1])));
    }
}
