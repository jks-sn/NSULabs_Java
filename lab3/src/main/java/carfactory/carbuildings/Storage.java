package carfactory.carbuildings;

import carfactory.carparts.Product;

import java.util.ArrayDeque;

public class Storage<T extends Product>{
    private final ArrayDeque<T> carItems;
    private final int size;
    private final String storageName;

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
                    monitor.wait();
                }
                catch(InterruptedException e){

                }
            }
            carItems.add(newCarItem);
            monitor.notify();
        }
    }
    public T get()
    {
        synchronized (monitor)
        {
            while(true)
            {
                try{
                    if(!carItems.isEmpty())
                    {
                        T carItem = carItems.getLast();
                        carItems.remove();
                        monitor.notify();
                        return carItem;
                    }
                    else
                    {
                        monitor.wait();
                    }
                } catch (InterruptedException e) {}
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
