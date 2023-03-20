package logic;

import static utils.UtilsBoard.countValidCoordinates;

public class Board {
    int mines;
    int numberOpenCells;
    private final Cell[][] cells;
    private final int rows;
    private final int columns;

    public Board(int numberRows, int numberColls, int numberMines) {
        mines = numberMines;
        rows = numberRows;
        columns = numberColls;
        numberOpenCells = 1;
        cells = new Cell[rows][columns];
    }

    public Board() {
        mines = 10;
        rows = 9;
        columns = 9;
        numberOpenCells = 1;
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

    public void changeFlag(int x, int y) {
        cells[x][y].changeFlag();
    }

    public void openCell(int x, int y) {
        if (!cells[x][y].getLocked())
            return;
        if (cells[x][y].getMine())
            Game.gameOver = true;
        numberOpenCells++;
        cells[x][y].open();
        if (cells[x][y].getSurroundingMines() == 0) {
            for (int i = countValidCoordinates(x - 1, rows); i < countValidCoordinates(x + 1, rows); ++i) {
                for (int j = countValidCoordinates(y - 1, columns); j < countValidCoordinates(y + 1, columns); ++j) {
                    if (i != x || j != y) {
                        openCell(i, j);
                    }
                }
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

    public boolean isMinned(int x, int y) {
        return cells[x][y].getMine();
    }

    public boolean isFlagged(int x, int y) {
        return cells[x][y].getFlag();
    }

    public boolean isClose(int x, int y) {
        return cells[x][y].getLocked();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
