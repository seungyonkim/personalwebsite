package com.mygdx.game.etc;

import com.mygdx.game.board.Board;
import com.mygdx.game.board.Region;

import java.util.ArrayList;

//starts at space 72, right of space 23
//can be moved up to 4 spaces, e.g. prince can move up to 8 spaces at the cost of 2 hours
//Next heroes turn after move prince action
//cannot collect tokens or farmers
//accompanies heroes up to letter G on legend track
//Counts as 4 extra strength points for heroes in a battle when on the same space as a creature
public class Prince {
    private int position;
    private boolean alive; //can prince die? otherwise prince disappears when game reaches legend G
    private final int strengthPoint=4;
    private int movesMade=0; //counts amount of moves left for one hour
    private int hours = 0;

    public Prince(int position, int movesMade){
        this.position=72;
        this.alive=true;
    }

    public int getPosition() {
        return this.position;
    }

    public int getStrengthPoint() {
        return this.strengthPoint;
    }

    public boolean moveTo(Region from, Region to){
        return true;
    }

    public void incrementHours(){
        if(movesMade%4==0){
            hours++;
        }
    }

    public void resetHours(){
        this.hours=0;
    }

    public ArrayList<Region> getAvailableRegions(Board board){
        Region r = board.getRegion(this.position);
        return board.getHeroAvailablePaths(r);
    }

    public void battleLost() {
        alive = false; //remove prince from board
    }

}
