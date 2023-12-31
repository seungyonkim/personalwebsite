package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Region;
import com.mygdx.game.character.Archer;
import com.mygdx.game.character.Dwarf;
import com.mygdx.game.character.Hero;
import com.mygdx.game.character.Warrior;
import com.mygdx.game.character.Wizard;
import com.mygdx.game.etc.Farmer;
import com.mygdx.game.etc.Merchant;
import com.mygdx.game.monster.Gor;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.monster.Skral;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {

    private Andor parent;
    private Stage stage;

    private OrthographicCamera camera;

    private ArrayList<Hero> playerHeroes;
    private Board gameBoard;

    private Texture warriorTexture;
    private Texture archerTexture;
    private Texture wizardTexture;
    private Texture dwarfTexture;

    private Rectangle player;

    private Rectangle warrior;
    private Rectangle archer;
    private Rectangle wizard;
    private Rectangle dwarf;


    private Texture farmerTexture;
    private Texture coveredFogTexture;
    private Texture wellTexture;
    private Texture gorTexture;
    private Texture skralTexture;

    private Rectangle farmer1;
    private Rectangle farmer2;
    private Rectangle coveredFog;
    private Rectangle well;
    //    private Rectangle gor;
    private Rectangle skral;
    private ArrayList<Rectangle> gors;
//    private ArrayList<Rectangle> farmers;

    private Texture pathTexture;
    private Image pathButtonImage;
    private ArrayList<Region> availableRegions;
    private ArrayList<TextButton> pathButtons;
    //    private TextButton timeMarker;
    private TextButton heroInformation;
    private TextButton goldInformation;
    private TextButton dropGoldButton;
    private TextButton pickUpGoldButton;
    private TextButton dropFarmer;
    private TextButton pickUpFarmer;
    private TextButton drinkWell;
    private TextButton merchantButton;
    private TextButton battleButton;
    private TextButton castleShields;

    private TextButton equipmentBagButton;
//    private BitmapFont font;

    private boolean skipping;
    private boolean canBattle;
    private boolean hasToStop;

    private Texture warriorPortraitTexture;
    private Image warriorPortraitImage;
    private Texture archerPortraitTexture;
    private Image archerPortraitImage;
    private Texture wizardPortraitTexture;
    private Image wizardPortraitImage;
    private Texture dwarfPortraitTexture;
    private Image dwarfPortraitImage;

    private int archerDiceValue = 0;
    private int wizardDiceValue = 0;

    public GameScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());

        gameBoard = parent.getGameBoard();

        playerHeroes = parent.getPlayerHeroes();

        pathButtons = new ArrayList<TextButton>();

        skipping = true;
        canBattle = true;
        hasToStop = false;

        warriorTexture = new Texture(Gdx.files.internal("characters/andor_warrior_M.png"));
        archerTexture = new Texture(Gdx.files.internal("characters/andor_archer_M.png"));
        wizardTexture = new Texture(Gdx.files.internal("characters/andor_wizard_M.png"));
        dwarfTexture = new Texture(Gdx.files.internal("characters/andor_dwarf_M.png"));

        warriorPortraitTexture = new Texture(Gdx.files.internal("characters/warrior_male_portrait.png"));
        warriorPortraitImage = new Image(warriorPortraitTexture);
        archerPortraitTexture = new Texture(Gdx.files.internal("characters/archer_male_portrait.png"));
        archerPortraitImage = new Image(archerPortraitTexture);
        wizardPortraitTexture = new Texture(Gdx.files.internal("characters/wizard_male_portrait.png"));
        wizardPortraitImage = new Image(wizardPortraitTexture);
        dwarfPortraitTexture = new Texture(Gdx.files.internal("characters/dwarf_male_portrait.png"));
        dwarfPortraitImage = new Image(dwarfPortraitTexture);

        farmerTexture = new Texture(Gdx.files.internal("andor_farmer_M.png"));
        coveredFogTexture = new Texture(Gdx.files.internal("andor_covered_fog.png"));
        wellTexture = new Texture(Gdx.files.internal("andor_well.png"));
        gorTexture = new Texture(Gdx.files.internal("monsters/andor_gor.png"));
        skralTexture = new Texture(Gdx.files.internal("monsters/andor_skral.png"));



        coveredFog = new Rectangle();
        coveredFog.width = 300;
        coveredFog.height = 300;

        well = new Rectangle();
        well.width = 300;
        well.height = 300;


        skral = new Rectangle();
        skral.width = 300;
        skral.height = 400;

//        gor = new Rectangle();
//        gor.width = 300;
//        gor.height = 400;
        gors = new ArrayList<Rectangle>();
//        farmers = new ArrayList<Rectangle>();


