package carfactory.carbuildings;

import carfactory.carparts.Car;
import carfactory.carparts.Accessory;
import carfactory.carparts.CarBody;
import carfactory.carparts.Engine;
import carfactory.tasks.BuildCar;
import carfactory.threadpool.Task;
import carfactory.threadpool.ThreadPool;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
public class CarFabric {
    private static final Logger logger = Logger.getLogger(CarFabric.class.getName());
    private Properties config;
    private final Storage<Engine> engineStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<CarBody> carBodyStorage;
    private final Storage<Car> carStorage;

    private AtomicInteger numberCars;

    private ThreadPool threadPoolWorker;
    private ThreadPool threadPoolDealer;
    private ThreadPool threadPoolSupplier;
    private ThreadPool threadPoolController;
    Task buildingOrder;
    Task sellingOrder;
    public CarFabric()
    {
        logger.info("Car Fabric starts");
        try{
            config = new Properties();
            config.load(this.getClass().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        engineStorage = new Storage<>(Integer.parseInt(config.getProperty("EngineStorageSize")), "EngineStorage");
        carBodyStorage = new Storage<>(Integer.parseInt(config.getProperty("CarBodyStorageSize")),"CarBodyStorage");
        accessoryStorage = new Storage<>(Integer.parseInt(config.getProperty("SupplierStorageSize")), "SupplierStorage");
        carStorage = new Storage<>(Integer.parseInt(config.getProperty("CarStorageSize")),"CarStorage");

        numberCars = new AtomicInteger(0);

        threadPoolSupplier = new ThreadPool(Integer.parseInt(config.getProperty("NumberSuppliers")));
        threadPoolWorker = new ThreadPool(Integer.parseInt(config.getProperty("NumberWorkers")));
        threadPoolDealer = new ThreadPool(Integer.parseInt(config.getProperty("NumberDealers")));

        buildingOrder = new BuildCar(this);
        Thread work = new Thread(() -> {
            while (carStorage.getNumberItems() < carStorage.getStorageSize()){
                threadPoolWorker.addTask(buildingOrder);
                threadPoolDealer.addTask(sellingOrder);
            }
        });

        work.start();
    }
    public void stopFactory(){
        threadPoolWorker.shutdown();
        threadPoolSupplier.shutdown();
        threadPoolDealer.shutdown();
        logger.info("Factory stop working");
    }
    public void finishCarBuilding(){
        numberCars.getAndIncrement();
    }
    public Storage<Engine> getEngineStorage(){
        return engineStorage;
    }
    public Storage<CarBody> getCarBodyStorage(){
        return carBodyStorage;
    }
    public Storage<Accessory> getAccessoryStorage(){
        return accessoryStorage;
    }
    public Storage<Car> getCarStorage(){
        return carStorage;
    }
    public int getProducedCarCount() {
        return numberCars.get();
    }
    public int getEngineStorageSize(){
        return engineStorage.getStorageSize();
    }

    public int getWheelStorageSize(){
        return accessoryStorage.getStorageSize();
    }

    public int getCarBodyStorageSize(){
        return carBodyStorage.getStorageSize();
    }
}
