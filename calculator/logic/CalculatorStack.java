package calculator.logic;

import calculator.utils.ArgChecker;
import calculator.exceptions.OperatorException;

import java.util.HashMap;
import java.util.Stack;

public class CalculatorStack {
    private Stack<Double> variableStack;
    private HashMap<String, Double> variableMap;

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
            throw new OperatorException();
    }

    public void push(double value) {
        variableStack.push(value);
    }

    public double pop() throws OperatorException {
        if (variableStack.isEmpty())
            throw new OperatorException();
        return variableStack.pop();
    }

    public double peek() throws OperatorException {
        if (variableStack.isEmpty())
            throw new OperatorException();
        return variableStack.peek();
    }

    public void clear() {
        variableMap.clear();
        variableStack.clear();
    }

}
