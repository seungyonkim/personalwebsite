package com.mygdx.game.monster;

public class Gor extends Monster{ //public Monster(int position, int wp, int sp, int rGold, int rWP)

    public Gor(int position)
    {
        super(position,4,2,2,2);
    }

    public void resetGor()
    {
        this.setStrengthPoint(2);
        this.setWillPower(4);
    }


}
