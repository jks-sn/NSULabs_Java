package ru.nsu.ccfit.berkaev.exceptions;

import static ru.nsu.ccfit.berkaev.constants.ErrorConstants.SOCKET_PROPERTIES_CANT_GET_MESSAGE;

public class SocketPropertyGetError extends Exception {
    public SocketPropertyGetError() {
        super(SOCKET_PROPERTIES_CANT_GET_MESSAGE);
    }
}
