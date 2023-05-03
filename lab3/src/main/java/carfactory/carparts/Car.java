package carfactory.carparts;

import java.util.logging.Logger;

public class Car extends Product {
    private final Engine engine;
    private final CarBody carBody;
    private final Accessory accessory;
    private static final Logger logger = Logger.getLogger(Car.class.getName());

    public Car(long ProductID, Engine engine, CarBody carBody, Accessory accessory) {
        super(ProductID);
        this.engine = engine;
        this.carBody = carBody;
        this.accessory = accessory;
    }
    public Car finishBuild(){
        logger.info("BUILD A CAR. ID: " + this.getID());
        return new Car(this.getID(), engine, carBody, accessory);
    }
}
