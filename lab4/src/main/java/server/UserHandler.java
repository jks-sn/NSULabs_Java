package server;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class UserHandler implements Runnable{
    private final Logger logger = Logger.getLogger(UserHandler.class.getName());
    private Socket socket;
    private Server server;
    @Override
    public void run() {

    }
}