//        player = new Rectangle();
//        player.x = parent.andorBoard.getWidth()/2 - 300/2;
//        player.y = parent.andorBoard.getHeight()/2 - 500/2;
//        player.width = 300;
//        player.height = 500;

        for (int i = 0; i < playerHeroes.size(); i++) {
            if (playerHeroes.get(i).getTypeOfHero() == 1) {
                int x = gameBoard.getRegion(playerHeroes.get(i).getPosition()).getX();
                int y = gameBoard.getRegion(playerHeroes.get(i).getPosition()).getY();
                archer = new Rectangle();
                archer.width = 300;
                archer.height = 500;
                archer.x = calcX(x) - archer.width/2;
                archer.y = calcY(y) - archer.height/2;
            }
            if (playerHeroes.get(i).getTypeOfHero() == 2) {
                int x = gameBoard.getRegion(playerHeroes.get(i).getPosition()).getX();
                int y = gameBoard.getRegion(playerHeroes.get(i).getPosition()).getY();
                dwarf = new Rectangle();
                dwarf.width = 300;
                dwarf.height = 500;
                dwarf.x = calcX(x) - dwarf.width/2;
                dwarf.y = calcY(y) - dwarf.height/2;
            }
            if (playerHeroes.get(i).getTypeOfHero() == 3) {
                int x = gameBoard.getRegion(playerHeroes.get(i).getPosition()).getX();
                int y = gameBoard.getRegion(playerHeroes.get(i).getPosition()).getY();
                warrior = new Rectangle();
                warrior.width = 300;
                warrior.height = 500;
                warrior.x = calcX(x) - warrior.width/2;
                warrior.y = calcY(y) - warrior.height/2;
            }
            if (playerHeroes.get(i).getTypeOfHero() == 4) {
                int x = gameBoard.getRegion(playerHeroes.get(i).getPosition()).getX();
                int y = gameBoard.getRegion(playerHeroes.get(i).getPosition()).getY();
                wizard = new Rectangle();
                wizard.width = 300;
                wizard.height = 500;
                wizard.x = calcX(x) - wizard.width/2;
                wizard.y = calcY(y) - wizard.height/2;
            }
        }

        ArrayList<Region> skralRegions = gameBoard.getMonsterRegions();
        for (Region region : skralRegions) {
            if (region.getMonster() instanceof Skral) {
                int x = region.getX();
                int y = region.getY();
                skral.x = calcX(x) - skral.width/2;
                skral.y = calcY(y) - skral.height/2;
            }
        }

        ArrayList<Region> monsterRegions = gameBoard.getMonsterRegions();
        for (Region region : monsterRegions) {
            if (region.getMonster() instanceof Gor) {
                int x = region.getX();
                int y = region.getY();
                Rectangle gor = new Rectangle();
                gor.width = 300;
                gor.height = 400;
                gor.x = calcX(x) - gor.width/2;
                gor.y = calcY(y) - gor.height/2;
                gors.add(gor);
            }
        }

