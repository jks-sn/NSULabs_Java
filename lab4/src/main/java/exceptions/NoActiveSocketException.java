package exceptions;

public class NoActiveSocketException extends Exception {
    public NoActiveSocketException() {
        super("There is no active connection");
    }
}
