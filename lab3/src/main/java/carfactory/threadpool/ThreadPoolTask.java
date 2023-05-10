package carfactory.threadpool;

public class ThreadPoolTask {
    private final TaskListener listener;
    private final Task task;

    public ThreadPoolTask(Task task, TaskListener listener){
        this.listener = listener;
        this.task = task;
    }

    void prepare(){
        listener.taskStarted(task);
    }

    void finish(){
        listener.taskFinished(task);
    }

    void interrupted(){
        listener.taskInterrupted(task);
    }

    void go() throws InterruptedException{
        task.performWork();
    }

    String getName(){
        return task.getName();
    }
}
