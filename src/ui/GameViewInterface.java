package ui;

import logic.Board;
import timer.Timer;

public interface GameViewInterface {
    void updateFlag(int x, int y, Board board);
    void updateBoard(Board board);
    void updateTimer(Timer timer);
    void restartGame();
    void noticeWinGame();
    void noticeLoseGame();
    void updatePause();

    Object[] getCommand();
}
