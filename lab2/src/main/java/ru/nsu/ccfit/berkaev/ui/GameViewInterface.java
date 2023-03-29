package ru.nsu.ccfit.berkaev.ui;

import ru.nsu.ccfit.berkaev.logic.Board;
import ru.nsu.ccfit.berkaev.timer.Timer;

public interface GameViewInterface {
    void updateFlag(int x, int y, Board board);
    void updateBoard(Board board);

    void writeFirstCommandMessage();
    void updateTimer(Timer timer);
    void restartGame();
    void noticeWinGame();
    void noticeLoseGame();
    void updatePause();
    void writeMessageUnknownCommand();
    Object[] getCommand();
}