//        ArrayList<Region> farmerRegions = gameBoard.getFarmerRegions();
        ArrayList<Farmer> farmers = gameBoard.getFarmers();

        int x = gameBoard.getRegion(farmers.get(0).getPosition()).getX();
        int y = gameBoard.getRegion(farmers.get(0).getPosition()).getY();
        farmer1 = new Rectangle();
        farmer1.width = 300;
        farmer1.height = 500;
        farmer1.x = calcX(x) - farmer1.width / 2;
        farmer1.y = calcY(y) - farmer1.height / 2;

        if (parent.getDifficulty() == 1) {
            x = gameBoard.getRegion(farmers.get(1).getPosition()).getX();
            y = gameBoard.getRegion(farmers.get(1).getPosition()).getY();
            farmer2 = new Rectangle();
            farmer2.width = 300;
            farmer2.height = 500;
            farmer2.x = calcX(x) - farmer2.width / 2;
            farmer2.y = calcY(y) - farmer2.height / 2;
        }


        // create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, parent.andorBoard.getWidth(), parent.andorBoard.getHeight());

    }

    public float calcX(int x) {
        return parent.andorBoard.getWidth()*x/1115;
    }

    public float calcY(int y) {
        return parent.andorBoard.getHeight()*(705-y)/705;
    }

    public void updateHeroPosition(Hero hero, Rectangle rectangle) {
        int x = gameBoard.getRegion(hero.getPosition()).getX();
        int y = gameBoard.getRegion(hero.getPosition()).getY();
        rectangle.x = calcX(x) - rectangle.width/2;
        rectangle.y = calcY(y) - rectangle.height/2;
    }

    public void updateFarmerPosition(ArrayList<Farmer> farmers) {
//        int x = gameBoard.getRegion(gameBoard.getFarmerRegions().get(0).getFarmer().getPosition()).getX();
//        int y = gameBoard.getRegion(gameBoard.getFarmerRegions().get(0).getFarmer().getPosition()).getY();

//        int x = gameBoard.getRegion(gameBoard.getFarmers().get(0).getPosition()).getX();
//        int y = gameBoard.getRegion(gameBoard.getFarmers().get(0).getPosition()).getY();
//        farmer1.x = calcX(x) - farmer1.width/2;
//        farmer1.y = calcY(y) - farmer1.height/2;
//        if (parent.getDifficulty() == 1) {
//            x = gameBoard.getRegion(gameBoard.getFarmers().get(1).getPosition()).getX();
//            y = gameBoard.getRegion(gameBoard.getFarmers().get(1).getPosition()).getY();
//            farmer2.x = calcX(x) - farmer2.width/2;
//            farmer2.y = calcY(y) - farmer2.height/2;
//        }

        for (Farmer farmer : farmers) {
            if (farmer.getFarmerNumber() == 1) {
                int x = gameBoard.getRegion(farmer.getPosition()).getX();
                int y = gameBoard.getRegion(farmer.getPosition()).getY();
                farmer1.x = calcX(x) - farmer1.width/2;
                farmer1.y = calcY(y) - farmer1.height/2;
            } else if (farmer.getFarmerNumber() == 2) {
                int x = gameBoard.getRegion(farmer.getPosition()).getX();
                int y = gameBoard.getRegion(farmer.getPosition()).getY();
                farmer2.x = calcX(x) - farmer2.width/2;
                farmer2.y = calcY(y) - farmer2.height/2;
            }
        }
    }

    public void updateMonsterPositions() {
//        System.out.println("Updating monsters");
//        for (Region monsterRegion : gameBoard.getMonsterRegions()) {
//            System.out.println(monsterRegion.getMonster().getPosition());
//        }

        ArrayList<Region> skralRegions = gameBoard.getMonsterRegions();
        for (Region region : skralRegions) {
            if (region.getMonster() instanceof Skral) {
                int x = region.getX();
                int y = region.getY();
                skral.x = calcX(x) - skral.width/2;
                skral.y = calcY(y) - skral.height/2;
            }
        }

        gors.clear();
        ArrayList<Region> monsterRegions = gameBoard.getMonsterRegions();
        for (Region region : monsterRegions) {
            if (region.getMonster() instanceof Gor) {
                int x = region.getX();
                int y = region.getY();
                Rectangle gor = new Rectangle();
                gor.width = 300;
                gor.height = 400;
                gor.x = calcX(x) - gor.width/2;
                gor.y = calcY(y) - gor.height/2;
                gors.add(gor);
            }
        }
    }

    public void battleDialog(Hero p, final Monster monster, int r, int l) {
        System.out.println("Battle started.");
//        final Gor gor = (Gor) monster;
        final int lastResult = l;
        if (p.getCanPlay()) {
            // if the player can play on (has enough hours left or wp to use for overtime)
            final int round = r;
            final Hero player = p;
            new Dialog("Battle vs. Monster / Round " + round, parent.skin) {
                {
                    if (round == 1) {
                        text("Monster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + player.getStrengthPoint() + "\nWillpower: " + player.getWillPower() + "\nHours used: " + player.getHours() + "\n");
                    } else {
                        if (lastResult > 0) {
                            text("You have won the last round.\nMonster lost "+lastResult+" willpower.\nMonster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + player.getStrengthPoint() + "\nWillpower: " + player.getWillPower() + "\nHours used: " + player.getHours() + "\n");
                        } else if (lastResult < 0) {
                            text("You have lost the last round.\nYou lost "+Math.abs(lastResult)+" willpower.\nMonster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + player.getStrengthPoint() + "\nWillpower: " + player.getWillPower() + "\nHours used: " + player.getHours() + "\n");
                        } else {
                            text("Last round ended in a Draw.\nMonster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + player.getStrengthPoint() + "\nWillpower: " + player.getWillPower() + "\nHours used: " + player.getHours() + "\n");
                        }
                    }
                    button("Roll Dice", true);
                    button("Leave Battle", false);
                }

                @Override
                protected void result(Object object) {
                    if (object.equals(true)) {
                        // if player chooses to roll the dice
                        final int heroBattleValue = player.rollDice() + player.getStrengthPoint();
                        new Dialog("Battle vs. Monster / Round " + round, parent.skin) {
                            // monster's turn
                            {
                                text("Monster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strengh: " + player.getStrengthPoint() + "\nWillpower: " + player.getWillPower() + "\nHours used: " + player.getHours() + "\n\nYou just got a battle value of: "+heroBattleValue);

                                button("Roll Monster's Dice", true);
                                button("Leave Battle", false);
                            }

                            @Override
                            protected void result(Object object) {
                                if (object.equals(true)) {
                                    // if player chooses to roll gor's dice
                                    int gorBattleValue = monster.getStrengthPoint() + monster.rollDice();
                                    int newResult = player.battle(heroBattleValue, gorBattleValue, monster);
                                    if (player.getWillPower() == 0) {
                                        playerLose(player, monster);
                                    } else if (monster.getWillPower() == 0) {
                                        playerWin(monster);
                                    } else {
                                        battleDialog(player, monster, round + 1, newResult);
                                    }
                                } else {
                                    // if player decides to leave battle
                                    leaveBattle(monster);
                                }
                            }
                        }.show(stage);
                    } else {
                        // if player decides to leave battle
                        leaveBattle(monster);
                    }
                }
            }.show(stage);
        } else {
            // if the player cannot play on because he has neither any hours left nor willpower to use for the overtime
            new Dialog("Battle vs. Monster / Over", parent.skin) {
                {
                    text("You cannot continue the battle because you don't have any hours left in the day.\nTry again next day.");

                    button("Okay", false);
                }

                @Override
                protected void result(Object object) {
                    leaveBattle(monster);
                }
            }.show(stage);
        }
    }

    public void archerBattleDialogue(Archer a, final Monster monster, int r, int n, int lastRoll, int l) {
        System.out.println("Battle started.");
//        final Gor gor = (Gor) monster;
        final int lastResult = l;
        if (a.getCanPlay() && a.getWillPower() > 0) {
            // if the player can play on
            final int round = r;
            final Archer archer = a;
            final int numOfDice = n;
            final int last = lastRoll;
            new Dialog("Battle vs. Monster / Round " + round, parent.skin) {
                {
                    if (last == 0) {
                        if (round == 1) {
                            text("Monster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + archer.getStrengthPoint() + "\nWillpower: " + archer.getWillPower() + "\nHours used: " + archer.getHours() + "\nRemaining Dice: " + numOfDice + "\n");
                        } else {
                            if (lastResult > 0) {
                                text("You have won the last round.\nMonster lost " + lastResult + " willpower.\nMonster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + archer.getStrengthPoint() + "\nWillpower: " + archer.getWillPower() + "\nHours used: " + archer.getHours() + "\nRemaining Dice: " + numOfDice + "\n");
                            } else if (lastResult < 0) {
                                text("You have lost the last round.\nYou lost " + Math.abs(lastResult) + " willpower.\nMonster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + archer.getStrengthPoint() + "\nWillpower: " + archer.getWillPower() + "\nHours used: " + archer.getHours() + "\nRemaining Dice: " + numOfDice + "\n");
                            } else {
                                text("Last round ended in a Draw.\nMonster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + archer.getStrengthPoint() + "\nWillpower: " + archer.getWillPower() + "\nHours used: " + archer.getHours() + "\nRemaining Dice: " + numOfDice + "\n");
                            }
                        }
                    } else {
                        text("Monster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + archer.getStrengthPoint() + "\nWillpower: " + archer.getWillPower() + "\nHours used: " + archer.getHours() + "\nYour Last Roll: " + last + "\nRemaining Dice: " + numOfDice + "\n");
                    }
                    if (last != 0) {
                        button("Stop Rolling", "1");
                    }
                    if (numOfDice > 0) {
                        button("Roll Dice", "2");
                    }
                    button("Leave Battle", "3");
                }

                @Override
                protected void result(Object object) {
                    if (object.equals("1")) {
                        // Stop rolling and use last roll
                        final int heroBattleValue = last + archer.getStrengthPoint();
                        new Dialog("Battle vs. Monster / Round " + round, parent.skin) {
                            // monster's turn
                            {
                                text("Monster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strengh: " + archer.getStrengthPoint() + "\nWillpower: " + archer.getWillPower() + "\nHours used: " + archer.getHours() + "\n\nYou just got a battle value of: "+heroBattleValue);

                                button("Roll Monster's Dice", true);
                                button("Leave Battle", false);
                            }

                            @Override
                            protected void result(Object object) {
                                if (object.equals(true)) {
                                    // if player chooses to roll Monster's dice
                                    int gorBattleValue = monster.getStrengthPoint() + monster.rollDice();
                                    int newResult = archer.battle(heroBattleValue, gorBattleValue, monster);
                                    int newNumOfDice = archer.getNumOfDice();
                                    if (archer.getWillPower() == 0) {
                                        playerLose(archer, monster);
                                    } else if (monster.getWillPower() == 0) {
                                        playerWin(monster);
                                    } else {
                                        archerBattleDialogue(archer, monster, round + 1, newNumOfDice, 0, newResult);
                                    }
                                } else {
                                    // if player decides to leave battle
                                    leaveBattle(monster);
                                }
                            }
                        }.show(stage);
                    } else if (object.equals("2")) {
                        // Roll another dice
                        Random r = new Random();
                        int newRoll = r.nextInt(6) + 1;
                        archerBattleDialogue(archer, monster, round, numOfDice-1, newRoll, lastResult);
                    } else {
                        // if player decides to leave battle
                        leaveBattle(monster);
                    }
                }
            }.show(stage);
        } else {
            // if the player cannot play on because he has neither any hours left nor willpower to use for the overtime
            new Dialog("Battle vs. Monster / Over", parent.skin) {
                {
                    text("You cannot continue the battle because you don't have any hours left in the day.\nTry again next day.");

                    button("Okay", false);
                }

                @Override
                protected void result(Object object) {
                    leaveBattle(monster);
                }
            }.show(stage);
        }
    }



    public void wizardBattleDialogue(Wizard w, final Monster monster, int r, int l) {
        // wizard's dice roll, since wizard has a special way to decide dice value
        System.out.println("Battle started.");
//        final Gor gor = (Gor) monster;
        final int lastResult = l;
        if (w.getCanPlay() && w.getWillPower() > 0) {
            // if the player can play on
            final int round = r;
            final Wizard wizard = w;
            new Dialog("Battle vs. Monster / Round " + round, parent.skin) {
                {
                    if (round == 1) {
                        text("Monster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + wizard.getStrengthPoint() + "\nWillpower: " + wizard.getWillPower() + "\nHours used: " + wizard.getHours() + "\n");
                    } else {
                        if (lastResult > 0) {
                            text("You have won the last round.\nMonster lost " + lastResult + " willpower.\nMonster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + wizard.getStrengthPoint() + "\nWillpower: " + wizard.getWillPower() + "\nHours used: " + wizard.getHours() + "\n");
                        } else if (lastResult < 0) {
                            text("You have lost the last round.\nYou lost " + Math.abs(lastResult) + " willpower.\nMonster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + wizard.getStrengthPoint() + "\nWillpower: " + wizard.getWillPower() + "\nHours used: " + wizard.getHours() + "\n");
                        } else {
                            text("Last round ended in a Draw.\nMonster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + wizard.getStrengthPoint() + "\nWillpower: " + wizard.getWillPower() + "\nHours used: " + wizard.getHours() + "\n");
                        }
                    }


                    button("Roll Dice", true);
                    button("Leave Battle", false);
                }

                @Override
                protected void result(Object object) {
                    if (object.equals(true)) {
                        // Roll dice
                        Random r = new Random();
                        final int newRoll = r.nextInt(6) + 1;
                        new Dialog("Battle vs. Monster / Round " + round, parent.skin) {
                            {
                                text("Monster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strength: " + wizard.getStrengthPoint() + "\nWillpower: " + wizard.getWillPower() + "\nHours used: " + wizard.getHours() + "\nDice Rolled: " + newRoll + "\n");
                                button("Flip Dice", "1");
                                button("Don't Flip", "2");
                                button("Leave Battle", "3");
                            }

                            @Override
                            protected void result(Object object) {
                                if (object.equals("3")) {
                                    leaveBattle(monster);
                                } else {
                                    int bv;
                                    if (object.equals("1")) {
                                        bv = (7 - newRoll) + wizard.getStrengthPoint();
                                    } else {
                                        bv = newRoll + wizard.getStrengthPoint();
                                    }
                                    final int heroBattleValue = bv;
                                    new Dialog("Battle vs. Monster / Round " + round, parent.skin) {
                                        // monster's turn
                                        {
                                            text("Monster's current attributes:\nStrength: " + monster.getStrengthPoint() + "\nWillpower: " + monster.getWillPower() + "\n\n\nYour Strengh: " + wizard.getStrengthPoint() + "\nWillpower: " + wizard.getWillPower() + "\nHours used: " + wizard.getHours() + "\n\nYou just got a battle value of: "+heroBattleValue);

                                            button("Roll Monster's Dice", true);
                                            button("Leave Battle", false);
                                        }

                                        @Override
                                        protected void result(Object object) {
                                            if (object.equals(true)) {
                                                // if player chooses to roll Monster's dice
                                                int gorBattleValue = monster.getStrengthPoint() + monster.rollDice();
                                                int newResult = wizard.battle(heroBattleValue, gorBattleValue, monster);
                                                if (wizard.getWillPower() == 0) {
                                                    playerLose(wizard, monster);
                                                } else if (monster.getWillPower() == 0) {
                                                    playerWin(monster);
                                                } else {
                                                    wizardBattleDialogue(wizard, monster, round + 1, newResult);
                                                }
                                            } else {
                                                // if player decides to leave battle
                                                leaveBattle(monster);
                                            }
                                        }
                                    }.show(stage);
                                }
                            }
                        }.show(stage);


                    } else {
                        // if player decides to leave battle
                        leaveBattle(monster);
                    }
                }
            }.show(stage);
        } else {
            // if the player cannot play on because he has neither any hours left nor willpower to use for the overtime
            new Dialog("Battle vs. Monster / Over", parent.skin) {
                {
                    text("You cannot continue the battle because you don't have any hours left in the day.\nTry again next day.");

                    button("Okay", false);
                }

                @Override
                protected void result(Object object) {
                    leaveBattle(monster);
                }
            }.show(stage);
        }
    }

    public void leaveBattle(Monster monster) {
        if (monster instanceof Gor) {
            ((Gor) monster).resetGor();
        } else if (monster instanceof Skral) {
            ((Skral) monster).resetSkral();
        }
        show();
    }

    public void playerWin(final Monster monster) {
        new Dialog("Battle vs. Monster / Over", parent.skin) {
            {
                text("Monster has no more willpower left. You have won the battle.");

                button("Okay", false);
            }

            @Override
            protected void result(Object object) {
                gameBoard.getRegion(monster.getPosition()).removeMonster();
                monster.setPosition(80);
                updateMonsterPositions();
                leaveBattle(monster);
            }
        }.show(stage);
    }

    public void playerLose(Hero hero, final Monster monster) {
        hero.battleLost();

        new Dialog("Battle vs. Monster / Over", parent.skin) {
            {
                text("You have no more willpower left. You have lost the battle.");

                button("Okay", false);
            }

            @Override
            protected void result(Object object) {
                leaveBattle(monster);
            }
        }.show(stage);
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        if (parent.getFinishedHeroes().size() == parent.getPlayerHeroes().size()) {
            // all the players have finished the day, so execute endDay
            parent.endDay();
            updateMonsterPositions();
            show();
        }

        if (!parent.whoseTurn().getCanPlay()) {
            parent.finishDay();
            show();
        }

        if (gameBoard.getCastle().getShield() < 0) {
            new Dialog("Game Over", parent.skin) {
                {
                    text("Game Over. You Lost.");
                    button("Exit Game", true);
                }

                @Override
                protected void result(Object object) {
                    if (object.equals(true)) {
                        Gdx.app.exit();
                    }
                }
            }.show(stage);
        }

        availableRegions = parent.whoseTurn().getAvailableRegions(gameBoard);
//        System.out.println("Current Hero position: "+parent.whoseTurn().getPosition());
//        for (Region r : availableRegions) {
//            System.out.println(r.getPosition());
//        }

        // Portrait of the current player hero
        final Hero currentHero = parent.whoseTurn();

        if (currentHero instanceof Warrior) {
            warriorPortraitImage.setSize(Gdx.graphics.getWidth()*45/640, Gdx.graphics.getHeight()*70/480);
            warriorPortraitImage.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-warriorPortraitImage.getHeight()-5);
//            warriorPortraitImage.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    System.out.println("Warrior Portrait Clicked.");
//                }
//            });
            stage.addActor(warriorPortraitImage);
        } else if (currentHero instanceof Archer) {
            archerPortraitImage.setSize(Gdx.graphics.getWidth()*45/640, Gdx.graphics.getHeight()*70/480);
            archerPortraitImage.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-archerPortraitImage.getHeight()-5);
//            archerPortraitImage.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    System.out.println("Archer Portrait Clicked.");
//                }
//            });
            stage.addActor(archerPortraitImage);
        } else if (currentHero instanceof Wizard) {
            wizardPortraitImage.setSize(Gdx.graphics.getWidth()*45/640, Gdx.graphics.getHeight()*70/480);
            wizardPortraitImage.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-wizardPortraitImage.getHeight()-5);
//            wizardPortraitImage.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    System.out.println("Wizard Portrait Clicked.");
//                }
//            });
            stage.addActor(wizardPortraitImage);
        } else if (currentHero instanceof Dwarf) {
            dwarfPortraitImage.setSize(Gdx.graphics.getWidth()*45/640, Gdx.graphics.getHeight()*70/480);
            dwarfPortraitImage.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-dwarfPortraitImage.getHeight()-5);
//            dwarfPortraitImage.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    System.out.println("Dwarf Portrait Clicked.");
//                }
//            });
            stage.addActor(dwarfPortraitImage);
        }

        // Displaying Hero Information
//        heroInformation = new TextButton(String.valueOf(currentHero.getHours()), parent.skin);
        heroInformation = new TextButton("GOLD: "+parent.whoseTurn().getGold()
                +"\nSTRENGTH: "+parent.whoseTurn().getStrengthPoint()
                +"\nWILLPOWER: "+parent.whoseTurn().getWillPower()
                +"\nUSED HOURS: "+parent.whoseTurn().getHours(), parent.skin);
        heroInformation.setTouchable(Touchable.disabled);
        heroInformation.getLabel().setFontScale(0.8f);
        heroInformation.setPosition(Gdx.graphics.getWidth()/4+warriorPortraitImage.getWidth(), Gdx.graphics.getHeight()-heroInformation.getHeight()-5);
        stage.addActor(heroInformation);

        // Displaying number of castle shields
        castleShields = new TextButton("Remaining Shields: " + gameBoard.getCastle().getShield(), parent.skin);
        castleShields.setTouchable(Touchable.disabled);
        castleShields.setPosition(heroInformation.getX()+heroInformation.getWidth(), Gdx.graphics.getHeight()-castleShields.getHeight()-5);
        stage.addActor(castleShields);


        // Show button to display the available paths for current hero
        pathTexture = new Texture(Gdx.files.internal("andor_path_button.png"));
        pathButtonImage = new Image(pathTexture);
        pathButtonImage.setSize(Gdx.graphics.getWidth()*30/640, Gdx.graphics.getWidth()*30/640);
        pathButtonImage.setPosition(Gdx.graphics.getWidth()-pathButtonImage.getWidth()-10, Gdx.graphics.getHeight()-pathButtonImage.getHeight()-10);
        pathButtonImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Path Button Clicked.");
                for (Region r : availableRegions) {
                    System.out.println(r.getPosition());
                }
            }
        });
        stage.addActor(pathButtonImage);

        //Equipment bag button


        equipmentBagButton = new TextButton("Equipment Bag", parent.skin);

        equipmentBagButton.setTouchable(Touchable.enabled);
        equipmentBagButton.setPosition(Gdx.graphics.getWidth()/4+warriorPortraitImage.getWidth(), Gdx.graphics.getHeight()-2*heroInformation.getHeight()+23);

        equipmentBagButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(currentHero instanceof Warrior){
                    parent.changeScreen(Andor.EQUIPMENT_WARRIOR);
                }
                else if(currentHero instanceof Archer){
                    parent.changeScreen(Andor.EQUIPMENT_ARCHER);
                }
                else if(currentHero instanceof Wizard){
                    parent.changeScreen(Andor.EQUIPMENT_WIZARD);
                }
                else if(currentHero instanceof Dwarf){
                    parent.changeScreen(Andor.EQUIPMENT_DWARF);
                }


            }
        });

        stage.addActor(equipmentBagButton);

        pathButtons.clear();
        // if the player doesn't have to stop, or if he is the only one remaining in the day
        if (!hasToStop || parent.getPlayerHeroes().size()-parent.getFinishedHeroes().size() == 1) {
            float newY = pathButtonImage.getY();
            for (int i = 0; i < availableRegions.size(); i++) {
                final Region region = availableRegions.get(i);
                TextButton pathButton = new TextButton(String.valueOf(region.getPosition()), parent.skin);
                pathButton.setSize(Gdx.graphics.getWidth() * 30 / 640, Gdx.graphics.getWidth() * 30 / 640);
                newY -= pathButton.getHeight();
                //            pathButton.setPosition(Gdx.graphics.getWidth()-pathButton.getWidth()-10, newY);
                pathButton.setPosition(pathButtonImage.getX(), newY);
                pathButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        currentHero.moveTo(gameBoard.getRegion(currentHero.getPosition()), region);
                        //                    System.out.println("Hero "+currentHero.getTypeOfHero()+" moved to region "+currentHero.getPosition());
                        //                    System.out.println("Hero "+currentHero.getTypeOfHero()+" used "+currentHero.getHours()+" hours in total in the day.");
                        if (currentHero instanceof Warrior) {
                            //                        int x = gameBoard.getRegion(currentHero.getPosition()).getX();
                            //                        int y = gameBoard.getRegion(currentHero.getPosition()).getY();
                            //                        warrior.x = calcX(x) - warrior.width/2;
                            //                        warrior.y = calcY(y) - warrior.height/2;
                            updateHeroPosition(currentHero, warrior);
                            if (currentHero.getFarmers().size() > 0) {
                                updateFarmerPosition(currentHero.getFarmers());
                            }
                            skipping = false;
                            canBattle = false;
                        } else if (currentHero instanceof Archer) {
                            updateHeroPosition(currentHero, archer);
                            if (currentHero.getFarmers().size() > 0) {
                                updateFarmerPosition(currentHero.getFarmers());
                            }
                            skipping = false;
                            canBattle = false;
                        } else if (currentHero instanceof Wizard) {
                            updateHeroPosition(currentHero, wizard);
                            if (currentHero.getFarmers().size() > 0) {
                                updateFarmerPosition(currentHero.getFarmers());
                            }
                            skipping = false;
                            canBattle = false;
                        } else if (currentHero instanceof Dwarf) {
                            updateHeroPosition(currentHero, dwarf);
                            if (currentHero.getFarmers().size() > 0) {
                                updateFarmerPosition(currentHero.getFarmers());
                            }
                            skipping = false;
                            canBattle = false;
                        }
                        show();
                    }
                });
                pathButtons.add(pathButton);
                stage.addActor(pathButton);
            }
        }


        // Add button to skip/finish turn
        // don't allow the player to skip or finish turn if he/she is the only one remaining in the day
        if (parent.getPlayerHeroes().size()-parent.getFinishedHeroes().size() > 1) {
            if (skipping) {
                TextButton skipTurn = new TextButton("Skip Turn", parent.skin);
                skipTurn.setPosition(10, 10);
                skipTurn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        currentHero.incrementHours();
                        parent.nextTurn();
                        hasToStop = false;
                        canBattle = true;
                        show();
                    }
                });
                stage.addActor(skipTurn);
            } else {
                TextButton finishTurn = new TextButton("Finish Turn", parent.skin);
                finishTurn.setPosition(10, 10);
                finishTurn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        parent.nextTurn();
                        skipping = true;
                        canBattle = true;
                        hasToStop = false;
                        show();
                    }
                });
                stage.addActor(finishTurn);

            }
        }

        if ((currentHero.getHours() >= 7) && (currentHero.getWillPower() >= 2)) {
            TextButton finishDay = new TextButton("Finish Day", parent.skin);
            finishDay.setPosition(100, 10);
            finishDay.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Finish Day for This Hero
                    skipping = true;
                    hasToStop = false;
                    canBattle = true;
                    parent.finishDay();
                    show();
                }
            });
            stage.addActor(finishDay);
        }

