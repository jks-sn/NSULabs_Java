package carfactory.carbuildings;

import carfactory.Car;
import carfactory.carparts.Accessory;
import carfactory.carparts.Engine;
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
    private final Storage<Car> carStorage;

    private AtomicInteger numberCars;

    private ThreadPool threadPoolWorker;
    private ThreadPool threadPoolDealer;
    private ThreadPool threadPoolSupplier;
    private ThreadPool threadPoolController;
    Task supplyWheels;
    Task supplyEngine;
    Task supplySupplier;
    Task buildingOrder;
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
        accessoryStorage = new Storage<>(Integer.parseInt(config.getProperty("SupplierStorageSize")), "SupplierStorage");
        carStorage = new Storage<>(Integer.parseInt(config.getProperty("CarStorageSize")),"CarStorage");
        numberCars = new AtomicInteger(0);
        threadPoolSupplier = new ThreadPool(Integer.parseInt(config.getProperty("NumberSuppliers")));
        threadPoolWorker = new ThreadPool(Integer.parseInt(config.getProperty("NumberWorkers")));
        threadPoolDealer = new ThreadPool(Integer.parseInt(config.getProperty("NumberDealers")));
    }
}
