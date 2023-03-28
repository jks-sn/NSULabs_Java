package ru.nsu.ccfit.berkaev.utils;

public class UtilsBoard {
    public static int countValidCoordinates(int i,int limit)
    {
        if(i < 0)
            return 0;
        if(i >=limit)
            return (limit-1);
        return i;
    }
}
