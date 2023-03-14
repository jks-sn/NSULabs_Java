package ui;

public interface GameViewInterface {
    void updateFlag(int x, int y);
    void updateField();
    void updateTimer();
    void restartGame();
    void noticeWinGame();
    void noticeLoseGame();
    void updatePause();
    void openCell();
}
