package com.mygdx.game.monster;

import com.mygdx.game.board.Region;
import com.mygdx.game.character.Hero;
import com.mygdx.game.etc.Castle;

import java.util.ArrayList;
import java.util.Random;

public class Monster {
    private int position; // if position == 80 then this monster is no more available.
    private int willPower;
    private int strengthPoint;
    private int rewardGold;
    private int rewardWP;

    public Monster(int position, int wp, int sp, int rGold, int rWP)
    {
        this.position = position;
        this.willPower = wp;
        this.strengthPoint = sp;
        this.rewardGold = rGold;
        this.rewardWP = rWP;
    }

    public int getPosition() {
        return this.position;
    }
    public int getWillPower(){
        return this.willPower;
    }
    public int getStrengthPoint() {
        return this.strengthPoint;
    }
    public int getRewardGold(){
        return this.rewardGold;
    }
    public int getRewardWP(){
        return this.rewardWP;
    }
    public void setPosition(int position) { this.position = position; }
    public void setWillPower(int wp) {
        this.willPower = wp;
    }
    public void setStrengthPoint(int sp) {
        this.strengthPoint = sp;
    }

    public void singleRewardGold(Hero hero) {
        hero.addGold(this.rewardGold);
    }
    public void singleRewardWP(Hero hero) {
        hero.addWillPower(this.rewardWP);
    }
    public boolean singleRewardBoth(Hero hero, int gold, int wp)
    {
        int sum = gold + wp;
        if (sum != rewardGold) return false;
        hero.addGold(gold);
        hero.addWillPower(wp);
        return true;
    }

    public boolean multiRewardGold(ArrayList<Hero> heroes, int[] gold)
    {
        int index = 0;
        for(int g : gold) { index += g; }
        if(index != this.rewardGold) return false;

        index = 0;
        for(Hero h : heroes)
        {
            h.addGold(gold[index]);
            index++;
        }
        return true;

    }
    public boolean multiRewardWP(ArrayList<Hero> heroes, int[] wp)
    {
        int index = 0;
        for(int w: wp) { index += w; }
        if(index != this.rewardWP) return false;

        index = 0;
        for(Hero h : heroes)
        {
            h.addWillPower(wp[index]);
            index++;
        }
        return true;
    }
    public boolean multiRewardBoth(ArrayList<Hero> heroes, int[] gold, int[] wp)
    {
        int index = 0;
        for(int g: gold) { index += g; }
        for(int w: wp) { index += w; }
        if(index != this.rewardGold) return false;

        for(Hero h : heroes)
        {
            h.addGold(gold[index]);
            h.addWillPower(wp[index]);
            index++;
        }
        return true;
    }

    // If method returns false, the game is over since the number of shields == 0.
    public boolean moveTo(Region from, Region to)
    {
        this.position = to.getPosition();
        from.removeMonster();
        to.addMonster(this);

        if(to.getPosition() == 0 && to instanceof Castle) {
            Castle c = (Castle)to;
//            boolean result = c.decrementShield();
            c.decrementShield();
            to.removeMonster();
            this.position = 80;
            System.out.println("Monster in. Remaining shields: "+c.getShield());
            if (this instanceof Gor) {
                System.out.println("Gor in castle.");
            } else {
                System.out.println("Skral in castle.");
            }
            if (c.getShield() <= 0) {
                return false;
            } else {
                return true;
            }
        }

        return true;
    }

    public void deductWP(int deduct) {
        this.willPower -= deduct;
        if (this.willPower < 0) {
            this.willPower = 0;
        }
    }

    public int getNumOfDice() {
        if (this.getWillPower() <= 6) {
            return 2;
        } else {
            return 3;
        }
    }

    public int rollDice() {
        int highestValue;
        int numOfDice = this.getNumOfDice();
        Random r = new Random();

        if (numOfDice == 2) {
            int first = r.nextInt(6)+1;
            int second = r.nextInt(6)+1;
            if (first > second) {
                highestValue = first;
            } else if (first < second) {
                highestValue = second;
            } else {
                highestValue = first + second;
            }
        } else {
            int first = r.nextInt(6)+1;
            int second = r.nextInt(6)+1;
            int third = r.nextInt(6)+1;
            if (first == second) {
                if (first+second > third) {
                    highestValue = first+second;
                } else {
                    highestValue = third;
                }
            } else if (second == third) {
                if (second+third > first) {
                    highestValue = second+third;
                } else {
                    highestValue = first;
                }
            } else if (first == third) {
                if (first+third > second) {
                    highestValue = first+third;
                } else {
                    highestValue = second;
                }
            } else {
                highestValue = Math.max(Math.max(first, second), third);
            }
        }

        return highestValue;
    }


}