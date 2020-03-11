package com.mygdx.game.etc;


import com.mygdx.game.board.Board;
import com.mygdx.game.board.Region;
import com.mygdx.game.character.Hero;

public class Farmer {
    private int position;
    private int number;
    private boolean alive;

    public Farmer(int position, int number)
    {
        this.position = position;
        this.number = number;
        this.alive = true;
    }

    public int getPosition()
    {
        return this.position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getFarmerNumber() {
        return this.number;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void die() {
        this.alive = false;
    }

//    public void moveFarmer(Region from, Region to) {
//        from.removeFarmer(this);
//        to.addFarmer(this);
//        this.setPosition(to.getPosition());
//    }
//
//    public void inCastle(Region from, Region to, Hero hero) {
//        this.die();
//        from.removeFarmer(this);
//        hero.dropOffFarmer(this);
//        to.getBoard().getCastle().farmerArrive();
//    }
//
//    public void dieFromMonster(Region from, Region to, Hero hero) {
//        this.die();
//        from.removeFarmer(this);
//        hero.dropOffFarmer(this);
//        System.out.println("Farmer died because he encountered a monster.");
//    }

}

