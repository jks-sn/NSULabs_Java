package exceptions;

public class SocketStillOpenedException extends Exception {
    public SocketStillOpenedException() {
        super("There is already a connection");
    }
}
