package com.mygdx.game.etc;

import com.mygdx.game.board.Board;
import com.mygdx.game.board.Region;
import com.mygdx.game.character.Hero;

public class Merchant extends Region {

    public Merchant(Board board, int position) // 18 , 57 , 71
    {
        super(board, position);
    }

    public boolean sellSP(Hero hero) {
        System.out.println("check");
        if(hero.getGold() >= 2) {
            hero.addGold(-2);
            hero.addStrengthPoint(1);
            System.out.println("sell sp");
            return true;
        }
        return false;
    }

    public boolean sellWineskin(Hero hero){
        if(hero.getGold() >= 2){
            hero.addGold(-2);
            //hero.addWineskin();
            System.out.println("sell wineskin");
            return true;
        }
        return false;
    }


    public boolean sellShield(Hero hero){
        if(hero.getGold() >= 2){
            hero.addGold(-2);
            System.out.println("sell shield");
            return true;
        }
        return false;
    }

    public boolean sellBow(Hero hero){
        if(hero.getGold() >= 2){
            hero.addGold(-2);
            System.out.println("sell bow");
            return true;
        }
        return false;
    }


    public boolean sellHelm(Hero hero){
        if(hero.getGold() >= 2){
            hero.addGold(-2);
            System.out.println("sell helm");
            return true;
        }
        return false;
    }


    public boolean sellFalcon(Hero hero){
        if(hero.getGold() >= 2){
            hero.addGold(-2);
            System.out.println("sell falcon");
            return true;
        }
        return false;
    }



    public boolean sellTelescope(Hero hero){
        if(hero.getGold() >= 2){
            hero.addGold(-2);
            System.out.println("sell telescope");
            return true;
        }
        return false;
    }

}