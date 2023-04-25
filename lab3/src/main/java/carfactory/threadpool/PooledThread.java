package carfactory.threadpool;

public class PooledThread extends Thread{
    private final ArrayDeque<ThreadPoolTask> taskQueue;
}
