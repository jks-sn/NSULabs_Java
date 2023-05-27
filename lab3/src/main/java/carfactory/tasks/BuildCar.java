package carfactory.tasks;

import carfactory.carparts.Car;
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
        this.engineStorage = carFabric.getEngineStorage();
        this.carBodyStorage = carFabric.getCarBodyStorage();
        this.accessoryStorage = carFabric.getAccessoryStorage();
        this.carStorage = carFabric.getCarStorage();
    }

    @Override
    public String getName() {
        return "Build car. Worker ID: " + workerID;
    }

    @Override
    public void performWork() {
        while (!Thread.currentThread().isInterrupted()) {
            Car car = new Car(engineStorage.get(), carBodyStorage.get(), accessoryStorage.get());
            carStorage.put(car.finishBuild());
            carFabric.finishCarBuilding();
        }
    }

    @Override
    public void setParameter(int parameter) {
    }
}
