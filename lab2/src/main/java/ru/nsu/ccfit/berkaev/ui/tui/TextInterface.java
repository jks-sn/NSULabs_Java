package ru.nsu.ccfit.berkaev.ui.tui;

import ru.nsu.ccfit.berkaev.logic.Board;
import ru.nsu.ccfit.berkaev.timer.Timer;
import ru.nsu.ccfit.berkaev.ui.GameViewInterface;

import java.util.HashMap;
import java.util.Scanner;

public class TextInterface implements GameViewInterface {
    HashMap<String, Integer> commands = new HashMap<String, Integer>() {{
        put("command1", 1);
        put("command2", 2);
        put("command3", 3);
    }};

    @Override
    public void updateFlag(int x, int y, Board board) {
        board.changeFlag(x,y);
    }

    @Override
    public void updateBoard(Board board) {
        System.out.print("  ");
        for (int col = 0; col < board.getColumns(); col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        for (int row = 0; row < board.getRows(); row++) {
            System.out.print(row + " ");
            for (int col = 0; col < board.getColumns(); col++) {
                if (board.isFlagged(row, col)) {
                    System.out.print("* ");
                } else if (board.isClose(row, col)) {
                    System.out.print("- ");
                } else {
                    System.out.print(board.getSurroundingMines(row,col) + " ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void writeFirstCommandMessage() {
        System.out.print("Please, open first cell");
    }

    @Override
    public void updateTimer(Timer timer) {

    }


    @Override
    public void restartGame() {

    }

    @Override
    public void noticeWinGame() {

    }

    @Override
    public void noticeLoseGame() {

    }

    @Override
    public void updatePause() {

    }

    @Override
    public void writeMessageUnknownCommand() {
        System.out.println("Unknown command");
    }

    public Object[] getCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter command, row and column (e.g. open 3 4): ");
        return new Object[]{scanner.next(), scanner.nextInt(), scanner.nextInt()};
    }
}
