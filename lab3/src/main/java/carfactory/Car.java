package carfactory;

import carfactory.carparts.Engine;
import carfactory.carparts.Wheel;

public class Car extends Product{
    private final Engine engine;
    private final Wheel wheel;

    public Car(long ProductID, Engine engine, Wheel wheel) {
        super(ProductID);
        this.engine = engine;
        this.wheel = wheel;
    }
}
