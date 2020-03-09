package com.mygdx.game.character;

import com.mygdx.game.board.Region;
import com.mygdx.game.etc.Item;

import java.util.ArrayList;


public class Hero {

    protected int position;
    protected int gold;
    protected int willPower;
    protected int strengthPoint;
    protected int rank;
    protected ArrayList<Item> items;

    public int diceRoll()
    {

    }
    int getWP()
    {
        return this.getWP();
    }
//    int getPosition();
//    int getSP();
//    int getGold();
//    int getInventory();
//    int updateGold(int gold);
//    int updateWP(int wp);

    public void moveTo(Region from, Region to) {
        from.removeHero(this);
        to.addHero(this);
        this.position = to.getPostion();
    }

}
