package carfactory.tasks;

import carfactory.carparts.Car;
import carfactory.carbuildings.CarFabric;
import carfactory.carbuildings.Storage;
import carfactory.threadpool.Task;

import static carfactory.utils.GeneratorID.generateID;

public class SellCar implements Task {
    private final long dealerID;
    private final long carID;
    private final Storage<Car> carStorage;
    private int delay;

    public SellCar(CarFabric factory, int delay, long carID) {
        dealerID = generateID();
        this.carStorage = factory.getCarStorage();
        this.carID = carID;
        this.delay = delay;
    }

    @Override
    public String getName() {
        return "Sell car. carID: " + carID;
    }

    @Override
    public void performWork() throws InterruptedException {
        while (!Thread.currentThread().isInterrupted()){
            Thread.sleep(delay);
            carStorage.get();
        }
    }

    @Override
    public void setParameter(int parameter) {
        this.delay = parameter;
    }
}
