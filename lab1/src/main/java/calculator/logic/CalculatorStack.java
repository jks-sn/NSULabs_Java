package calculator.logic;

import calculator.exceptions.StackException;
//import srs.calculator.log.Log;
import calculator.utils.ArgChecker;
import calculator.exceptions.OperatorException;

import java.util.HashMap;
import java.util.Stack;

import static calculator.exceptions.ExceptionConstants.*;

public class CalculatorStack {
    private final Stack<Double> variableStack;
    private final HashMap<String, Double> variableMap;

    public CalculatorStack() {
        variableStack = new Stack<>();
        variableMap = new HashMap<>();
    }

    public long getStackLength() {
        return variableStack.size();
    }

    public void createVariable(String variableName, double value) {
        if (!variableMap.containsKey(variableName) && !ArgChecker.isDouble(variableName)) {
            variableMap.put(variableName, value);
        }
    }

    public void push(String variableName) throws StackException {
        if (variableMap.containsKey(variableName))
            variableStack.push(variableMap.get(variableName));
        else
            throw new StackException(HASH_MAP, UNKNOWN_VARIABLE);
    }

    public void push(double value) {
        variableStack.push(value);
    }

    public double pop() throws StackException {
        if (variableStack.isEmpty())
            throw new StackException(STACK, EMPTY_STACK);
        return variableStack.pop();
    }

    public double peek() throws StackException {
        if (variableStack.isEmpty())
            throw new StackException(STACK, EMPTY_STACK);
        return variableStack.peek();
    }

    public void printStack() {
        StringBuilder message = new StringBuilder("[");
        if (variableStack.isEmpty())
            message.append("empty");
        for (int i = 0; i < variableStack.size(); i++)
            message.append(ArgChecker.isDouble(String.valueOf(variableStack.get(i))) ? "var" + variableStack.get(i) : variableStack.get(i)).append(" = ").append(variableMap.get(variableStack.get(i))).append(i == variableStack.size() - 1 ? "" : ", ");
        message.append("]");

        //Log.log(Log.LogType.INFO, message.toString(), null);
    }

    public boolean containsKey(String key) {
        return variableMap.containsKey(key);
    }
    public double get(String key)
    {
        return variableMap.get(key);
    }
    public void clear() {
        variableMap.clear();
        variableStack.clear();
    }

}
