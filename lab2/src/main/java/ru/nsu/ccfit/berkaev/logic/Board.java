package ru.nsu.ccfit.berkaev.logic;

import static ru.nsu.ccfit.berkaev.utils.UtilsBoard.countCoordinates;

public class Board {
    private final int mines;
    private final int numberOpenCells;
    private Cell[][] cells;
    private final int rows;
    private final int columns;

    public Board(int numberRows, int numberColumns, int numberMines) {
        mines = numberMines;
        rows = numberRows;
        columns = numberColumns;
        numberOpenCells = 0;
        createEmptyBoard();
        setMines();
        calcNeighboursMines();
    }
    public Board()
    {
        this(9,9,10);
    }
    public void createEmptyBoard()
    {
        cells = new Cell[columns][rows];
        for (int x = 0; x < columns; x++)
        {
            for (int y = 0; y < rows; y++)
            {
                cells[x][y] = new Cell();
            }
        }
    }
    public void setMines() {
        int x, y, numberMines = 0;
        while (numberMines != mines) {
            x = (int) Math.floor(Math.random() * rows);
            y = (int) Math.floor(Math.random() * columns);
            if (!cells[x][y].getMine()) {
                ++numberMines;
                System.out.print(x + " " + y + "\n");
                cells[x][y].setMine();
            }
        }
    }

/*    public Cell[][] getCells()
    {
        return cells;
    }*/
    public void calcNeighboursMines(){
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                calcNeighboursMines(row,col);
            }
        }
    }
    public int getSurroundingMines(int x, int y)
    {
        return(cells[x][y].getSurroundingMines());
    }
    public void calcNeighboursMines(int x, int y) {
        int neighbours = 0;
        for (int i = countCoordinates(x - 1, rows); i <= countCoordinates(x + 1, rows); ++i) {
            for (int j = countCoordinates(y - 1, columns); j <= countCoordinates(y + 1, columns); ++j) {
                if ((i != x || j != y) && cells[i][j].getMine()) {
                    neighbours++;
                }
            }
        }
        cells[x][y].setSurroundingMines(neighbours);
    }
    public int getState(int x, int y) {
        return cells[x][y].getState();
    }
    public void setState(int x, int y, int state) {
        cells[x][y].setState(state);
    }
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
    public int getSize(){return rows*columns;}

    public int getNumberOpenCells() {
        return numberOpenCells;
    }
    public boolean getMine(int x, int y)
    {
        return(cells[x][y].getMine());
    }

    public int getNumberMines() {
        return mines;
    }
}
