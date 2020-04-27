package com.mygdx.game.character;

import com.mygdx.game.board.Board;
import com.mygdx.game.board.Region;
import com.mygdx.game.etc.Farmer;
import com.mygdx.game.etc.Item;
import com.mygdx.game.etc.Well;
import com.mygdx.game.monster.Monster;

import java.util.ArrayList;
import java.util.Random;

public class Hero {

    private static final long serialVersionUID = 4L; // DO NOT CHANGE AND DELETE


    private int position;
    private boolean moved;
    private int gold;
    private int willPower;
    private int strengthPoint;
    private int rank;
    private String username;
    private ArrayList<Item> items;
    private int hours = 0;
    private boolean canPlay = true;
    //    private Farmer farmer;
    private ArrayList<Farmer> farmers;

    public Hero(int position, int gold, int wp, int sp, int rank, String name)
    {
        this.position = position;
        this.gold = gold;
        this.willPower = wp;
        this.strengthPoint = sp;
        this.rank = rank;
        this.username = name;
        this.items = new ArrayList<Item>();
        this.farmers = new ArrayList<Farmer>();
    }

//    public int diceRoll()
//    {
//        // Based : 1 dice
//        return (int)(Math.random() * 6) + 1;
//
//    }

    public int getWillPower()
    {
        return this.willPower;
    }

    public int getPosition()
    {
        return this.position;
    }

    public int getStrengthPoint()
    {
        return this.strengthPoint;
    }

    public int getGold()
    {
        return this.gold;
    }

    public int getRank() { return this.rank; }
    public String getUsername() { return this.username; }

    public boolean getCanPlay() {
        return this.canPlay;
    }

    public void enablePlay() {
        this.canPlay = true;
    }

    public void disablePlay() {
        this.canPlay = false;
    }


    public ArrayList<Item> getItems()
    {
        return this.items;
    }

    public int getHours()
    {
        return this.hours;
    }

    //    public Farmer getFarmer() {
//        return this.farmer;
//    }
    public ArrayList<Farmer> getFarmers() {
        return this.farmers;
    }

    public void addGold(int gold)
    {
        this.gold += gold;
    }

    public void addWillPower(int wp)
    {
        this.willPower += wp;
        if(this.willPower > 20) this.willPower = 20;
    }

    public void addStrengthPoint(int sp)
    {
        this.strengthPoint += sp;
        if(this.strengthPoint > 14) this.strengthPoint = 14;
    }
    public String getTypeOfHeroString() {
        if(this instanceof Archer) return "Archer";
        if(this instanceof Dwarf) return "Dwarf";
        if(this instanceof Warrior) return "Warrior";
        if(this instanceof Wizard) return "Wizard";
        else return "";
    }

    public boolean hasMoved(){return moved;}
    public void setMoved(){moved = true;}
    public void restoreMoved(){moved = false;}
    public boolean moveTo(Region from, Region to) {
        if(canPlay)
        {
            int[] available = from.getAvailableHeroPaths();
            boolean result = false;
            for(int a : available) {
                if(a == to.getPosition()) {
                    result = true;
                    break;
                }
            }
            if(result) {
                if (this.farmers.size() == 1) {
                    if (to.getMonster() != null) {
                        int farmerNum = this.farmers.get(0).getFarmerNumber();
                        this.farmers.get(0).die();
                        this.farmers.remove(this.farmers.get(0));
//                        System.out.println("Farmer "+farmers.get(0).getFarmerNumber()+" died because he encountered a monster.");
                        System.out.println("Farmer "+farmerNum+" died because he encountered a monster.");
                    } else if (to.getPosition() == 0) {
                        this.farmers.get(0).die();
                        this.farmers.remove(this.farmers.get(0));
                        to.getBoard().getCastle().farmerArrive();
                    } else {
                        this.farmers.get(0).setPosition(to.getPosition());
                    }
                } else if (this.farmers.size() == 2) {
                    if (to.getMonster() != null) {
                        this.farmers.get(1).die();
                        this.farmers.remove(this.farmers.get(1));
                        this.farmers.get(0).die();
                        this.farmers.remove(this.farmers.get(0));
                        System.out.println("Both farmers died because they encountered a monster.");
                    } else if (to.getPosition() == 0) {
                        this.farmers.get(1).die();
                        this.farmers.remove(this.farmers.get(1));
                        to.getBoard().getCastle().farmerArrive();
                        this.farmers.get(0).die();
                        this.farmers.remove(this.farmers.get(0));
                        to.getBoard().getCastle().farmerArrive();
                    } else {
                        this.farmers.get(0).setPosition(to.getPosition());
                        this.farmers.get(1).setPosition(to.getPosition());
                    }
                }
                from.removeHero(this);
                to.addHero(this);
                this.position = to.getPosition();
                incrementHours();
            }
            return result;
        }
        return false;
    }

