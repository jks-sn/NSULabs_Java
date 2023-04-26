package carfactory.threadpool;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class PooledThread extends Thread{
    AtomicBoolean shutdownRequired = new AtomicBoolean(false);
    private final ArrayDeque<ThreadPoolTask> taskQueue;

    public PooledThread(String name, ArrayDeque<ThreadPoolTask> taskQueue) {
        super(name);
        this.taskQueue = taskQueue;
    }

    public void interruptPooledThread(){
        this.interrupt();
        shutdownRequired.set(true);
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
                        break;
                    }
                    continue;
                } else {
                    task = taskQueue.remove();
                }
            }
            performTask(task);
        }
    }
}
