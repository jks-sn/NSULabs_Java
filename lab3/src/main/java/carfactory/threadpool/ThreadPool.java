package carfactory.threadpool;

import carfactory.logger.MyLogger;

import java.util.logging.Logger;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class ThreadPool implements TaskListener {
    private final ArrayDeque<ThreadPoolTask> taskQueue = new ArrayDeque<>();
    private final Set<PooledThread> availableThreads = new HashSet<>();
    private static final MyLogger logger = new MyLogger(ThreadPool.class.getName());


    public ThreadPool(int size) {
        for (int i = 0; i < size; ++i) {
            availableThreads.add(new PooledThread("contractor " + i, taskQueue));
        }
        for (PooledThread thread : availableThreads) {
            thread.start();
        }
    }

    @Override
    public void taskInterrupted(Task task) {
        logger.info("THREAD POOL :: INTERRUPTED " + task.getName());
    }

    @Override
    public void taskFinished(Task task) {
        logger.info("THREAD POOL :: FINISHED " + task.getName());
    }

    @Override
    public void taskStarted(Task task) {
        logger.info("THREAD POOL :: STARTED " + task.getName());
    }

    public void addTask(Task task) {
        addTask(task, this);
    }

    public void addTask(Task task, TaskListener listener) {
        synchronized (taskQueue) {
            logger.fine("THREAD POOL :: ADDING NEW TASK " + task.getName());
            taskQueue.add(new ThreadPoolTask(task, listener));
            taskQueue.notifyAll();
        }
    }

    public void shutdown() {
        logger.info("THREAD POOL :: SHUTTING DOWN");
        for (PooledThread thread : availableThreads) {
            thread.interruptPooledThread();
            logger.info("THREAD POOL :: INTERRUPTED " + thread.getName());
        }

        for (PooledThread thread : availableThreads) {
            thread.interrupt();
            logger.info("THREAD POOL :: FINISHED " + thread.getName());
        }
        availableThreads.clear();
        taskQueue.clear();
        logger.info("THREAD POOL :: SHUTDOWN COMPLETED");
    }
}
