package ru.nsu.ccfit.berkaev.exceptions;

import static ru.nsu.ccfit.berkaev.constants.ErrorConstants.NO_ACTIVE_SOCKET_EXCEPTION_MESSAGE;

public class NoActiveSocketException extends Exception {
    public NoActiveSocketException() {
        super(NO_ACTIVE_SOCKET_EXCEPTION_MESSAGE);
    }
}
