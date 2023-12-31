
package com.mygdx.game.board;

import com.mygdx.game.character.Hero;
import com.mygdx.game.etc.Farmer;
import com.mygdx.game.etc.Fog;
import com.mygdx.game.etc.Merchant;
import com.mygdx.game.etc.Well;
import com.mygdx.game.etc.Witch;
import com.mygdx.game.monster.Gor;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.monster.Skral;

import java.util.ArrayList;
import java.util.LinkedList;

public class Region {

    private Board board;

    private final int[][] character_coordinates = {
            // 0
            {131,176},{158,227},{212,242},{206,285},{91,264},{15,212},{268,207},
            // 7
            {116,81},{208,72},{164,19},{221,335},{244,163},{316,159},{383,214},
            // 14
            {267,315},{72,19},{482,314},{351,315},{295,394},{146,352},{75,337},
            // 21
            {10,324},{86,415},{105,479},{17,447},{17,538},{17,622},{59,603},
            // 28
            {371,461},{284,491},{241,573},{106,600},{542,320},{168,611},{201,500},{144,551},
            // 36
            {403,388},{262,675},{494,403},{595,508},{457,503},{364,583},{677,404},{715,475},
            // 44
            {678,354},{776,387},{714,321},{689,219},{550,188},{407,118},{522,109},{616,134},
            // 52
            {580,71},{665,169},{737,154},{700,61},{791,214},{813,127},{956,155},{919,92},
            // 60
            {993,77},{990,272},{1035,167},{868,224},{859,308},{879,368},{932,443},{926,487},
            // 68 - 72
            {915,527},{852,556},{849,598},{788,473},{216,423},
            // 73 - 79 "Empty
            {},{},{},{},{},{},{},
            //80
            {958,641},{827,625},{786,676},{1005,372},{733,609}
    };

    private final int[][] fog_coordinates = {
            // 8, 11, 12, 13, 16
            {}, {}, {}, {}, {}, {}, {}, {}, {248,92}, {}, {}, {294,143}, {356,142}, {423,190}, {}, {}, {505,264},
            {},{},{},{},{},{},{},{},{},{},{},{},{},{},{},
            // 32, 42, 44, 46, 47, 48, 49
            {565,290}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {641,392}, {}, {645,358}, {}, {661,303}, {657,249}, {604,218}, {501,170},
            // 56, 63, 64
            {}, {}, {}, {}, {}, {}, {762,242}, {}, {}, {}, {}, {}, {}, {942,248}, {793,291}
    };

    private final int[][] well_coordinates = {

    };

    private ArrayList<Hero> heroes;
    private int position;
    private int gold = 0;
    private int[] heroAvailablePaths;
    private int monsterAvailablePath;
    private Well well;
    private Fog fog;
    private ArrayList<Farmer> farmers;
    //    private Farmer farmer;
    private Monster monster;
    private int x;
    private int y;
    private int fogX;
    private int fogY;
    private Witch witch;

    // Legend 2 the starting positions of Gors: 8, 20, 21, 26, 48
    // Legend 2 the starting positions of Skral: 19
    public Region(Board board, int position)
    {
        this.board = board;
        this.position = position;
        this.heroes = new ArrayList<Hero>();
        this.farmers = new ArrayList<Farmer>();
//        this.farmers = new ArrayList<Farmer>();
        if(position == 8 || position == 20 || position == 21 || position == 26 || position == 48) {
            Gor gor = new Gor(position);
            this.monster = gor;
        } else if(position == 19) {
            Skral skral = new Skral(position);
            this.monster = skral;
        }
        if (position == 5 || position == 35 || position == 45 || position == 55) {
            Well well = new Well(position);
            this.well = well;
        }
        if (position == 8 || position == 11 || position == 12 || position == 13 || position == 16 ||
                position == 32 || position == 42 || position == 44 || position == 46 || position == 47
                || position == 48 || position == 49 || position == 56 || position == 63 || position == 64) {
            Fog fog = new Fog(position);
            this.fog = fog;
            this.fogX = fog_coordinates[position][0];
            this.fogY = fog_coordinates[position][1];
        }
        Witch witch=new Witch();
        if(position==witch.getPosition()){
            this.witch=witch;
        }

        this.x = character_coordinates[position][0];
        this.y = character_coordinates[position][1];
    }


