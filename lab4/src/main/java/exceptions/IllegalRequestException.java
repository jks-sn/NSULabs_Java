package exceptions;

public class IllegalRequestException extends Exception {
    public IllegalRequestException(String err) {
        super(err);
    }
}
