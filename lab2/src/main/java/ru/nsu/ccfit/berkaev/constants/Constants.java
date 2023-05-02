package ru.nsu.ccfit.berkaev.constants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Constants {
    public static Font font = new Font("Serif", Font.BOLD, 24);
    public static Border border = BorderFactory.createLineBorder(Color.black, 1, true);
    public static final String FRAME_TITLE = "Minesweeper";
    public static final int FRAME_WIDTH = 490;
    public static final int FRAME_HEIGHT = 520;
    public static final int FRAME_LOC_X = 400;
    public static final int FRAME_LOC_Y = 20;
    public static class Paths {
        public static String minePath = "/mine.png";
        public static String timerPath = "/timer.png";
        public static String backgroundPath = "/background.jpg";
        public static String redminePath = "/redmine.png";
        public static String flagPath = "/flag.png";
        public static String closetPath = "/closet.png";


    }

    public enum Digits {
        ZERO("0", 0, 0, 0),
        ONE("1", Color.blue),
        TWO("2", 51, 103, 1),
        THREE("3", Color.red),
        FOUR("4", 153, 0, 0),
        FIVE("5", 153, 0, 153),
        SIX("6", 96, 96, 96),
        SEVEN("7", 0, 0, 102),
        EIGHT("8", 153, 0, 76);

        private final String number;
        private final Color color;

        Digits(String number, Color color) {
            this.number = number;
            this.color = color;
        }

        Digits(String number, int red, int green, int blue) {
            this.number = number;
            color = new Color(red, green, blue);
        }

        public static Color getColor(String number) {
            return switch (number) {
                case ("0") -> ZERO.color;
                case ("1") -> ONE.color;
                case ("2") -> TWO.color;
                case ("3") -> THREE.color;
                case ("4") -> FOUR.color;
                case ("5") -> FIVE.color;
                case ("6") -> SIX.color;
                case ("7") -> SEVEN.color;
                case ("8") -> EIGHT.color;
                default -> null;
            };
        }
    }
    public static class hideAll{
        public static String text = "";
        public static Color backgroundColor = new Color(0, 103, 200);
    }
    public static class makeLabel{
        public static String labelText = "  0  ";
        public static Color colorBackground = new Color(110, 110, 255);
    }
    public static class downPanel{
        public static BorderLayout downPanelBorderLayout = new BorderLayout(0, 20);
    }
    public static class Panel{
        public static BorderLayout panelBorderLayout = new BorderLayout(10, 0);
        public static int BorderLayoutHgap = 10;
        public static int BorderLayoutVgap = 0;
        public static String panelIconText = "";
    }
    public static class Window{
        public static BorderLayout windowBorderLayout = new BorderLayout(0, 10);
        public static Border windowBorder = BorderFactory.createEmptyBorder(60, 60, 14, 60);
    }
    public static class EndGame{
        public static String exitText = "Exit";
        public static String playAgainText = "PlayAgain";
        public static String looseGameMesage = "You lose the game!";
        public static String winGameMesage = "You win the game!";
        public static String dialogWinText = "Game Won";
        public static String dialogLooseText = "Game Lost";
        public static GridLayout buttonsGridLayout = new GridLayout(1, 2, 10, 0);
        public static BorderLayout borderLayout = new BorderLayout(20, 20);
        public static Border endGameBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

    }
}
