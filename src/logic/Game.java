package logic;

import logic.Board;
import timer.Timer;
import ui.GameViewInterface;
import ui.gui.GrafficInterface;
import ui.tui.TextInterface;

import java.util.HashMap;
import java.util.Objects;

public class Game {
    private HashMap<String, Integer> commands = new HashMap<String, Integer>() {{
        put("OPEN", 0);
        put("FLAGGED", 1);
        //put("PAUSE", 2);
    }};
    private Timer timer;
    private Board board;

    static boolean gameOver = false;

    GameViewInterface ui;

    public Game(int rows, int cols, int mines, boolean isGui) {
        board = new Board(rows, cols, mines);
        if(isGui)
            ui = new GrafficInterface();
        else
            ui = new TextInterface();
    }

    public Game() {
        board = new Board();
    }

    public void playGame() {
        var input = ui.getCommand();
        String command = (String) input[0];
        int x;
        int y;
        while(!Objects.equals(command, "OPEN"))
        {
            ui.writeFirstCommandMessage();
            input = ui.getCommand();
            command = (String) input[0];
        }
        x = (int)input[1];
        y = (int)input[2];
        board.openCell(x,y);
        timer = new Timer();
        ui.updateBoard(board);
        board.setMines();
        while (!gameOver && (board.getNumberOpenCells() + board.getNumberMines() != board.getColumns()*board.getRows())) {
            if (input.length != 3)
                throw new RuntimeException("Error, wrong command");
            input = ui.getCommand();
            command = (String) input[0];
            x = (int)input[1];
            y = (int)input[2];
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
