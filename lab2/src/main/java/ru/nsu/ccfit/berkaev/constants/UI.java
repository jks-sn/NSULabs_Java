package ru.nsu.ccfit.berkaev.constants;

import javax.swing.*;
import java.awt.*;

public class UI {
    public static final String FRAME_TITLE = "Minesweeper";
    public static final int FRAME_WIDTH = 490;
    public static final int FRAME_HEIGHT = 520;
    public static final int FRAME_LOC_X = 400;
    public static final int FRAME_LOC_Y = 20;
    public static void setTextColor(JButton button) {
        if (button.getText().equals("1"))
            button.setForeground(Color.blue);
        else if (button.getText().equals("2"))
            button.setForeground(new Color(51, 103, 1));
        else if (button.getText().equals("3"))
            button.setForeground(Color.red);
        else if (button.getText().equals("4"))
            button.setForeground(new Color(153, 0, 0));
        else if (button.getText().equals("5"))
            button.setForeground(new Color(153, 0, 153));
        else if (button.getText().equals("6"))
            button.setForeground(new Color(96, 96, 96));
        else if (button.getText().equals("7"))
            button.setForeground(new Color(0, 0, 102));
        else if (button.getText().equals("8"))
            button.setForeground(new Color(153, 0, 76));
    }
}
