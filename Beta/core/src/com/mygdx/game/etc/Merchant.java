package com.mygdx.game.etc;

import com.mygdx.game.board.Region;
import com.mygdx.game.character.Hero;

public class Merchant extends Region {

    public Merchant(int position) // 18 , 57 , 71
    {
        super(position);
    }

    public boolean buySP(Hero hero) {
        if(hero.getGold() >= 2) {
            hero.addGold(-2);
            hero.addStrengthPoint(1);
            return true;
        }
        return false;
    }

}
