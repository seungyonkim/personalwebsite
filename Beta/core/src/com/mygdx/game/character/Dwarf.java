package com.mygdx.game.character;

public class Dwarf extends Hero{
    public Dwarf(String username)
    {
        super(7, 0, 7, 1, 7, username);
    }

    public int getNumOfDice() {
        if (this.getWillPower() <= 6) {
            return 1;
        } else if (this.getWillPower() <= 13) {
            return 2;
        } else {
            return 3;
        }
    }
}


