package ru.nsu.ccfit.berkaev.constants;

import java.awt.*;

public class Constants {
    public static class UI
    {

    }
    public static class Game
    {
    }
    public enum Digits
    {
        ZERO("0",0,0,0),
        ONE("1", Color.blue),
        TWO("2",51,103,1),
        THREE("3",Color.red),
        FOUR("4",153,0,0),
        FIVE("5",153,0,153),
        SIX("6",96,96,96),
        SEVEN("7",0,0,102),
        EIGHT("8",153,0,76);

        private final String number;
        private final Color color;
        Digits(String number, Color color)
        {
            this.number = number;
            this.color = color;
        }
        Digits(String number, int red, int green, int blue)
        {
            this.number = number;
            color = new Color(red,green,blue);
        }

    }
}
