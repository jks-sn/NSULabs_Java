package ru.nsu.ccfit.berkaev.ui.gui.textures;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static ru.nsu.ccfit.berkaev.ui.gui.textures.SameTextures.BUTTON_BACKGROUND;
import static ru.nsu.ccfit.berkaev.ui.gui.textures.SameTextures.BUTTON_BACKGROUND_PRESSED;

public class MyButton extends JButton {
    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;

    public MyButton() {
        this(null);
    }

    public MyButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        setBackground(BUTTON_BACKGROUND);
        setPressedBackgroundColor(BUTTON_BACKGROUND_PRESSED);
        setHoverBackgroundColor(BUTTON_BACKGROUND.brighter());
        setForeground(Color.LIGHT_GRAY);
        setFocusPainted(false);
        setBorder(new LineBorder(Color.GRAY, 1));
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        if(getModel().isPressed())
        {
            g.setColor(pressedBackgroundColor);
        } else if(getModel().isRollover())
        {
            g.setColor(hoverBackgroundColor);
        } else
        {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }
}