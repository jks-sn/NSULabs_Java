package carfactory.carparts;

import static carfactory.utils.GeneratorID.generateID;

public abstract class Product {
    private final long id;

    public Product(){
        id = generateID();
    }

    public long getID(){
        return id;
    }
}