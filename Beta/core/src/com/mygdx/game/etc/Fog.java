package com.mygdx.game.etc;

public class Fog {
    // Need to add witch
    // possible positions: 8, 11, 12, 13, 16, 32, 42, 44, 46, 47, 48, 49, 56, 63, 64

    private int position;
    private boolean covered;

    public Fog(int position) {
        this.position = position;
        this.covered = true;
    }

    public boolean isCovered() {
        return this.covered;
    }

    public void unCover(){
        this.covered=false;
    }

    public boolean getCover() { return this.covered; }

    public int getPosition() { return this.position; }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        else
        {
            if (o instanceof Fog)
            {
                Fog pFog = (Fog) o;
                return (this.getPosition() == pFog.getPosition() && this.isCovered() == pFog.isCovered());
            }
            else return false;
        }
    }
}
