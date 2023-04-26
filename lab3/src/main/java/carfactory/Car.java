package carfactory;

import carfactory.carparts.Engine;
import carfactory.carparts.Supplier;
import carfactory.carparts.Wheel;

public class Car extends Product{
    private final Engine engine;
    private final Wheel wheel;
    private final Supplier supplier;

    public Car(long ProductID, Engine engine, Wheel wheel, Supplier supplier) {
        super(ProductID);
        this.engine = engine;
        this.wheel = wheel;
        this.supplier = supplier;
    }
}
