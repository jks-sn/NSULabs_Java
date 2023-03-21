package logic;

public class Cell {
    private boolean mine;
    private boolean flag;
    private boolean locked;
    private int surroundingMines;

    public Cell() {
        mine = false;
        flag = false;
        locked = true;
        surroundingMines = 0;
    }

    public boolean getMine() {
        return this.mine;
    }

    public void setMine() {
        this.mine = true;
    }

    public int getSurroundingMines() {
        return surroundingMines;
    }

    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }
    public void changeFlag() {
        if (locked)
        {
            flag = !flag; //check
        }
    }
    public boolean getLocked()
    {
        return locked;
    }
    public boolean getFlag()
    {
        return flag;
    }
    public void open()
    {
        locked = false;
    }

}
