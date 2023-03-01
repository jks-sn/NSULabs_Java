package calculator.logic;

import calculator.exceptions.StackException;
import calculator.utils.ArgChecker;
import calculator.exceptions.OperatorException;

import java.util.HashMap;
import java.util.Stack;

import static calculator.exceptions.ExceptionConstants.*;

public class CalculatorStack {
    private final Stack<Double> variableStack;
    private final HashMap<String, Double> variableMap;

    CalculatorStack() {
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

    public void push(String variableName) throws OperatorException {
        if (variableMap.containsKey(variableName))
            variableStack.push(variableMap.get(variableName));
        else
            throw new StackException(HASH_MAP, UNKNOWN_VARIABLE);
    }

    public void push(double value) {
        variableStack.push(value);
    }

    public double pop() throws OperatorException {
        if (variableStack.isEmpty())
            throw new StackException(STACK, EMPTY_STACK);
        return variableStack.pop();
    }

    public double peek() throws OperatorException {
        if (variableStack.isEmpty())
            throw new StackException(STACK,EMPTY_STACK);
        return variableStack.peek();
    }

    public void clear() {
        variableMap.clear();
        variableStack.clear();
    }

}
