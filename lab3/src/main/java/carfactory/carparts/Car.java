package carfactory.carparts;

import carfactory.logger.MyLogger;

import java.util.logging.Logger;

public class Car extends Product {
    private final Engine engine;
    private final CarBody carBody;
    private final Accessory accessory;
    private static final MyLogger logger = new MyLogger(Car.class.getName());

    public Car(Engine engine, CarBody carBody, Accessory accessory) {
        super();
        this.engine = engine;
        this.carBody = carBody;
        this.accessory = accessory;
    }
    public Car finishBuild(){
        logger.info("BUILD A CAR. ID: " + this.getID());
        return new Car(engine, carBody, accessory);
    }
}
