import ui.GameViewInterface;

public class Game {
    Timer timer;
    Board board;

    GameViewInterface ui;

    public Game(int rows, int cols, int mines, boolean isGui) {
        board = new Board(rows, cols, mines);
        timer = new Timer();
    }

    public Game() {
        board = new Board();
        timer = new Timer();
    }

    public void RunGame() {

    }
}