//        // Add time marker
//        timeMarker = new TextButton(String.valueOf(currentHero.getHours()), parent.skin);
//        timeMarker.setTouchable(Touchable.disabled);
//        timeMarker.setSize(Gdx.graphics.getWidth()*30/640, Gdx.graphics.getWidth()*30/640);
//        timeMarker.setPosition(Gdx.graphics.getWidth()-timeMarker.getWidth()-10, 10);
//        stage.addActor(timeMarker);

        // Show where golds are in the map
        String displayGoldInfo = "Golds dropped: ";
        for (Region region : gameBoard.getGoldRegions()) {
            int golds = region.getGold();
            int position = region.getPosition();
            displayGoldInfo += golds+"G in "+position+",";
        }
        goldInformation = new TextButton(displayGoldInfo, parent.skin);
        goldInformation.setTouchable(Touchable.disabled);
        goldInformation.setPosition(200, 10);
        stage.addActor(goldInformation);


        // Battle button
        battleButton = new TextButton("Start Battle", parent.skin);
        if(gameBoard.getRegion(currentHero.getPosition()).getMonster() != null && canBattle) {
            // can attack the monster only if he is on the same space as the monster and at the beginning of a turn
            battleButton.setPosition(200, goldInformation.getHeight()+15);
            battleButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Perform battle
                    Monster monster = gameBoard.getRegion(currentHero.getPosition()).getMonster();
                    if (currentHero instanceof Archer) {
                        archerBattleDialogue((Archer)currentHero, monster, 1, ((Archer) currentHero).getNumOfDice(), 0, 0);
                    } else if (currentHero instanceof Wizard) {
                        wizardBattleDialogue((Wizard)currentHero, monster, 1, 0);
                    } else {
                        battleDialog(currentHero, monster, 1, 0);
                    }
                    skipping = false;
//                    show();
                }
            });
            stage.addActor(battleButton);
        }


        // Buttons to drop/pickup gold
        dropGoldButton = new TextButton("Drop Gold", parent.skin);
        if (currentHero.getGold() > 0) {
            dropGoldButton.setPosition(Gdx.graphics.getWidth() - dropGoldButton.getWidth() - 10, 10);
            dropGoldButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Perform Drop Gold
                    currentHero.dropGold();
                    gameBoard.getRegion(currentHero.getPosition()).addGold();
                    show();
                }
            });
            stage.addActor(dropGoldButton);
        }

        pickUpGoldButton = new TextButton("Pickup Gold", parent.skin);
        if (gameBoard.getRegion(currentHero.getPosition()).getGold() > 0) {
            pickUpGoldButton.setPosition(Gdx.graphics.getWidth()-pickUpGoldButton.getWidth()-10, dropGoldButton.getHeight()+15);
            pickUpGoldButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Perform Pickup Gold
                    currentHero.pickUpGold();
                    gameBoard.getRegion(currentHero.getPosition()).removeGold();
                    show();
                }
            });
            stage.addActor(pickUpGoldButton);
        }


        // Buttons to pickup/drop off farmer
        dropFarmer = new TextButton("Drop Off Farmer", parent.skin);
        if (currentHero.getFarmers().size() != 0) {
            dropFarmer.setPosition(Gdx.graphics.getWidth()-dropFarmer.getWidth()-110, 10);
            dropFarmer.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Perform Drop Farmer
                    currentHero.dropOffFarmer(currentHero.getFarmers().get(0), gameBoard.getRegion(currentHero.getPosition()));
                    show();
                }
            });
            stage.addActor(dropFarmer);
        }

        pickUpFarmer = new TextButton("Pickup Farmer", parent.skin);
        if (gameBoard.getRegion(currentHero.getPosition()).getFarmers().size() > 0) {
            pickUpFarmer.setPosition(Gdx.graphics.getWidth()-pickUpFarmer.getWidth()-110, dropFarmer.getHeight()+15);
//            pickUpFarmer.setPosition(Gdx.graphics.getWidth()-pickUpFarmer.getWidth()-110, 10);
            pickUpFarmer.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Perform Pickup Farmer
                    currentHero.pickupFarmer(gameBoard.getRegion(currentHero.getPosition()).getFarmers().get(0), gameBoard.getRegion(currentHero.getPosition()));
                    show();
                }
            });
            stage.addActor(pickUpFarmer);
        }

        // Well interaction button
        drinkWell = new TextButton("Drink Well", parent.skin);
        if (gameBoard.getRegion(currentHero.getPosition()).getWell() != null && !gameBoard.getRegion(currentHero.getPosition()).getWell().isEmpty()) {
            drinkWell.setPosition(Gdx.graphics.getWidth()-drinkWell.getWidth()-250, 10);
            drinkWell.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Perform drink well
                    currentHero.drinkWell(gameBoard.getRegion(currentHero.getPosition()).getWell());
                    if (!skipping) {
                        hasToStop = true;
                    }
                    show();
                }
            });
            stage.addActor(drinkWell);
        }


        // Merchant interaction button
        merchantButton = new TextButton("Not at Merchant Yet", parent.skin);
        if (gameBoard.getRegion(currentHero.getPosition()) instanceof Merchant) {
            if (currentHero.getGold() >= 2) {
                merchantButton.setText("Visit Merchant Store");
                merchantButton.setPosition(Gdx.graphics.getWidth()-merchantButton.getWidth()-10, dropGoldButton.getHeight()+pickUpGoldButton.getHeight()+15);
//                merchantButton.addListener(new ChangeListener() {
//                    @Override
//                    public void changed(ChangeEvent event, Actor actor) {
//                        // Perform SP purchase
//                        //((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellSP(currentHero);
//                        parent.changeScreen(Andor.PREFERENCE);
//                        if (!skipping) {
//                            hasToStop = true;
//                        }
//
//                        show();
//                    }
//                });

                merchantButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {

                        parent.changeScreen(Andor.MERCHANT);

                        if (!skipping) {
                            hasToStop = true;
                        }
                        show();


                    }
                });
                stage.addActor(merchantButton);
            } else {
                merchantButton.setText("Not Enough Gold");
                merchantButton.setTouchable(Touchable.disabled);
                merchantButton.setPosition(Gdx.graphics.getWidth()-merchantButton.getWidth()-10, dropGoldButton.getHeight()+pickUpGoldButton.getHeight()+15);
                stage.addActor(merchantButton);
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));

        // Tell camera to update its matrices
        camera.update();

        // Tell SpriteBatch to render in the coordinate system specified by the camera
        stage.getBatch().setProjectionMatrix(camera.combined);

        // Begin a new batch and draw
        stage.getBatch().begin();
        stage.getBatch().setColor(Color.WHITE); // to prevent the flickering from the dialogs

        stage.getBatch().draw(parent.andorBoard, 0, 0);
