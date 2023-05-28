package exceptions;

import static constants.ErrorConstants.socketStillOpenedExceptionMessage;

public class SocketStillOpenedException extends Exception {
    public SocketStillOpenedException() {
        super(socketStillOpenedExceptionMessage);
    }
}
