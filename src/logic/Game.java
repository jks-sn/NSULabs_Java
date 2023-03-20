package logic;

import logic.Board;
import timer.Timer;
import ui.GameViewInterface;
import ui.gui.GrafficInterface;
import ui.tui.TextInterface;

import java.util.HashMap;
import java.util.Objects;

public class Game {
    HashMap<String, Integer> commands = new HashMap<String, Integer>() {{
        put("OPEN", 0);
        put("FLAGGED", 1);
        //put("PAUSE", 2);
    }};
    Timer timer;
    Board board;

    static boolean gameOver = false;

    GameViewInterface ui;

    public Game(int rows, int cols, int mines, boolean isGui) {
        board = new Board(rows, cols, mines);
        timer = new Timer();
        if(isGui)
            ui = new GrafficInterface();
        else
            ui = new TextInterface();
    }

    public Game() {
        board = new Board();
        timer = new Timer();
    }

    public void playGame() {
        ui.updateBoard(board);
        while (!gameOver) {
            var input = ui.getCommand();
            if (input.length != 3)
                throw new RuntimeException("Error, wrong command");
            int x = (int) input[1];
            int y = (int) input[2];
            String command = (String) input[0];
            if (Objects.equals(command, "OPEN")) {
                {
                    board.openCell(x, y);
                }
            } else if (command.equals("FLAGGED")) {
                board.changeFlag(x, y);
            }
            else {
                throw new RuntimeException("Unknown command");
            }
            ui.updateBoard(board);
        }
    }
}