//        parent.batch.draw(playerTexture, player.x, player.y, player.width, player.height);


        stage.getBatch().draw(skralTexture, skral.x, skral.y, skral.width, skral.height);

        // Draw gors
        for (Rectangle gor : gors) {
            stage.getBatch().draw(gorTexture, gor.x, gor.y, gor.width, gor.height);
        }


        // Draw farmers
        for (Farmer farmer : gameBoard.getFarmers()) {
            if (farmer.isAlive()) {
                if (farmer.getFarmerNumber() == 1) {
                    stage.getBatch().draw(farmerTexture, farmer1.x, farmer1.y, farmer1.width, farmer1.height);
                } else if (farmer.getFarmerNumber() == 2) {
                    stage.getBatch().draw(farmerTexture, farmer2.x, farmer2.y, farmer2.width, farmer2.height);
                }
            }
        }

        for (Hero hero : playerHeroes) {
            for (Farmer farmer : hero.getFarmers()) {
                if (farmer.isAlive()) {
                    if (farmer.getFarmerNumber() == 1) {
                        stage.getBatch().draw(farmerTexture, farmer1.x, farmer1.y, farmer1.width, farmer1.height);
                    } else if (farmer.getFarmerNumber() == 2) {
                        stage.getBatch().draw(farmerTexture, farmer2.x, farmer2.y, farmer2.width, farmer2.height);
                    }
                }
            }
        }


