package com.mygdx.game.character;

import com.mygdx.game.board.Region;

public class Archer extends Hero {

    public Archer(String username)
    {
        super(25, 0, 7, 1, 25, username);
    }

    public int getNumOfDice() {
        if (this.getWillPower() <= 6) {
            return 3;
        } else if (this.getWillPower() <= 13) {
            return 4;
        } else {
            return 5;
        }
    }
}
