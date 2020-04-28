package com.mygdx.game.etc;

public class Well { // 5, 35, 45, 55
    private int position;
    private boolean isEmpty;

    public Well(int position) {
        this.position = position;
        this.isEmpty = false;
    }

    public int getPosition(){ return this.position; }

    public void empty() {
        this.isEmpty = true;
    }

    public void replenish() {
        this.isEmpty = false;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        else
        {
            if(o instanceof Well)
            {
                Well pWell = (Well) o;
                return (pWell.getPosition() == this.getPosition() && pWell.isEmpty() == this.isEmpty());
            }
            else return false;
        }
    }

}