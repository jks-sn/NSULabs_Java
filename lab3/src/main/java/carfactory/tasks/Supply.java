package carfactory.tasks;

import carfactory.carbuildings.Storage;
import carfactory.carparts.Product;
import carfactory.logger.MyLogger;
import carfactory.threadpool.Task;


public class Supply<T extends Product> implements Task {
    private final Storage<T> storage;
    private int delay;
    private final Class<T> itemClass;
    private static final MyLogger logger = new MyLogger(Supply.class.getName());

    public Supply(Storage<T> storage, int delay, Class<T> itemClass) {
        this.storage = storage;
        this.delay = delay;
        this.itemClass = itemClass;
    }

    @Override
    public String getName() {
        return "Supply" + itemClass.getName();
    }

    @Override
    public void performWork() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(delay);
                T item = itemClass.newInstance();
                storage.put(item);
            } catch (InterruptedException e) {
                logger.info(Thread.currentThread().getName() + " :: INTERRUPTED");
                break;
            }
            catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void setParameter(int parameter) {
        this.delay = parameter;
    }
}