    public void incrementHours()
    {
        if (hours < 7) {
            this.hours++;
        }
        else if(7 <= hours && hours < 10 && willPower >= 2)
        {
            this.hours++;
            this.willPower -= 2;
            if (hours == 10) {
                this.canPlay = false;
            }
        }
//        else if(hours == 10) this.canPlay = false;
        else this.canPlay = false;

        if (this.hours >= 7 && this.willPower <= 2) {
            this.canPlay = false;
        }
    }

    public void resetHours() {
        this.hours = 0;
    }

    public void pass()
    {
        if(canPlay)
        {
            incrementHours();
        }
    }

    /*
    1 = archer
    2 = dwarf
    3 = warrior
    4 = wizard
     */
    public int getTypeOfHero() {
        if(this instanceof Archer) return 1;
        if(this instanceof Dwarf) return 2;
        if(this instanceof Warrior) return 3;
        if(this instanceof Wizard) return 4;
        else return -1;
    }

    public ArrayList<Region> getAvailableRegions(Board board)
    {
        Region r = board.getRegion(this.position);
        return board.getHeroAvailablePaths(r);
    }


    public void dropGold() {
        this.gold--;
    }

    public void pickUpGold() {
        this.gold++;
    }

    public void drinkWell(Well well) {
        if (this instanceof Warrior) {
            this.willPower += 5;
        } else {
            this.willPower += 3;
        }
        well.empty();
    }

//    public void pickupFarmer(Farmer farmer)
//    {
//        this.farmer = farmer;
//    }
//
//    public void dropOffFarmer() {
//        this.farmer = null;
//    }

    public void pickupFarmer(Farmer farmer, Region region)
    {
        this.farmers.add(farmer);
        region.removeFarmer(farmer);
    }

    public void dropOffFarmer(Farmer farmer, Region region) {
        this.farmers.remove(farmer);
        region.addFarmer(farmer);
    }

    public int battle(int heroBattleValue, int monsterBattleValue, Monster monster) {
        // implementation of hero's turn of the battle
        int result;
        if (heroBattleValue > monsterBattleValue) {
            monster.deductWP(heroBattleValue-monsterBattleValue);
            result = heroBattleValue - monsterBattleValue;
        } else if (heroBattleValue < monsterBattleValue) {
            this.willPower -= (monsterBattleValue - heroBattleValue);
            if (this.willPower < 0) {
                this.willPower = 0;
            }
            result = heroBattleValue - monsterBattleValue;
        } else {
            result = 0;
        }
        this.incrementHours();
        return result;
    }

    public void battleLost() {
        if (this.strengthPoint > 1) {
            this.strengthPoint -= 1;
        }
        this.willPower += 3;
    }

    public int rollDice() {
        // hero rolls dice and gets the value
        int numOfDice = 0;
        int highestValue = 0;
        Random r = new Random();

        if (this instanceof Warrior) {
            numOfDice = ((Warrior) this).getNumOfDice();
        } else if (this instanceof Dwarf) {
            numOfDice = ((Dwarf) this).getNumOfDice();
        } else if (this instanceof Wizard) {
            numOfDice = 1;
        }

        for (int i = 0; i < numOfDice; i++) {
            int roll = r.nextInt(6)+1;
            if (roll > highestValue) {
                highestValue = roll;
            }
        }

        return highestValue;
    }

}