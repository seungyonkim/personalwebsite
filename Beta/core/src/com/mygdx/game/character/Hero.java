package com.mygdx.game.character;

import com.mygdx.game.board.Region;
import com.mygdx.game.etc.Item;

import java.util.ArrayList;

public class Hero {

    private int position;
    private int gold;
    private int willPower;
    private int strengthPoint;
    private int rank;
    private String username;
    private ArrayList<Item> items;
    private int hours = 0;
    private boolean canPlay = true;

    public Hero(int position, int gold, int wp, int sp, int rank, String name)
    {
        this.position = position;
        this.gold = gold;
        this.willPower = wp;
        this.strengthPoint = sp;
        this.rank = rank;
        this.username = name;
        this.items = new ArrayList<Item>();
    }

    public int diceRoll()
    {
        // Based : 1 dice
        return (int)(Math.random() * 6) + 1;

    }

    public int getWillPower()
    {
        return this.willPower;
    }

    public int getPosition()
    {
        return this.position;
    }

    public int getStrengthPoint()
    {
        return this.strengthPoint;
    }

    public int getGold()
    {
        return this.gold;
    }

    public int getRank() { return this.rank; }
    public String getUsername() { return this.username; }


    public ArrayList<Item> getItems()
    {
        return this.items;
    }

    public int getHours()
    {
        return this.hours;
    }

    public void updateGold(int gold)
    {
        this.gold += gold;
    }

    public void updateWillPower(int wp)
    {
        this.willPower += wp;
        if(this.willPower > 20) this.willPower = 20;
    }

    public void updateStrengthPoint(int sp)
    {
        this.strengthPoint += sp;
        if(this.strengthPoint > 14) this.strengthPoint = 14;
    }


    public boolean moveTo(Region from, Region to) {
        if(canPlay)
        {
            int[] available = from.getAvailableHeroPaths();
            boolean result = false;
            for(int a : available) {
                if(a == to.getPosition()) {
                    result = true;
                    break;
                }
            }
            if(result) {
                from.removeHero(this);
                to.addHero(this);
                this.position = to.getPosition();
                updateHours();
                return result;
            }
            return result;
        }
        return false;
    }

    public void updateHours()
    {
        if (hours < 7) this.hours++;
        else if(7 <= hours && hours < 10 && willPower >= 2)
        {
            this.hours++;
            this.willPower -= 2;
        }
        else if(hours == 10) this.canPlay = false;
    }

    public void pass()
    {
        if(canPlay)
        {
            updateHours();
        }
    }

    /*
    1 = archer
    2 = dwarf
    3 = warrior
    4 = wizard
     */
    public int getTypeOfHero(Hero hero) {
        if(hero instanceof Archer) return 1;
        if(hero instanceof Dwarf) return 2;
        if(hero instanceof Warrior) return 3;
        if(hero instanceof Wizard) return 4;
        else return -1;
    }

}
