package com.mygdx.game.Equipment;


//Each side of the shield can be used once to either
// 1) prevent the loss of a hero's will power after a battle round
// 2) fend of a negative event card for the group of heros
//after the first use, must indicate that it is half used
//after the second use, shield goes back to the equipment board(merchant's store)

public class Shield {

    private String name;

    private boolean usedOnce;
    private boolean usedTwice;

    public Shield(String name){
        usedOnce=false;
        usedTwice=false;
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public boolean getUsedOnce(){
        return usedOnce;
    }
    public boolean getUsedTwice(){
        return usedTwice;
    }

    public void setUsedOnce(boolean bool){
        usedOnce=bool;
    }
}
