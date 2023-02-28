package calculator.logic;

import calculator.exceptions.OperatorException;
import calculator.factory.OperationCreator;
import calculator.utils.ArgChecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OfflineCalc {
    private final String pathToFile;
    private CalculatorStack context;
    private String operationName;
    private ArrayList<Object> args;
    private int numberLine;

    public OfflineCalc(String inputFileName) {
        pathToFile = inputFileName;
        context = new CalculatorStack();
        operationName = "";
        args = new ArrayList<>();
        numberLine = 1;
    }

    public void execute() {
        OperationCreator factory = new OperationCreator(context);
        Scanner readScan;
        String line;
        boolean isOperation;
        try {
            readScan = new Scanner(new File(pathToFile));
        } catch (IOException error) {
            error.printStackTrace(System.err);
            return;
        }
        while (readScan.hasNextLine()) {
            try {
                isOperation = true;
                line = readScan.nextLine();
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
            } catch (Throwable error) {
                System.err.println("Error on line " + numberLine + ":\n" + error.getClass().getSimpleName() + (error.getMessage() == null ? "" : ": " + error.getMessage()));
            } finally {
                args.clear();
                numberLine++;
            }
        }
        readScan.close();
        if (context.getStackLength() > 1)
            System.out.println("In stack now " + context.getStackLength() + " variables.");
        else if (context.getStackLength() == 0)
            System.out.println("Stack is empty!!!");
    }
}
