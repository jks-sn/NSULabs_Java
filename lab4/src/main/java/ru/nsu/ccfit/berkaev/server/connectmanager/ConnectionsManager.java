package ru.nsu.ccfit.berkaev.server.connectmanager;

import ru.nsu.ccfit.berkaev.exceptions.ConnectionError;
import ru.nsu.ccfit.berkaev.exceptions.IllegalRequestException;
import ru.nsu.ccfit.berkaev.constants.SharedConstants;
import ru.nsu.ccfit.berkaev.properties.PropertiesReader;
import ru.nsu.ccfit.berkaev.server.chathistory.FileData;
import ru.nsu.ccfit.berkaev.stcmessages.ChatHistoryMessage;
import ru.nsu.ccfit.berkaev.stcmessages.ErrorMessage;
import ru.nsu.ccfit.berkaev.stcmessages.STCMessage;
import ru.nsu.ccfit.berkaev.server.ChatServerTask;
import ru.nsu.ccfit.berkaev.server.ServerMain;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static ru.nsu.ccfit.berkaev.constants.ServerConstants.*;

public class ConnectionsManager extends Thread implements CTSPassingInterface, STCPassingInterface {

    private final ServerMain server;
    private final ServerSocket serverSocket;
    private PropertiesReader propertiesReader;
    private final Map<Integer, Socket> sockets = new HashMap<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(NUMBER_THREADS);
    private BlockingQueue<ChatServerTask> taskQueue = new LinkedBlockingQueue<>();

    private Integer nextID = SharedConstants.FIRST_ELEMENT;
    private final String protocol;

    public ConnectionsManager(ServerSocket serverSock, ServerMain server, String protocol) {
        this.protocol = protocol;
        this.server = server;
        this.serverSocket = serverSock;
        propertiesReader = new PropertiesReader();
        propertiesReader.getAllProperties(SharedConstants.SERVER_PROPERTIES_PATH);
        setName(SharedConstants.CONNECTIONS_MANAGER_NAME);
    }

    @Override
    public void run() {
        Thread executorThread = new Thread(() -> {
            executor.scheduleAtFixedRate(() -> {
                Runnable task = taskQueue.poll();
                if (task != null) {
                    task.run();
                }
            }, INITIAL_EXECUTOR_DELAY, PERIOD_EXECUTOR, TimeUnit.SECONDS);
        });
        executorThread.start();
        while (! this.isInterrupted()) {
            try {
                Socket newClient = serverSocket.accept();
                try {
                    taskQueue.put((new ChatServerTask(this, newClient, nextID, null, protocol)));
                    sockets.put(nextID, newClient);
                    nextID++;
                } catch (ConnectionError e) {
                    new ObjectOutputStream(newClient.getOutputStream()).writeObject(new ErrorMessage(SharedConstants.CANT_CREATE_CONNECT_MESSAGE));
                    e.printStackTrace();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void connectUser(String username, Integer sessionID) {
        try {
            server.registrateUser(sessionID, username);
        } catch (IOException | IllegalRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void disconnectUser(Integer sessionID) {
        try {
            server.deleteUser(sessionID);
        } catch (IllegalRequestException ignored) {}
    }

    public synchronized void disconnectUser(Integer sessionID, String reason) {
        try {
            server.deleteUser(sessionID, reason);
        } catch (IllegalRequestException ignored) {}
    }

    @Override
    public synchronized void requestForParticipantsList(Integer sessionID) {
        try {
            server.sendParticipantsTable(sessionID);
        } catch (IllegalRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void sendMessage(Integer sessionID, STCMessage message) {
        try {
            try {
                if (sockets.get(sessionID) != null)
                    taskQueue.add(new ChatServerTask(this, sockets.get(sessionID), sessionID, message, protocol));
            } catch (ConnectionError e) {
                new ObjectOutputStream(sockets.get(sessionID).getOutputStream()).writeObject(new ErrorMessage("Cannot create connection"));
                e.printStackTrace();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void chatMessageNotification(String message, Integer sessionID) {
        server.addMessageToChatHistory(message, sessionID);
    }

    public void broadcastMessage(HashMap<Integer, Integer> offsets, FileData message, int recentMessagesCount, int startOffset) {
        try {
            for (Integer i : sockets.keySet()) {
                if (offsets.get(i) != null) sendMessage(i, new ChatHistoryMessage(message, offsets.get(i) - recentMessagesCount + startOffset));
            }
        } catch (NullPointerException ignored) {}
    }
    public void addTask(ChatServerTask task) throws InterruptedException {
        taskQueue.put(task);
    }
    public ChatServerTask getTask() throws InterruptedException {
        return taskQueue.take();
    }
    public int getAndIncrementID()
    {
        return nextID++;
    }
}
