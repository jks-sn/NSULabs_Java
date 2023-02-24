package calculator.logic;

import calculator.utils.ArgChecker;
import calculator.exceptions.OperatorException;
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
    public static void push(String variableName) throws OperatorException {
        if(variableMap.containsKey(variableName))
            variableStack.push(variableMap.get(variableName));
        else
            throw new OperatorException();
    }
    public static void push(double value)
    {
        variableStack.push(value);
    }
    public static double pop() throws OperatorException {
        if(variableStack.isEmpty())
            throw new OperatorException();
        return variableStack.pop();
    }
    public static double peek() throws OperatorException {
        if(variableStack.isEmpty())
            throw new OperatorException();
        return variableStack.peek();
    }
    public static void clear()
    {
        variableMap.clear();
        variableStack.clear();
    }
    
}
