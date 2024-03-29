package carfactory.threadpool;

import carfactory.logger.MyLogger;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;


public class PooledThread extends Thread{
    AtomicBoolean shutdownRequired = new AtomicBoolean(false);
    private final ArrayDeque<ThreadPoolTask> taskQueue;
    private static final MyLogger logger = new MyLogger(PooledThread.class.getName());

    public PooledThread(String name, ArrayDeque<ThreadPoolTask> taskQueue) {
        super(name);
        this.taskQueue = taskQueue;
    }

    public void interruptPooledThread(){
        this.interrupt();
        shutdownRequired.set(true);
        taskQueue.clear();
    }
    private void performTask(ThreadPoolTask task){
        task.prepare();
        try {
            task.go();
        } catch (InterruptedException e){
            task.interrupted();
            shutdownRequired.set(true);
            return;
        }
        task.finish();
    }
    public void run(){
        ThreadPoolTask task;
        while (!shutdownRequired.get()){
            synchronized (taskQueue){
                if(taskQueue.isEmpty()){
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e){
                        logger.info("POOLED THREAD:: THREAD WAS INTERRUPTED: " + getName());
                        break;
                    }
                    continue;
                } else {
                    task = taskQueue.remove();
                }
            }
            logger.info(this.getName() + " :: GOT THE JOB: " + task.getName());
            performTask(task);
        }
    }
}
