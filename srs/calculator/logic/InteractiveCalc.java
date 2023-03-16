package srs.calculator.logic;

import srs.calculator.exceptions.CalcException;
import srs.calculator.factory.OperationCreator;
//import srs.calculator.log.Log;
import srs.calculator.utils.ArgChecker;
import srs.calculator.utils.RegularSpecialSymbolsException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static srs.calculator.constants.CalcConstants.*;
import static srs.calculator.exceptions.ExceptionConstants.*;

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
        System.out.println(HELLO_MESSAGE);
        while (readScan.hasNext()) {
            try {
                isOperation = true;
                line = readScan.nextLine();
                if (Objects.equals(line, NAME_EXIT_COMMAND)) {
                    break;
                }
                if (line.isEmpty() || line.charAt(0) == COMMENT_CHARACTER) {
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
                //Log.info("Stack after:");
            } catch (CalcException e) {
                //Log.error(MESSAGE_ERROR+e.getMessage(), e);
                System.err.println(MESSAGE_ERROR);
                e.whatTheProblem();
            } catch (RegularSpecialSymbolsException e) {
                //Log.error(MESSAGE_SPECIAL_SYMBOLS, e);
                System.err.println(MESSAGE_SPECIAL_SYMBOLS);
            } finally {
                args.clear();
                operationName = "";
            }
        }
        readScan.close();
        if (context.getStackLength() > 0) {
            //Log.info("In stack now " + context.getStackLength() + " variables.");
            System.out.println("In stack now " + context.getStackLength() + " variables.");
        }
        else {
            //Log.info(MESSAGE_EMPTY_STACK);
            System.out.println(EMPTY_STACK);
        }
    }
}
