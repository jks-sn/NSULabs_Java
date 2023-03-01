package calculator.logic;

import calculator.exceptions.CalcException;
import calculator.factory.OperationCreator;
import calculator.utils.ArgChecker;
import calculator.utils.RegularSpecialSymbolsException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static calculator.exceptions.ExceptionConstants.*;

public class OfflineCalc {
    private final String pathToFile;
    private final CalculatorStack context;
    private String operationName;
    private final ArrayList<Object> args;
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
                if (line.isEmpty() || line.charAt(0) == '#') continue;
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
                System.err.println("Error on line " + numberLine + ":\n");
                e.whatTheProblem();
            } catch (RegularSpecialSymbolsException e) {
                System.err.println("Error on line " + numberLine + ":\n" + "There are some specific symbols");
            } finally {
                args.clear();
                numberLine++;
            }
        }
        readScan.close();
        if (context.getStackLength() > 0)
            System.out.println("In stack now " + context.getStackLength() + " variables.");
        else System.out.println("Stack is empty");
    }
}
