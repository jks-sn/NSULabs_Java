package carfactory.tasks;

import carfactory.carparts.Car;
import carfactory.carbuildings.CarFabric;
import carfactory.carbuildings.Storage;
import carfactory.threadpool.Task;

public class SellCar implements Task {
    private final long carID;
    private final Storage<Car> carStorage;

    public SellCar(CarFabric factory, Storage<Car> carStorage, long carID) {
        this.carStorage = factory.getCarStorage();
        this.carID = carID;
    }

    @Override
    public String getName() {
        return "Sell car. carID: " + carID;
    }

    @Override
    public void performWork() {
        while (!Thread.currentThread().isInterrupted()){
            carStorage.get();
        }
    }

    @Override
    public void setParameter(int parameter) {
    }
}