//        // Display hero information
//        font.getData().setScale(10.0f);
//        font.draw(stage.getBatch(), "GOLD: "+parent.whoseTurn().getGold()
//                +"\nSTRENGTH: "+parent.whoseTurn().getStrengthPoint()
//                +"\nWILLPOWER: "+parent.whoseTurn().getWillPower()
//                +"\nUSED HOURS: "+parent.whoseTurn().getHours(), calcX(370), calcY(10));
//        ArrayList<Hero> playerHeroes = parent.getPlayerHeroes();

        for (int i = 0; i < playerHeroes.size(); i++) {
            if (playerHeroes.get(i).getTypeOfHero() == 1) {
                stage.getBatch().draw(archerTexture, archer.x, archer.y, archer.width, archer.height);
            }
            if (playerHeroes.get(i).getTypeOfHero() == 2) {
                stage.getBatch().draw(dwarfTexture, dwarf.x, dwarf.y, dwarf.width, dwarf.height);
            }
            if (playerHeroes.get(i).getTypeOfHero() == 3) {
                stage.getBatch().draw(warriorTexture, warrior.x, warrior.y, warrior.width, warrior.height);
            }
            if (playerHeroes.get(i).getTypeOfHero() == 4) {
                stage.getBatch().draw(wizardTexture, wizard.x, wizard.y, wizard.width, wizard.height);
            }
        }


        stage.getBatch().end();
        stage.draw();

