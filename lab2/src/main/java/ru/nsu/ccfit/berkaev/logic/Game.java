package ru.nsu.ccfit.berkaev.logic;

import ru.nsu.ccfit.berkaev.timer.Timer;
import ru.nsu.ccfit.berkaev.ui.GameViewInterface;
import ru.nsu.ccfit.berkaev.ui.gui.GameFrame;
import ru.nsu.ccfit.berkaev.ui.tui.TextInterface;

import java.util.HashMap;

public class Game {
    private HashMap<String, Integer> commands = new HashMap<String, Integer>() {{
        put("OPEN", 0);
        put("FLAGGED", 1);
        //put("PAUSE", 2);
    }};
    private Timer timer;
    private Board board;

    static public boolean gameOver = false;

    private boolean gameStarted = false;


    public Game(int rows, int cols, int mines, boolean isGui) {
        board = new Board(rows, cols, mines);
    }

    public Game() {
        board = new Board();
    }

    public boolean getGameStarted()
    {
        return gameStarted;
    }

    public void playGame() {
        gameStarted = true;
        var input = ui.getCommand();
        String command = (String) input[0];
        int x;
        int y;
        while (!command.equalsIgnoreCase("open")) {
            ui.writeFirstCommandMessage();
            input = ui.getCommand();
            command = (String) input[0];
        }
        x = (int) input[1];
        y = (int) input[2];
        timer = new Timer();
        board.setMines(x, y);
        board.calcNeighboursMines();
        board.openCell(x,y);
        ui.updateBoard(board);
        while (!gameOver && (board.getNumberOpenCells() + board.getNumberMines() != board.getColumns()*board.getRows())) {
            if (input.length != 3)
                throw new RuntimeException("Error, wrong command");
            input = ui.getCommand();
            command = (String) input[0];
            x = (int)input[1];
            y = (int)input[2];
            if (command.equalsIgnoreCase("open")) {
                {
                    board.openCell(x, y);
                }
            } else if (command.equalsIgnoreCase("flag")) {
                board.changeFlag(x, y);
            }
            else {
                ui.writeMessageUnknownCommand();
            }
            ui.updateBoard(board);
        }
    }
}