    public ArrayList<Hero> getHeroes() { return this.heroes; }
    public int getPosition() { return this.position; }
    public int getGold() { return this.gold; }
    public int[] getAvailableHeroPaths() { return this.heroAvailablePaths; }
    public int getAvailableMonsterPath() { return this.monsterAvailablePath; }
    public Well getWell() { return this.well; }
    public Fog getFog() { return this.fog; }
    //    public Farmer getFarmer() { return this.farmer; }
    public ArrayList<Farmer> getFarmers() { return this.farmers; }
    public Monster getMonster() { return this.monster; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getFogX() { return this.fogX; }
    public int getFogY() { return this.fogY; }
    public Board getBoard() { return this.board; }
    public Witch getWitch() { return this.witch; }


    public void setAvailableHeroPaths(int[] regions)
    {
        this.heroAvailablePaths = regions;
    }

    public void setAvailabeMonsterPath(int nextPosition)
    {
        this.monsterAvailablePath = nextPosition;
    }

    public void removeHero(Hero hero)
    {
        this.heroes.remove(hero);
    }
    public void addHero(Hero hero)
    {
        this.heroes.add(hero);
    }

    public void removeMonster()
    {
        this.monster = null;
    }
    public void addMonster(Monster monster)
    {
        this.monster = monster;
    }

    public void removeFarmer(Farmer farmer)
    {
//        this.farmer = null;
        this.farmers.remove(farmer);
    }
    public void addFarmer(Farmer farmer)
    {
//        this.farmer = farmer;
        this.farmers.add(farmer);
    }

//    public void addFarmer(Farmer farmer)
//    {
//        if(this.farmer == null) this.farmers = new ArrayList<Farmer>();
//        this.farmers.add(farmer);
//    }

    public void addGold() {
        this.gold++;
    }

    public void removeGold() {
        this.gold--;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == this) return true;
        else
        {
            if(o instanceof Region)
            {
                Region pRegion = (Region) o;
                // To prevent nullpointer excpetion
                if((this.getMonster() == null && pRegion.getMonster() == null) && (this.getWitch() == null && this.getWitch() == null))
                {
                    return ( pRegion.getHeroes().equals(this.getHeroes()) && pRegion.getPosition() == this.getPosition() &&
                            pRegion.getGold() == this.getGold() && pRegion.getWell().equals(this.getWell()) &&
                            pRegion.getFog().equals(this.getFog()) && pRegion.getFarmers().equals(this.getFarmers()) &&
                            pRegion.getX() == this.getX() && pRegion.getY() == this.getY() && pRegion.getFogX() == this.getFogX() &&
                            pRegion.getFogY() == this.getFogY() );
                }
                else if(this.getMonster() == null && pRegion.getMonster() == null)
                {
                    return ( pRegion.getHeroes().equals(this.getHeroes()) && pRegion.getPosition() == this.getPosition() &&
                            pRegion.getGold() == this.getGold() && pRegion.getWell().equals(this.getWell()) &&
                            pRegion.getFog().equals(this.getFog()) && pRegion.getFarmers().equals(this.getFarmers()) &&
                            pRegion.getX() == this.getX() && pRegion.getY() == this.getY() && pRegion.getFogX() == this.getFogX() &&
                            pRegion.getFogY() == this.getFogY() && pRegion.getWitch().equals(this.getWitch()));
                }
                else if(this.getWitch() == null && pRegion.getWitch() == null)
                {
                    return ( pRegion.getHeroes().equals(this.getHeroes()) && pRegion.getPosition() == this.getPosition() &&
                            pRegion.getGold() == this.getGold() && pRegion.getWell().equals(this.getWell()) &&
                            pRegion.getFog().equals(this.getFog()) && pRegion.getFarmers().equals(this.getFarmers()) &&
                            pRegion.getX() == this.getX() && pRegion.getY() == this.getY() && pRegion.getFogX() == this.getFogX() &&
                            pRegion.getFogY() == this.getFogY() && this.getMonster().equals(pRegion.getWitch()));
                }
                else if(this.getWitch() == null || pRegion.getWitch() == null || pRegion.getMonster() == null || this.getMonster() == null)
                {
                    return false;
                }
                else
                {
                    return ( pRegion.getHeroes().equals(this.getHeroes()) && pRegion.getPosition() == this.getPosition() &&
                            pRegion.getGold() == this.getGold() && pRegion.getWell().equals(this.getWell()) &&
                            pRegion.getFog().equals(this.getFog()) && pRegion.getFarmers().equals(this.getFarmers()) &&
                            pRegion.getX() == this.getX() && pRegion.getY() == this.getY() && pRegion.getFogX() == this.getFogX() &&
                            pRegion.getFogY() == this.getFogY() && pRegion.getMonster().equals(this.getMonster()) &&
                            pRegion.getWitch().equals(this.getWitch()));
                }
            }
            else return false;
        }
//        private ArrayList<Hero> heroes; // completed
//        private int position;
//        private int gold = 0;
//        private Well well;// completed
//        private Fog fog; // Completed
//        private ArrayList<Farmer> farmers;
//        private Monster monster; // Completed
//        private int x;
//        private int y;
//        private int fogX;
//        private int fogY;
//        private Witch witch; // Completed

    }

}