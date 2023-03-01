package calculator.exceptions;

import static calculator.exceptions.ExceptionConstants.EXCEPTION_MESSAGE_DELIMITER;
import static calculator.exceptions.ExceptionConstants.MESSAGE_TRY;

public class CalcException extends Exception {
    protected String problemDescription;
    protected String problemObjectName;
    public CalcException(String problemObjectName,
                  String currentProblem)
    {
        this.problemObjectName = problemObjectName;
        this.problemDescription = currentProblem;
    }

    public void whatTheProblem()
    {
        System.out.println(problemObjectName+EXCEPTION_MESSAGE_DELIMITER+problemDescription+" "+MESSAGE_TRY+EXCEPTION_MESSAGE_DELIMITER+'\n');
    }
}
