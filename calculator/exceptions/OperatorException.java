package calculator.exceptions;

public class OperatorException extends Exception {
    public OperatorException()
    {
        super();
    }
    public OperatorException(String informationAboutException){
        super(informationAboutException);
    }
}
