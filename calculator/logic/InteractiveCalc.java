package calculator.logic;

import calculator.exceptions.CalcException;
import calculator.factory.OperationCreator;
import calculator.utils.ArgChecker;
import calculator.utils.RegularSpecialSymbolsException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static calculator.exceptions.ExceptionConstants.*;

public class InteractiveCalc {
    private CalculatorStack context;
    private String operationName;
    private final ArrayList<Object> args;
    private String line;

    private boolean isOperation;

    public InteractiveCalc() {
        context = new CalculatorStack();
        operationName = "";
        args = new ArrayList<>();
        line = "";
    }

    public void execute() {
        OperationCreator factory = new OperationCreator(context);
        Scanner readScan = new Scanner(System.in);
        System.out.println("Hello! This is stack-calculator, please write you command:");
        while (readScan.hasNext()) {
            try {
                isOperation = true;
                line = readScan.nextLine();
                if(Objects.equals(line, "EXIT"))
                {
                    break;
                }
                if (line.isEmpty() || line.charAt(0) == '#') {
                    continue;
                }
                for (var word : line.split(" ")) {
                    ArgChecker.regularSpecialSymbols(word);
                    if (isOperation) {
                        isOperation = false;
                        operationName = word;
                    } else {
                        if (ArgChecker.isDouble(word)) {
                            if (Double.isNaN(Double.parseDouble(word))) throw new CalcException(VALUE, VALUE_NAN);
                            if (Double.isInfinite(Double.parseDouble(word)))
                                throw new CalcException(VALUE, VALUE_INFINITE);
                            args.add(word);
                        } else {
                            args.add(word);
                        }
                    }
                }
                factory.getOperation(operationName, args.toArray(new Object[0])).exec();
            } catch (CalcException e) {
                System.err.println("Error " + ":\n");
                e.whatTheProblem();
            } catch (RegularSpecialSymbolsException e) {
                System.err.println("Error" + ":\n" + "There are some specific symbols");
            } finally {
                args.clear();
                operationName = "";
            }
        }
        readScan.close();
        if (context.getStackLength() > 0)
            System.out.println("In stack now " + context.getStackLength() + " variables.");
        else {
            context.getStackLength();
        }
            System.out.println("Stack is empty");
    }
}
