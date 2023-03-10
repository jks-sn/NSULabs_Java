package srs.calculator.exceptions;

import static srs.calculator.exceptions.ExceptionConstants.BAD_OPERATION;

public class OperatorException extends FactoryException {
    public OperatorException(String problemObjectName, String currentProblem) {
        super(problemObjectName,currentProblem);
    }
}
