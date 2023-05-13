package carfactory.carbuildings;

import carfactory.carparts.Car;
import carfactory.carparts.Accessory;
import carfactory.carparts.CarBody;
import carfactory.carparts.Engine;
import carfactory.tasks.BuildCar;
import carfactory.tasks.SellCar;
import carfactory.tasks.Supply;
import carfactory.threadpool.Task;
import carfactory.threadpool.ThreadPool;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static carfactory.constants.Constants.ConfigStrings.*;
import static carfactory.utils.GeneratorID.generateID;

public class CarFabric {
    private static final Logger logger = Logger.getLogger(CarFabric.class.getName());
    private Properties config;
    private final Storage<Engine> engineStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<CarBody> carBodyStorage;
    private final Storage<Car> carStorage;

    private final AtomicInteger numberCars;

    private final ThreadPool threadPoolWorker;
    private final ThreadPool threadPoolDealer;
    private final ThreadPool threadPoolSupplier;
    private ThreadPool threadPoolController;
    Task supplyAccessory;
    Task supplyEngine;
    Task supplyCarBody;
    Task buildingOrder;
    Task sellingOrder;
    public CarFabric()
    {
        logger.info("Car Fabric starts");
        try{
            config = new Properties();
            config.load(this.getClass().getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        engineStorage = new Storage<>(Integer.parseInt(config.getProperty(configEngineStorageName)), engineStorageName);
        carBodyStorage = new Storage<>(Integer.parseInt(config.getProperty(configCarBodyStorageName)),carBodyStorageName);
        accessoryStorage = new Storage<>(Integer.parseInt(config.getProperty(configAccessoryStorageName)), accessoryStorageName);
        carStorage = new Storage<>(Integer.parseInt(config.getProperty(configCarStorageName)),carStorageName);

        int supplierDelay = Integer.parseInt(config.getProperty(configSupplierDelay));
        int dealerDelay = Integer.parseInt(config.getProperty(configDealerDelay));

        numberCars = new AtomicInteger(0);

        threadPoolSupplier = new ThreadPool(Integer.parseInt(config.getProperty(configNumberSuppliers))*3);
        threadPoolWorker = new ThreadPool(Integer.parseInt(config.getProperty(configNumberWorkers)));
        threadPoolDealer = new ThreadPool(Integer.parseInt(config.getProperty(configNumberDealers)));

        supplyAccessory = new Supply<>(accessoryStorage, supplierDelay, Accessory.class);
        supplyEngine = new Supply<>(engineStorage, supplierDelay, Engine.class);
        supplyCarBody = new Supply<>(carBodyStorage, supplierDelay, CarBody.class);

        buildingOrder = new BuildCar(this);
        sellingOrder = (new SellCar(this, dealerDelay, generateID()));
    }
    public void startFabric()
    {
        Thread work = new Thread(() -> {
            while (carStorage.getNumberItems() < carStorage.getStorageSize()){
                threadPoolSupplier.addTask(supplyAccessory);
                threadPoolSupplier.addTask(supplyEngine);
                threadPoolSupplier.addTask(supplyCarBody);
                threadPoolWorker.addTask(buildingOrder);
                threadPoolDealer.addTask(sellingOrder);
            }
        });
        work.start();
    }
    public void stopFabric(){
        logger.info("factory start stop working");
        threadPoolWorker.shutdown();
        threadPoolSupplier.shutdown();
        threadPoolDealer.shutdown();
        logger.info("Factory finish stop working");
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

    public int getAccessoryStorageSize(){
        return accessoryStorage.getStorageSize();
    }

    public int getCarBodyStorageSize(){
        return carBodyStorage.getStorageSize();
    }
    public int getCarStorageSize(){return carStorage.getStorageSize();}
    public int getEngineStorageNumberCarItems(){
        return engineStorage.getNumberItems();
    }

    public int getAccessoryStorageNumberCarItems(){
        return accessoryStorage.getNumberItems();
    }

    public int getCarBodyStorageNumberCarItems(){
        return carBodyStorage.getNumberItems();
    }
    public int getCarStorageNumberCarItems(){
        return carStorage.getNumberItems();
    }
    public void setDealerDelay(int dealerDelay) {
        sellingOrder.setParameter(dealerDelay);
    }
    public void setAccessorySupplierDelay(int supplierDelay) {
        supplyAccessory.setParameter(supplierDelay);
    }
    public void setEngineSupplierDelay(int supplierDelay) {
        supplyEngine.setParameter(supplierDelay);
    }

    public void setCarBodySupplierDelay(int supplierDelay) {
        supplyCarBody.setParameter(supplierDelay);
    }
}