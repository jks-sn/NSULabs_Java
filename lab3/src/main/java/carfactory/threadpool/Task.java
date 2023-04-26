package carfactory.threadpool;

public interface Task {
    String getName();
    void performWork() throws InterruptedException;

}
