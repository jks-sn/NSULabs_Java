package ru.nsu.ccfit.berkaev.exceptions;

import ru.nsu.ccfit.berkaev.constants.ErrorConstants;

public class SocketStillOpenedException extends Exception {
    public SocketStillOpenedException() {
        super(ErrorConstants.SOCKET_STILL_OPENED_EXCEPTION_MESSAGE);
    }
}
