package com.mygdx.game.board;


import com.mygdx.game.character.Archer;
import com.mygdx.game.character.Hero;
import com.mygdx.game.character.Warrior;

import java.util.ArrayList;
import java.util.Arrays;


public class Board {

    private Region[] regions = new Region[85];

    private int[][] allHeroPaths = {
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

    private int[] allMonsterPaths = {
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


    public Board(ArrayList<Hero> heroes)
    {
        for(int i = 0; i < 85; i++)
        {
            if(i >= 73 && i <= 80) {
                regions[i] = null;
            }
            else  {
                Region r = new Region(i);
                r.setAvailableHeroPaths(this.allHeroPaths[i]);
                r.setAvailabeMonsterPath(this.allMonsterPaths[i]);
                regions[i] = r;
            }
        }

        for(Hero h: heroes)
        {
            int pos = h.getPosition();
            Region r = getRegion(pos);
            r.addHero(h);
        }

    }

    public Region getRegion(int position)
    {
        return this.regions[position];
    }

    @Override
    public String toString()
    {
        String str = "";
        for(Region r : this.regions)
        {
            if(r != null) {
                str += "Region: " + r.getPosition() + " \n\tHero Paths: "  + Arrays.toString(r.getAvailableHeroPaths()) +
                        "\n\tMonster path: " + r.getAvailableMonsterPath() + "\n";
                if(r.getHeroes().size() > 0)
                {
                    str += "\tThis region has a hero: ";
                    for(Hero h : r.getHeroes())
                    {
                        if (h.getTypeOfHero() == 1) {
                            str += " Archer of Player";
                        } else if (h.getTypeOfHero() == 2) {
                            str += " Dwarf of Player";
                        } else if (h.getTypeOfHero() == 3) {
                            str += " Warrior of Player";
                        } else if (h.getTypeOfHero() == 4) {
                            str += " Wizard of Player";
                        }
                        str += " " + h.getUsername() + " ";
                    }
                    str += "\n";
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

        Board b = new Board(h);

        System.out.println(b.toString());

        Region from = b.getRegion(a.getPosition());
        Region to = b.getRegion(0);
        a.moveTo(from, to);

        System.out.println(b.toString());
    }

}
