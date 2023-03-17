
public class Game {
    Timer timer;
    Board board;

    public Game(int rows, int cols, int mines)
    {
        board = new Board(rows,cols, mines);
        timer = new Timer();
    }
    public Game()
    {
        board = new Board();
        timer = new Timer();
    }
}
