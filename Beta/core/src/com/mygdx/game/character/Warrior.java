package com.mygdx.game.character;

public class Warrior extends Hero {
    public Warrior(String username)
    {
        super(14, 0, 7, 1, 14, username);
    }

    public int getNumOfDice() {
        if (this.getWillPower() <= 6) {
            return 2;
        } else if (this.getWillPower() <= 13) {
            return 3;
        } else {
            return 4;
        }
    }
}
