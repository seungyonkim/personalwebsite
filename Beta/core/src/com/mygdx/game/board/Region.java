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

    private ArrayList<Hero> heroes;
    private int position;
    private int gold = 0;
    private int[] heroAvailablePaths;
    private int monsterAvailablePath = -1;
    private Well well;
    private Fog fog;
    private ArrayList<Farmer> farmers;
    private Monster monster;
    private Merchant merchant;

    public Region(int position)
    {
        this.position = position;
        this.heroes = new ArrayList<Hero>();
        if(position == 18 || position == 57 || position == 71)
        {
            this.merchant = new Merchant();
        }
    }
    public ArrayList<Hero> getHeroes() { return this.heroes; }
    public int getPosition() { return this.position; }
    public int getGold() { return this.gold; }
    public int[] getAvailableHeroPaths() { return this.heroAvailablePaths; }
    public int getAvailableMonsterPath() { return this.monsterAvailablePath; }
    public Well getWell() { return this.well; }
    public Fog getFog() { return this.fog; }
    public ArrayList<Farmer> getFarmers() { return this.farmers; }
    public Monster getMonster() { return this.monster; }
    public Merchant getMerchant() { return this.merchant; }

    public void setAvailableHeroPaths(int[] regions)
    {
        this.heroAvailablePaths = regions;
    }

    public void setAvailabeMonsterPath(int nextPosition)
    {
        this.monsterAvailablePath = nextPosition;
    }

    public void removeHero(Hero hero)
    {
        this.heroes.remove(hero);
    }
    public void addHero(Hero hero)
    {
        this.heroes.add(hero);
    }

    public void removeMonster(Monster monster)
    {
        if(this.monster == monster)
            this.monster = null;
    }
    public void addMonster(Monster monster)
    {
        this.monster = monster;
    }

}
