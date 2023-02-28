package calculator.exceptions;

public class OperatorException extends CalcException {
    public OperatorException() {
        super();
    }

    public OperatorException(String informationAboutException) {
        super(informationAboutException);
    }
}
