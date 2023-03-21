package ui.gui;

import logic.Board;
import timer.Timer;
import ui.GameViewInterface;

public class GrafficInterface implements GameViewInterface {
    @Override
    public void updateFlag(int x, int y, Board board) {

    }

    @Override
    public void updateBoard(Board board) {

    }

    @Override
    public void writeFirstCommandMessage() {

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
    public Object[] getCommand() {
        return new Object[0];
    }
}
