package exceptions;

import static constants.ErrorConstants.SOCKET_STILL_OPENED_EXCEPTION_MESSAGE;

public class SocketStillOpenedException extends Exception {
    public SocketStillOpenedException() {
        super(SOCKET_STILL_OPENED_EXCEPTION_MESSAGE);
    }
}
