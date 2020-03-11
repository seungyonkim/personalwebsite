package com.mygdx.game.character;

import com.mygdx.game.board.Board;
import com.mygdx.game.board.Region;
import com.mygdx.game.etc.Farmer;
import com.mygdx.game.etc.Item;
import com.mygdx.game.etc.Well;

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
    private Farmer farmer;

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

    public Farmer getFarmer() {
        return this.farmer;
    }

    public void addGold(int gold)
    {
        this.gold += gold;
    }

    public void addWillPower(int wp)
    {
        this.willPower += wp;
        if(this.willPower > 20) this.willPower = 20;
    }

    public void addStrengthPoint(int sp)
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
                if(this.farmer != null) {
                    from.removeFarmer();
                    to.addFarmer(this.farmer);
                    this.farmer.setPosition(to.getPosition());
                }
                from.removeHero(this);
                to.addHero(this);
                this.position = to.getPosition();
                incrementHours();
            }
            return result;
        }
        return false;
    }

    public void incrementHours()
    {
        if (hours < 7) this.hours++;
        else if(7 <= hours && hours < 10 && willPower >= 2)
        {
            this.hours++;
            this.willPower -= 2;
            if (hours == 10) {
                this.canPlay = false;
            }
        }
        else if(hours == 10) this.canPlay = false;
    }

    public void pass()
    {
        if(canPlay)
        {
            incrementHours();
        }
    }

    /*
    1 = archer
    2 = dwarf
    3 = warrior
    4 = wizard
     */
    public int getTypeOfHero() {
        if(this instanceof Archer) return 1;
        if(this instanceof Dwarf) return 2;
        if(this instanceof Warrior) return 3;
        if(this instanceof Wizard) return 4;
        else return -1;
    }

    public ArrayList<Region> getAvailableRegions(Board board)
    {
        Region r = board.getRegion(this.position);
        return board.getHeroAvailablePaths(r);
    }


    public void dropGold() {
        this.gold--;
    }

    public void pickUpGold() {
        this.gold++;
    }

    public void drinkWell(Well well) {
        this.willPower += 3;
        well.empty();
    }

    public void pickupFarmer(Farmer farmer)
    {
        this.farmer = farmer;
    }

}
