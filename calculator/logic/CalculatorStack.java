package calculator.logic;

import calculator.utils.ArgChecker;

import java.util.HashMap;
import java.util.Stack;

public class CalculatorStack {
    private static int uniqueVariableCounter;
    private static int variableCounter;
    private static final Stack<String> variableStack = new Stack<>();
    private static final HashMap<String,Double> variableMap = new HashMap<>();
    public static long getVariableCounter()
    {
        return variableCounter;
    }
    public static void createVariable(String variableName, double value)
    {
        if(!variableMap.containsKey(variableName) && !ArgChecker.isNumeric(variableName))
        {
            uniqueVariableCounter++;
            variableMap.put(variableName,value);
        }
    }
    public static void push()
    {

    }
    
}
