package com.mygdx.game.etc;

public class Well { // 5, 35, 45, 55
    private int position;
    private boolean isEmpty;

    public Well(int position) {
        this.position = position;
        isEmpty = false;
    }

    public void empty() {
        this.isEmpty = true;
    }

    public void replenish() {
        this.isEmpty = false;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

}
