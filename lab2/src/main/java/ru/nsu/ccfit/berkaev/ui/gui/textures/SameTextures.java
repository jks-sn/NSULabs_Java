package ru.nsu.ccfit.berkaev.ui.gui.textures;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class SameTextures {
    private static boolean initWas = false;
    public static final Color DEFAULT_BACKGROUND = new Color(60, 63, 65);
    public static final Color BUTTON_BACKGROUND = new Color(60, 60, 60);
    public static final Color BUTTON_BACKGROUND_PRESSED = new Color(60, 60, 60);

    public static void init()
    {
        if(initWas)
            return;

        UIManager.put("OptionPane.background", DEFAULT_BACKGROUND);
        UIManager.put("OptionPane.messageForeground", Color.LIGHT_GRAY);
        UIManager.put("OptionPane.font", getFont(20, UIManager.getFont("OptionPane.font"), false));

        UIManager.put("MyPanel.background", DEFAULT_BACKGROUND);

        UIManager.put("ComboBox.background", DEFAULT_BACKGROUND);
        UIManager.put("ComboBox.foreground", Color.LIGHT_GRAY);
        UIManager.put("ComboBox.selectionBackground", Color.DARK_GRAY.darker());
        UIManager.put("ComboBox.selectionForeground", Color.LIGHT_GRAY);
        UIManager.put("ComboBox.font", getFont(13, UIManager.getFont("OptionPane.font"), false));

        UIManager.put("MyButton.background", BUTTON_BACKGROUND);
        UIManager.put("MyButton.foreground", Color.LIGHT_GRAY);
        UIManager.put("MyButton.select", BUTTON_BACKGROUND_PRESSED);
        UIManager.put("MyButton.light", BUTTON_BACKGROUND.brighter());
        UIManager.put("MyButton.disabledText", new Color(60, 60, 60));
        UIManager.put("MyButton.font", getFont(13, UIManager.getFont("MyButton.font"), false));

        UIManager.put("Label.font", getFont(15, UIManager.getFont("Label.font"), false));

        UIManager.put("ScrollBar.background", Color.DARK_GRAY);
        initWas = true;
    }

    public static Font getFont(int size, Font currentFont, boolean bold)
    {
        if(currentFont == null)
            return null;
        String resultName;
        try
        {
            loadFont("Sawasdee");
            loadFont("Sawasdee-Bold");
        } catch(Throwable e)
        {
            e.printStackTrace();
        }
        Font testFont = new Font("Sawasdee", Font.PLAIN, 10);

        if(testFont.canDisplay('a') && testFont.canDisplay('1'))
            resultName = "Sawasdee";
        else
            resultName = currentFont.getName();


        return new Font(resultName, bold ? Font.BOLD : Font.PLAIN, size >= 0 ? size : currentFont.getSize());
    }

    private static void loadFont(String name) throws FontFormatException, IOException
    {
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(0, Objects.requireNonNull(SameTextures.class.getResourceAsStream("/res/" + name + ".ttf"))));
    }
}
