package carfactory.carbuildings;

import carfactory.carparts.Product;
import carfactory.logger.MyLogger;

import java.util.ArrayDeque;

public class Storage<T extends Product>{
    private final ArrayDeque<T> carItems;
    private final int size;
    private final String storageName;
    private static final MyLogger logger = new MyLogger(Storage.class.getName());
    private final Object monitor;

    public Storage(int size, String storageName) {
        this.size = size;
        this.storageName = storageName;
        this.carItems = new ArrayDeque<>();
        this.monitor = new Object();
    }
    public void put(T newCarItem) {
        synchronized (monitor)
        {
            if(carItems.size() > size)
            {
                try{
                    logger.info(storageName + " :: STORAGE IS FULL");
                    monitor.wait();
                }
                catch(InterruptedException e){
                    logger.info(storageName + " :: INTERRUPTED IN WAIT");

                }
            }
            logger.info(storageName + " :: GOT NEW ITEM :: " + newCarItem.toString());
            carItems.add(newCarItem);
            monitor.notify();
            logger.info(storageName + " :: NOTIFIED");
        }
    }
    public T get()
    {
        synchronized (monitor)
        {
            while(true)
            {
                try{
                    logger.info(storageName + " NUMBER carItems " + carItems.size());
                    if(!carItems.isEmpty())
                    {
                        T carItem = carItems.getLast();
                        carItems.remove();
                        monitor.notify();
                        logger.info(storageName + " :: PASSING PRODUCT");
                        return carItem;
                    }
                    else
                    {
                        logger.info(storageName + " :: WAITING FOR A SPARE");
                        monitor.wait();
                        logger.info(storageName + " :: WOKE UP");
                    }
                } catch (InterruptedException e) {
                    logger.info(storageName + " :: INTERRUPTED IN WAIT");
                }
            }
        }
    }
    public int getStorageSize() {
        return size;
    }

    public int getNumberItems(){
        return carItems.size();
    }
}
