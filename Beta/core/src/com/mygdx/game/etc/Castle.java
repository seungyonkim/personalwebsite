package com.mygdx.game.etc;

import com.mygdx.game.board.Board;
import com.mygdx.game.board.Region;

import java.util.ArrayList;

public class Castle extends Region {
    private int shield;
    private int farmerCount = 0;

    public Castle(Board board, int numPlayers, int position) {
        super(board, position);
        switch (numPlayers) {
            case 2:
                shield = 3;
                break;
            case 3:
                shield = 2;
                break;
            case 4:
                shield = 1;
                break;
        }
    }

    public int getShield() {
        return this.shield;
    }

    public void farmerArrive() {
        this.shield++;
        this.farmerCount++;
        System.out.println("Farmer arrived in the castle. Castle now has "+this.shield+" shields.");
    }

    public boolean decrementShield() {
        if (shield == 1) return false; //Game ends
        else {
            shield--;
            return true;
        }
    }

    public int getFarmerCount() { return this.farmerCount; }
}

