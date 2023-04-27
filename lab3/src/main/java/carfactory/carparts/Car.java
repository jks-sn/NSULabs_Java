package carfactory.carparts;

public class Car extends Product {
    private final Engine engine;
    private final CarBody carBody;
    private final Accessory accessory;

    public Car(long ProductID, Engine engine, CarBody carBody, Accessory accessory) {
        super(ProductID);
        this.engine = engine;
        this.carBody = carBody;
        this.accessory = accessory;
    }
}
