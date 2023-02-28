package calculator.logic;

import calculator.exceptions.OperatorException;
import calculator.factory.OperationCreator;
import calculator.utils.ArgChecker;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class InteractiveCalc {
    private CalculatorStack context;
    private String operationName;
    private ArrayList<Object> args;
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
                if (line.isEmpty()) {
                    continue;
                }
                if (line.charAt(0) == '#')
                    break;
                for (var word : line.split(" ")) {
                    if (ArgChecker.regularSpecialSymbols(word))
                        throw new OperatorException("Wrong symbols!!!");
                    if (isOperation) {
                        isOperation = false;
                        operationName = word;
                    } else {
                        if (ArgChecker.isDouble(word)) {
                            if (Double.isNaN(Double.parseDouble(word)) || Double.isInfinite(Double.parseDouble(word)))
                                throw new OperatorException("Error, wrong value!!!");
                            args.add(word);
                        } else {
                            args.add(word);
                        }
                    }
                }
                factory.getOperation(operationName, args.toArray(new Object[0])).exec();
            }  catch (OperatorException | IOException | ClassNotFoundException | NoSuchMethodException |
                      InvocationTargetException | InstantiationException | IllegalAccessException | NullPointerException e) {
                System.out.println("Error" + ":\n" + e.getClass().getSimpleName() + (e.getMessage() == null ? "" : ": " + e.getMessage())+"\nPlease try again");
            }
            finally {
                args.clear();
                operationName = "";
            }
        }
        readScan.close();
        if (context.getStackLength() > 1)
            System.out.println("In stack now " + context.getStackLength() + " variables.");
        else if (context.getStackLength() == 0)
            System.out.println("Stack is empty!!!");
    }
}
