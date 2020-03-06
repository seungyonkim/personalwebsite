package com.mygdx.game.character;

import com.mygdx.game.board.Region;
import com.mygdx.game.etc.Item;

import java.util.ArrayList;


public abstract class Hero {
    protected Region region;
    protected int gold;
    protected int wp;
    protected int sp;
    protected ArrayList<Item> items;

    //    int diceRoll();
//    int getWP();
//    int getPosition();
//    int getSP();
//    int getGold();
//    int getInventory();
//    int updateGold(int gold);
//    int updateWP(int wp);
}
