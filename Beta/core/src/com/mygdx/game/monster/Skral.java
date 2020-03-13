package com.mygdx.game.monster;

public class Skral extends Monster {
    public Skral(int position)
    {
        super(position,6,6,4,4);
    }

    public void resetSkral()
    {
        this.setStrengthPoint(6);
        this.setWillPower(6);
    }

    public String getMonsterType() {
        return "Skral";
    }

}