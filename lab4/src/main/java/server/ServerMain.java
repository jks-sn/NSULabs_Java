package server;

import java.io.IOException;
import java.util.logging.LogManager;

public class ServerMain {
    public static void main(String [] args) throws InterruptedException {
        try {
            LogManager.getLogManager().readConfiguration(ServerMain.class.getResourceAsStream("resources/log.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server server = new Server(80);
        Thread serverThread = new Thread(server);
        serverThread.start();
        serverThread.join();
    }
}
