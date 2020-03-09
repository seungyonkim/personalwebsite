package com.mygdx.game.board;

import com.mygdx.game.character.Hero;
import com.mygdx.game.etc.Farmer;
import com.mygdx.game.etc.Fog;
import com.mygdx.game.etc.Merchant;
import com.mygdx.game.etc.Well;
import com.mygdx.game.monster.Monster;

import java.util.ArrayList;
import java.util.LinkedList;

public class Region {

    private int position;
    private int gold = 0;
    private ArrayList<Integer> heroPath = null;
    private int monsterMoveTo = -1;
    private Well well = null;
    private Fog fog = null;
    private ArrayList<Farmer> farmers = null;
    private ArrayList<Monster> monsters = null;
    private Merchant merchant = null;
    private ArrayList<Hero> heroes = null;

    public Region(int position)
    {
        this.position = position;
        if(position == 18 || position == 57 || position == 71)
        {

        }
    }

    public int getPostion()
    {
        return this.position;
    }

    public void setHeroPath(ArrayList<Integer> regions)
    {
        this.heroPath = regions;
    }

    public void setMonsterMoveTo(int position)
    {
        this.monsterMoveTo = position;
    }

    public ArrayList<Integer> getAvailableHeroPath()
    {
        return this.heroPath;
    }

    public void addHero(Hero hero)
    {
        this.heroes.add(hero);
    }

    public void removeHero(Hero hero)
    {
        this.heroes.remove(hero);
    }

}
