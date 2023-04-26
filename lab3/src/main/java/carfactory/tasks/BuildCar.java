package carfactory.tasks;

import carfactory.Car;
import carfactory.carbuildings.CarFabric;
import carfactory.carbuildings.Storage;
import carfactory.carparts.Accessory;
import carfactory.carparts.CarBody;
import carfactory.carparts.Engine;
import carfactory.threadpool.Task;

import static carfactory.utils.GeneratorID.generateID;

public class BuildCar implements Task {

    private final CarFabric carFabric;
    private final long workerID;
    private final Storage<Engine> engineStorage;
    private final Storage<CarBody> carBodyStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Car> carStorage;

    public BuildCar(CarFabric carFabric) {
        this.carFabric = carFabric;
        this.workerID = generateID();
        this.engineStorage = engineStorage;
        this.carBodyStorage = carBodyStorage;
        this.accessoryStorage = accessoryStorage;
        this.carStorage = carStorage;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void performWork() throws InterruptedException {

    }
}
