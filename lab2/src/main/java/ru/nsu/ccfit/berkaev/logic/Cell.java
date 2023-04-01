package ru.nsu.ccfit.berkaev.logic;

public class Cell {
    private boolean mine;
    public enum states{
         CLOSE, OPEN, FLAG, MINE
    }
    private int state;
    private int surroundingMines;

    public Cell() {
        mine = false;
        state = states.CLOSE.ordinal();
        surroundingMines = 0;
    }
    public void setMine() {
        mine = true;
        this.state = states.MINE.ordinal();
    }
    public boolean getMine()
    {
        return mine;
    }
    public int getSurroundingMines() {
        return surroundingMines;
    }
    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }
    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }

}
