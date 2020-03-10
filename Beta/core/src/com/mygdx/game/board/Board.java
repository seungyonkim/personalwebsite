package com.mygdx.game.board;


import com.mygdx.game.character.Archer;
import com.mygdx.game.character.Hero;
import com.mygdx.game.character.Warrior;
import com.mygdx.game.etc.Castle;
import com.mygdx.game.etc.Farmer;
import com.mygdx.game.etc.Merchant;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.monster.Skral;

import java.util.ArrayList;
import java.util.Arrays;


public class Board {

    private ArrayList<Region> regions = new ArrayList<Region>();

    private final int[][] allHeroPaths = {
            // 0
            {1,2,4,5,6,7,11}, {0,2,3,4}, {0,1,3,6,14}, {1,2,4,10,14,19,20}, {0,1,3,5,20,21}, {0,4,21},
            // 6
            {0,2,11,13,14,17}, {0,8,9,11,15}, {7,9,11}, {7,8,15}, {3,14,18,19}, {0,6,7,8,12,13}, {11,13},
            //13
            {6,11,12,16,17}, {2,3,6,10,17,18}, {7,9}, {13,17,32,36,38,48}, {6,13,14,16,18,36}, {10,14,17,19,28,36,72},
            // 19
            {3,10,18,20,22,23,72}, {3,4,19,21,22}, {4,5,20,22,24}, {19,20,21,23,24}, {19,22,24,25,31,34,35,72},
            // 24
            {21,22,23,25}, {23,24,26,27,31}, {25,27}, {25,26,31}, {18,29,36,38,72}, {28,30,34,72}, {29,33,34,35},
            // 31
            {23,25,27,33,35}, {16,38}, {30,31,35}, {23,29,30,35,72}, {23,30,31,33,34}, {16,17,18,28,38},
            // 37
            {41}, {16,28,32,36,39}, {38,40,42,43}, {39,41}, {37,40}, {39,43,44}, {39,42,44,45,71},
            // 44
            {42,43,45,46}, {43,44,46,64,65}, {44,45,47,64}, {46,48,53,54,56}, {16,47,49,50,51,53},
            // 49
            {48,50}, {48,49,51,52}, {48,50,52,53,55}, {50,51,55}, {47,48,51,54,55}, {47,53,55,56,57},
            // 55
            {51,52,53,54,57}, {47,54,57,63}, {54,55,56,58,59,63}, {57,59,60,61,62,63}, {57,58,60}, {58,59,62},
            // 61
            {58,62,63,64}, {58,60,61}, {56,57,58,61,64}, {45,46,61,63,65}, {45,64,66}, {65,67}, {66,68}, {67,69},
            // 69
            {68,70}, {69,81}, {43}, {18,19,23,28,29,34},
            // 73 - 79 : no regions exist
            {},{},{},{},{},{},{},
            // 80 for narrator
            {},
            // 81
            {70,82}, {81,84}, {},//Skip for 83
            // 84
            {82}
    };

    private final int[] allMonsterPaths = {
            // region 0
            -1, 0, 0, 1, 0, 0, 0, 0, 7, 7, 3, 0,
            // 12
            11, 6, 2, 7, 13, 6, 14, 3, 3,
            // 21
            4, 19, 19, 21, 24, 25, 25, 18, 28, 29,
            // 31
            23, 16, 30, 23, 23, 16, 41, 16, 38, 39,
            // 41
            40, 39, 39, 42, 43, 44, 46, 16, 48, 48,
            // 51
            48, 50, 47, 47, 51, 47, 54, 57, 57, 59,
            // 61
            58, 58, 56, 45, 45, 65, 66, 67, 68, 69,
            // 71
            43, 18,
            // 73 - 79 no regions, 80 for narrator
            -100,-100,-100,-100,-100,-100,-100,-100,
            //81
            70, 81, -100,// Skip for 83
            82
    };


