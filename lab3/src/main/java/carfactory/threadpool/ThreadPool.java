package carfactory.threadpool;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class ThreadPool implements TaskListener {
    private final ArrayDeque<ThreadPoolTask> taskQueue = new ArrayDeque<>();
    private final Set<PooledThread> availableThreads = new HashSet<>();

    public ThreadPool(int size) {
        for (int i = 0; i < size; ++i) {
            availableThreads.add(new PooledThread("contractor " + i, taskQueue));
        }
        for (PooledThread thread : availableThreads) {
            thread.start();
        }
    }

    @Override
    public void taskInterrupted(Task t) {

    }

    @Override
    public void taskFinished(Task t) {

    }

    @Override
    public void taskStarted(Task t) {

    }

    public void addTask(Task t) {
        addTask(t, this);
    }

    public void addTask(Task t, TaskListener l) {
        synchronized (taskQueue) {
            taskQueue.add(new ThreadPoolTask(t, l));
            taskQueue.notifyAll();
        }
    }

    public void shutdown() {
        for (PooledThread thread : availableThreads) {
            thread.interruptPooledThread();
        }

        for (PooledThread thread : availableThreads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
    }
}
