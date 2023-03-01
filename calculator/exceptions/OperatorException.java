package calculator.exceptions;

import static calculator.exceptions.ExceptionConstants.BAD_OPERATION;

public class OperatorException extends FactoryException {
    public OperatorException(String problemObjectName, String currentProblem) {
        super(problemObjectName,currentProblem);
    }
}