    public Board(ArrayList<Hero> heroes, int difficulty) // difficulty : easy(-1), hard(1)
    {
        for(int i = 0; i < 85; i++)
        {
            if(i == 0) { // This is for castle
                Castle castle = new Castle(heroes.size(), 0);
                castle.setAvailabeMonsterPath(this.allMonsterPaths[i]);
                castle.setAvailableHeroPaths(this.allHeroPaths[i]);
                regions.add(castle);
            } else if(i >= 73 && i <= 79) { // Those regions don't exist
                regions.add(null);
            } else if(i == 18 || i == 57 || i == 71) { // These are for merchant
                Merchant merchant = new Merchant(i);
                merchant.setAvailableHeroPaths(this.allHeroPaths[i]);
                merchant.setAvailabeMonsterPath(this.allMonsterPaths[i]);
                regions.add(merchant);
            } else  {
                Region r = new Region(i);
                r.setAvailableHeroPaths(this.allHeroPaths[i]);
                r.setAvailabeMonsterPath(this.allMonsterPaths[i]);
                regions.add(r);
            }
        }

        for(Hero h: heroes) { // Set heroes on starting location
            int pos = h.getPosition();
            Region r = getRegion(pos);
            r.addHero(h);
        }


        if(difficulty == -1) {
            Farmer f = new Farmer(24);
            this.regions.get(24).addFarmer(f);
        } else {
            Farmer fOne = new Farmer(24);
            Farmer fTwo = new Farmer(36);
            this.regions.get(24).addFarmer(fOne);
            this.regions.get(36).addFarmer(fTwo);
        }



    }

    public Region getRegion(int position)
    {
        return this.regions.get(position);
    }

    public Region getMonsterAvailableRegion(Region r)
    {
        if(r.getPosition() == 0) return null; // there's no available path from castle. -->
            //Hope that the above code is a dead code.
        else {
            Region result = this.regions.get(r.getAvailableMonsterPath());
            if(result.getMonster() != null) {
                while(result.getMonster() != null) {
                    result = this.regions.get(result.getAvailableMonsterPath());
                }
            } if(result instanceof Castle) {
                this.getRegion(80).addMonster(r.getMonster());
            }
            return result;
        }
    }


    public ArrayList<Region> getHeroAvailablePaths(Region r)
    {
        ArrayList<Region> result = new ArrayList<Region>();
        for(int i: r.getAvailableHeroPaths()) result.add(getRegion(i));
        return result;
    }

    public ArrayList<Region> getMonsterRegions()
    {
        ArrayList<Region> result = new ArrayList<Region>();
        for(Region region: this.regions){
            if(region != null) {
                if(region.getMonster() != null) result.add(region);
            }
        }
        return result;
    }

    public ArrayList<Region> getGoldRegions() {
        ArrayList<Region> result = new ArrayList<Region>();
        for (Region region : this.regions) {
            if (region != null) {
                if (region.getGold() > 0) result.add(region);
            }
        }
        return result;
    }

    @Override
    public String toString()
    {
        String str = "";
        for(Region r : this.regions)
        {
            if(r != null) {
                str += "Region: " + r.getPosition() + " \n\tHero Paths: "  + Arrays.toString(r.getAvailableHeroPaths()) +
                        "\n\tMonster path: " + r.getAvailableMonsterPath() + "\n\t(x,y) = (" + r.getX() + "," + r.getY() + ") \n";
                if(r instanceof Castle) {
                    Castle c = (Castle)r;
                    str += "\tHere is a castle with " + c.getShied() + " shields\n";
                }
                if(r.getHeroes().size() > 0)
                {
                    str += "\tThis region has a hero: ";
                    for(Hero h : r.getHeroes())
                    {
                        str += " " + h.getUsername() + " ";
                    }
                    str += "\n";
                }
                if(r.getMonster() != null) {
                    str += "\tThis region has a monster: " + r.getMonster().getClass() + "\n";
                }
                str+= "\n";
            } else {
                str += "Region: Does Not Exist\n";
            }


        }

        return str;
    }

    // Test to create a Board object
    public static void main(String[] args)
    {
        Archer a = new Archer("Greg");
        Warrior w = new Warrior("Steven");
        ArrayList<Hero> h = new ArrayList<Hero>();
        h.add(a);
        h.add(w);

        Board b = new Board(h,-1);

        System.out.println(b.toString());
        System.out.println("------------------------------------------------------------------");

        Region r7 = b.getRegion(7);
        Monster add = new Skral(7);
        r7.addMonster(add);

        System.out.println(b.toString());
        System.out.println("------------------------------------------------------------------");
        Monster m = b.getRegion(8).getMonster();
        Region from = b.getRegion(8);
        Region to = b.getMonsterAvailableRegion(from);
        m.moveTo(from, to);

//        Region from = b.getRegion(a.getPosition());
//        Region to = b.getRegion(0);
//        a.moveTo(from, to);

//        System.out.println(b.toString());


//        System.out.println(region instanceof Castle);
        System.out.println(b.toString());




    }

}
