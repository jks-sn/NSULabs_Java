
import static src.utils.UtilsBoard.countValidCoordinates;
import src.Cell;
public class Board {
    int mines;
    private Cell cells[][];
    private int rows;
    private int columns;

    public Board(int numberRows, int numberColls, int numberMines) {
        mines = numberMines;
        rows = numberRows;
        columns = numberColls;
        cells = new Cell[rows][columns];
    }
    public Board()
    {
        mines = 10;
        rows = 9;
        columns = 9;
        cells = new Cell[rows][columns];
    }
    public void setMines() { //придумать что-нибудь получше
        int x, y, i = 0;
        while (i != mines) {
            x = (int) Math.floor(Math.random() * rows);
            y = (int) Math.floor(Math.random() * columns);
            if (!cells[x][y].getMine()) {
                ++i;
                cells[x][y].setMine();
            }
        }
    }

    public void setNeighborMines() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                cells[i][j].setSurroundingMines(calcNeighboursMines(i, j));
            }
        }
    }

    public int calcNeighboursMines(int x, int y) {
        int neighbours = 0;
        for (int i = countValidCoordinates(x - 1, rows); i < countValidCoordinates(x + 1, rows); ++i) {
            for (int j = countValidCoordinates(y - 1, columns); j < countValidCoordinates(y + 1, columns); ++j) {
                if (i != x || j != y) {
                    neighbours++;
                }
            }
        }
        return neighbours;
    }
}
