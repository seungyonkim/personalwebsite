package com.mygdx.game.character;

import com.mygdx.game.board.Region;
import com.mygdx.game.etc.Item;

import java.util.ArrayList;

public class Archer extends Hero{ // Start at region - 25

    private int position;
    private int willPower;
    private int strengthPoint;
    private int gold;
    private int gender; // 0 : male, 1 : female
    private ArrayList<Item> items = null;
    private final int RANK = 25;
    private int hour = 0;

    public Archer(int position, int gold, int willPower, int strengthPoint, int rank)
    {
        this.position = position;
    }



}
