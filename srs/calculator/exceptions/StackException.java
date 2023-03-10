package srs.calculator.exceptions;

public class StackException extends OperatorException {
    public StackException(String problemObjectName, String currentProblem) {
        super(problemObjectName, currentProblem);
    }
}