//        // Process user input
//        if (Gdx.input.isTouched()) {
////            System.out.println("X: "+Gdx.input.getX()+"   Y: "+Gdx.input.getY());
//            Vector3 touchPos = new Vector3();
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(touchPos);
//            warrior.x = touchPos.x - warrior.width / 2;
//            warrior.y = touchPos.y - warrior.height / 8;
//        }

        // make sure the bucket stays within the screen bounds
//        if (warrior.x < 0) {
//            warrior.x = 0;
//        }
//        if (warrior.x > parent.andorBoard.getWidth() - warrior.width) {
//            warrior.x = parent.andorBoard.getWidth() - warrior.width;
//        }
//        if (warrior.y < 0) {
//            warrior.y = 0;
//        }
//        if (warrior.y > parent.andorBoard.getHeight() - warrior.height) {
//            warrior.y = parent.andorBoard.getHeight() - warrior.height;
//        }

    }

    public void heroPortraitResize(Image heroPortraitImage) {
        heroPortraitImage.setSize(Gdx.graphics.getWidth()*45/640, Gdx.graphics.getHeight()*70/480);
        heroPortraitImage.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-warriorPortraitImage.getHeight()-5);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        heroPortraitResize(warriorPortraitImage);
        heroPortraitResize(archerPortraitImage);
        heroPortraitResize(wizardPortraitImage);
        heroPortraitResize(dwarfPortraitImage);

        heroInformation.setPosition(Gdx.graphics.getWidth()/4+warriorPortraitImage.getWidth(), Gdx.graphics.getHeight()-heroInformation.getHeight()-5);

        pathButtonImage.setSize(Gdx.graphics.getWidth()*30/640, Gdx.graphics.getWidth()*30/640);
        pathButtonImage.setPosition(Gdx.graphics.getWidth()-pathButtonImage.getWidth()-10, Gdx.graphics.getHeight()-pathButtonImage.getHeight()-10);
        float newY = pathButtonImage.getY();
        for (TextButton button : pathButtons) {
            button.setSize(Gdx.graphics.getWidth()*30/640, Gdx.graphics.getWidth()*30/640);
            newY -= button.getHeight();
            button.setPosition(pathButtonImage.getX(), newY);
        }

