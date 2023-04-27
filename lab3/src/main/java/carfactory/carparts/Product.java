package carfactory.carparts;

public abstract class Product {
    private final long id;

    public Product(long ProductID){
        id = ProductID;
    }

    public long getID(){
        return id;
    }
}