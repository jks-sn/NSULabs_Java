package ru.nsu.ccfit.berkaev;

import ru.nsu.ccfit.berkaev.logic.Game;

public class Main {
    public static void main(String[] args) {
        boolean isGUI = false;
        int rows = 10, cols = 10, mines = 9;
        Game game;
        switch (args.length) {
            case 0 -> {
            }
            case 1 -> {
                if (args[0].equalsIgnoreCase("gui")) {
                    isGUI = true;
                } else {
                    System.out.println("Unknown arg: " + args[0]);
                    return;
                }
            }
            case 4 -> {
                if (args[0].equalsIgnoreCase("gui")) {
                    isGUI = true;
                } else {
                    System.out.println("Unknown arg: " + args[0]);
                    return;
                }
                rows = Integer.parseInt(args[1]);
                cols = Integer.parseInt(args[2]);
                mines = Integer.parseInt(args[3]);
            }
            default -> {
                System.out.println("Wrong number args");
                return;
            }
        }
        game = new Game(rows,cols,mines, isGUI);
        game.playGame();
    }
}
