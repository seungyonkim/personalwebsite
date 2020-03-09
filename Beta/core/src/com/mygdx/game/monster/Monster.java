package com.mygdx.game.monster;

import com.mygdx.game.board.Region;
import com.mygdx.game.character.Hero;
import com.mygdx.game.etc.Castle;

import java.util.ArrayList;

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
        from.removeMonster(this);
        to.addMonster(this);

        if(to.getPosition() == 0 && to instanceof Castle) {
            Castle c = (Castle)to;
            boolean result = c.decrementShield();
            to.removeMonster(this);
            this.position = 80;
            return result;
        }
        return true;
    }


}
