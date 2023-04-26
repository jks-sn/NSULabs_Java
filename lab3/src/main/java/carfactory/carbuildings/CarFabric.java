package carfactory.carbuildings;

import carfactory.Car;
import carfactory.carparts.Engine;
import carfactory.carparts.Supplier;
import carfactory.carparts.Wheel;
import carfactory.threadpool.ThreadPool;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
public class CarFabric {
    private static final Logger logger = Logger.getLogger(CarFabric.class.getName());
    private final Properties config;
    private final Storage<Engine> engineStorage;
    private final Storage<Wheel> wheelStorage;

    private final Storage<Supplier> supplierStorage;
    private final Storage<Car> carStorage;

    private AtomicInteger numberCars;

    private ThreadPool threadPoolWorker;
    private ThreadPool threadPoolDealer;
    private ThreadPool threadPoolSupplier;
    private ThreadPool threadPoolController;

}
