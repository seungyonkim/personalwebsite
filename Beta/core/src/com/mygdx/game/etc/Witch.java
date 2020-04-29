package com.mygdx.game.etc;

import java.util.Random;

public class Witch {

    private int position;

    public Witch(){
        int[] witchRegions={8,11,12,13,16,32,42,44,46,47,48,49,56,63,64};
        int i=new Random().nextInt(witchRegions.length);
        this.position=witchRegions[i];
    }

    public int getPosition(){
        return this.position;
    }

    /*public static void main(String[] args){
        Witch tester = new Witch();
        int[] witchRegions={8,11,12,13,16,32,42,44,46,47,48,49,56,63,64};
        int x=new Random().nextInt(witchRegions.length);
        System.out.println(tester.getPosition());
        System.out.println(x);
    }*/

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        else
        {
            if( o instanceof Witch)
            {
                Witch pWitch = (Witch) o;
                return pWitch.getPosition() == this.getPosition();
            }
            else return false;
        }
    }
}
