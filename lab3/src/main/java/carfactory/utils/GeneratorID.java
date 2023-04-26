package carfactory.utils;

import java.util.concurrent.atomic.AtomicLong;

public class GeneratorID {
    private static final AtomicLong seed = new AtomicLong();
    public static long getID()
    {
        return seed.getAndIncrement();
    }
}
