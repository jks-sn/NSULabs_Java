package exceptions;

public class DuplicateNameException extends Exception {
    public DuplicateNameException(String err) {
        super(err);
    }
}
