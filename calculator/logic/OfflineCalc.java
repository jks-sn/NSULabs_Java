package calculator.logic;

import calculator.exceptions.OperatorException;
import calculator.factory.OperationCreator;
import calculator.utils.ArgChecker;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class OfflineCalc {
    private final String pathToFile;
    private String operation;
    private ArrayList<Object> args;
    public OfflineCalc(String inputFileName) {
        pathToFile = inputFileName;
        String operation = "";
        ArrayList<Object> args = new ArrayList<>();
    }

    public void parseFile() throws OperatorException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Scanner readScan;
        String line;
        boolean isOperation;
        readScan = new Scanner(pathToFile);
        while (readScan.hasNextLine()) {
            isOperation = true;
            line = readScan.nextLine();
            if(line.charAt(0) == '#')
                break;
            for(var word: line.split(" "))
            {
                if(ArgChecker.regularSpecialSymbols(word))
                    throw new OperatorException("Wrong symbols!!!");
                if(isOperation)
                {
                    isOperation = false;
                    operation = word;
                }
                else
                {
                    if(ArgChecker.isNumeric(word))
                    {
                        args.add(word);
                    }
                    else
                    {
                        throw new OperatorException("Eroor, wrong value");
                    }
                }
            }
            OperationCreator.getOperation(operation).setArgs(args);
        }
    }
}
