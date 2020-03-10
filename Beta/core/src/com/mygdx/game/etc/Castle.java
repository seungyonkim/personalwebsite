package com.mygdx.game.etc;

import com.mygdx.game.board.Region;

import java.util.ArrayList;

public class Castle extends Region {
    private int shield;
    private int countFarmer = 0;

    public Castle(int numPlayers, int position) {
        super(position);
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

    public int getShied() {
        return this.shield;
    }

    public boolean decrementShield() {
        if (shield == 1) return false; //Game ends
        else {
            shield--;
            return true;
        }
    }

    public int getCountFarmer() { return this.countFarmer; }
}

