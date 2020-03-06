package com.mygdx.game.etc;


public class Farmer {

    private int position;
    private boolean isHome = false;

    public Farmer(int position)
    {
        this.position = position;
    }

    public int getPosition()
    {
        return position;
    }

    public void dropped(int position)
    {
        this.position = position;

        if(this.position == 0)
            this.isHome = true;


    }


}