//        timeMarker.setSize(Gdx.graphics.getWidth()*30/640, Gdx.graphics.getWidth()*30/640);
//        timeMarker.setPosition(Gdx.graphics.getWidth()-timeMarker.getWidth()-10, 10);

        dropGoldButton.setPosition(Gdx.graphics.getWidth() - dropGoldButton.getWidth() - 10, 10);
        pickUpGoldButton.setPosition(Gdx.graphics.getWidth()-pickUpGoldButton.getWidth()-10, dropGoldButton.getHeight()+15);
        dropFarmer.setPosition(Gdx.graphics.getWidth()-dropFarmer.getWidth()-110, 10);
        pickUpFarmer.setPosition(Gdx.graphics.getWidth()-pickUpFarmer.getWidth()-110, dropFarmer.getHeight()+15);
        drinkWell.setPosition(Gdx.graphics.getWidth()-drinkWell.getWidth()-250, 10);
        equipmentBagButton.setPosition(Gdx.graphics.getWidth()/4+warriorPortraitImage.getWidth(),Gdx.graphics.getHeight()-2*heroInformation.getHeight()+23);
        merchantButton.setPosition(Gdx.graphics.getWidth()-merchantButton.getWidth()-10, dropGoldButton.getHeight()+pickUpGoldButton.getHeight()+15);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
//        playerTexture.dispose();
        warriorTexture.dispose();
        warriorPortraitTexture.dispose();
        archerTexture.dispose();
        archerPortraitTexture.dispose();
        wizardTexture.dispose();
        wizardPortraitTexture.dispose();
        dwarfTexture.dispose();
        dwarfPortraitTexture.dispose();

        farmerTexture.dispose();
        gorTexture.dispose();
        skralTexture.dispose();
        pathTexture.dispose();
        wellTexture.dispose();
        coveredFogTexture.dispose();
    }
}