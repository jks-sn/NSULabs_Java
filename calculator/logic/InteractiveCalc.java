package calculator.logic;

import calculator.exceptions.OperatorException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class InteractiveCalc {
    private final String pathToFile;
    private  CalculatorStack context;
    private String operation;
    private ArrayList<Object> args;
    public InteractiveCalc(String inputFileName) {
        pathToFile = inputFileName;
        context = new CalculatorStack();
        String operation = "";
        ArrayList<Object> args = new ArrayList<>();
    }

    public void execute() {

    }
}
