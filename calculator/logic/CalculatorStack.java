package calculator.logic;

import calculator.utils.ArgChecker;

import java.util.HashMap;
import java.util.Stack;

public class CalculatorStack {
    private static final Stack<Double> variableStack = new Stack<>();
    private static final HashMap<String,Double> variableMap = new HashMap<>();
    public static long getStackLength()
    {
        return variableStack.size();
    }
    public static void createVariable(String variableName, double value)
    {
        if(!variableMap.containsKey(variableName) && !ArgChecker.isNumeric(variableName))
        {
            variableMap.put(variableName,value);
        }
    }
    public static void push(String variableName)
    {
        if(variableMap.containsKey(variableName))
            variableStack.push(variableMap.get(variableName));
        else
            throw exception;
    }
    public static void push(double value)
    {
        variableStack.push(value);
    }
    public static double pop()
    {
        if(variableStack.isEmpty())
            throw exception;
        return variableStack.pop();
    }
    public static double peek()
    {
        if(variableStack.isEmpty())
            throw exception;
        return variableStack.peek();
    }
    public static void clear()
    {
        variableMap.clear();
        variableStack.clear();
    }
    
}
