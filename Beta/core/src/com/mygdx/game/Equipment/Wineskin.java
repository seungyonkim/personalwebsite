package com.mygdx.game.Equipment;

//when a hero chooses 'move action', he can use each side of the wineskin token to move
//one space without advancing an hour.
//after its first use, must indicate that the wineskin is half full
//after the second use, it is returned back to the equipment board (merchant's store)

public class Wineskin {

    private String name;

    private boolean usedOnce;
    private boolean usedTwice;


    public Wineskin(String name){
        this.name=name;
        usedOnce=false;
        usedTwice=false;
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
