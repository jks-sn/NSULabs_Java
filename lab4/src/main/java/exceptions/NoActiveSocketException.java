package exceptions;

import static constants.ErrorConstants.noActiveSocketExceptionMessage;

public class NoActiveSocketException extends Exception {
    public NoActiveSocketException() {
        super(noActiveSocketExceptionMessage);
    }
}
