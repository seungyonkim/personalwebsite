package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;
import com.mygdx.game.character.Archer;
import com.mygdx.game.character.Dwarf;
import com.mygdx.game.character.Hero;
import com.mygdx.game.character.Warrior;
import com.mygdx.game.character.Wizard;
import com.mygdx.game.monster.Gor;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.monster.Skral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class BattleScreen implements Screen {

    private Andor parent;
    private Stage stage;
    private Socket socket;

    private int numOfPlayers;
    //    private Skin skin;
    private Texture warriorTexture;
    private Texture archerTexture;
    private Texture wizardTexture;
    private Texture dwarfTexture;
    private Texture gorTexture;
    private Texture skralTexture;
    private Label titleLabel;
    private TextArea areaInfo;


    private Label titleLabel2;


    TextButton rollButton;
    TextButton backButton;
    TextButton startButton;
    TextButton stopRolling;
    TextButton flipButton;
    private ArrayList<Hero>HeroBattling ;
    final private Monster  monsterBattling;

    private int round =0;
    private boolean flip;
    private int lastRoll;
    private boolean stopRoll;
    private int numberDiceUsed ;




    //    private TextureRegion myTextureRegion;
//    private TextureRegionDrawable myTexRegionDrawable;
//    private ImageButton button;
    ArrayList<Hero> battlePlayers;


    public BattleScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());
        //battlePlayers ();
        this.socket= parent.getSocket();

        archerTexture = new Texture(Gdx.files.internal("characters/andor_archer_M.png"));
        wizardTexture = new Texture(Gdx.files.internal("characters/andor_wizard_M.png"));
        dwarfTexture = new Texture(Gdx.files.internal("characters/andor_dwarf_M.png"));
        warriorTexture = new Texture(Gdx.files.internal("characters/andor_warrior_M.png"));
        gorTexture = new Texture(Gdx.files.internal("monsters/andor_gor.png"));
        skralTexture = new Texture(Gdx.files.internal("monsters/andor_skral.png"));

        titleLabel = new Label( "Battle Screen", parent.skin);

        HeroBattling = parent.getHeroBattling();
        monsterBattling = parent.getMonsterBattling();

        //updateBattle();



    }

    public void battleDialog(Hero p) {

            if (p.getCanPlay()) {
                // if the player can play on (has enough hours left or wp to use for overtime)
                final Hero player = p;

                if (round == 0) {
                    areaInfo.setText("");
                    areaInfo.appendText("Battle started.\n");
                    areaInfo.appendText("Monster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + player.getStrengthPoint() + " / Willpower: " + player.getWillPower() + " / Hours used: " + player.getHours() + "\n");
                    startButton.remove();
                    rollButton.setDisabled(false);
                    round++;
                } else {
                    areaInfo.setText("");

                    areaInfo.appendText("Battle vs. Monster / Round " + round + "\n");

                    // if player chooses to roll the dice
                    final int heroBattleValue = player.rollDice() + player.getStrengthPoint();
                    // if player chooses to roll gor's dice
                    int monsterDice = monsterBattling.rollDice();
                    int gorBattleValue = monsterBattling.getStrengthPoint() + monsterDice;

                    areaInfo.appendText("You just got a battle value of: " + heroBattleValue + "\n");
                    areaInfo.appendText("The monster got a battle value of " + gorBattleValue + "\n");
                    int lastResult = player.battle(heroBattleValue, gorBattleValue, monsterBattling);
                    round++;
                    if (lastResult > 0) {
                        areaInfo.appendText("You have won the last round.\nMonster lost " + lastResult + " willpower.\nMonster's current attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + player.getStrengthPoint() + " / Willpower: " + player.getWillPower() + " / Hours used: " + player.getHours() + "\n");
                    } else if (lastResult < 0) {
                        areaInfo.appendText("You have lost the last round.\nYou lost " + Math.abs(lastResult) + " willpower.\nMonster's current attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + player.getStrengthPoint() + "/ Willpower: " + player.getWillPower() + "/ Hours used: " + player.getHours() + "\n");
                    } else {
                        areaInfo.appendText("Last round ended in a Draw.\nMonster's current attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + player.getStrengthPoint() + "  / Willpower: " + player.getWillPower() + " / Hours used: " + player.getHours() + "\n");
                    }
                    if (player.getWillPower() == 0) {
                        playerLose(player, monsterBattling);
                    } else if (monsterBattling.getWillPower() == 0) {
                        playerWin(monsterBattling);
                    }

                }


            } else {
                // if the player cannot play on because he has neither any hours left nor willpower to use for the overtime
                areaInfo.setText("");
                areaInfo.appendText("Battle vs. Monster / Over");

                areaInfo.appendText("\nYou cannot continue the battle because you don't have any hours left in the day.\nTry again next day.");

                rollButton.setDisabled(true);

            }

    }
    public void archerBattleDialogue(Archer archer, int n) {

        final int numOfDiceLeft = n - numberDiceUsed;

        if (archer.getCanPlay() && archer.getWillPower() > 0 && numOfDiceLeft > 0) {
            // if the player can play on


            if (round == 0) {
                areaInfo.setText("");
                areaInfo.appendText("Battle started.\n");
                areaInfo.appendText("Monster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + archer.getStrengthPoint() + " / Willpower: " + archer.getWillPower() + " / Hours used: " + archer.getHours() + "\n");
                startButton.remove();
                rollButton.setDisabled(false);
                round++;
            }else {

                areaInfo.setText("");
                areaInfo.appendText("Battle vs. Monster / Round " + round + "\n");
                Random rand = new Random();
                int newRoll = rand.nextInt(6) + 1;
                final int last = this.lastRoll;
                if (!stopRoll) {
                    stopRolling.setDisabled(false);
                    areaInfo.appendText("Monster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + archer.getStrengthPoint() + " / Willpower: " + archer.getWillPower() + " / Hours used: " + archer.getHours() + "\nRemaining Dice: " + numOfDiceLeft + "\n");
                    areaInfo.appendText("\n\nYou just got a dice value of: " + newRoll + "\n");
                    this.lastRoll = newRoll;
                    numberDiceUsed++;
                }
                else {
                    round = round + 1;
                    areaInfo.setText("");
                    // if player chooses to roll the dice
                    final int heroBattleValue = last + archer.getStrengthPoint();
                    // if player chooses to roll gor's dice
                    int monsterDice = monsterBattling.rollDice();
                    int gorBattleValue = monsterBattling.getStrengthPoint() + monsterDice;

                    areaInfo.appendText("\n\nYou just got a battle value of: " + heroBattleValue + "\n");
                    areaInfo.appendText("The monster go a battle value of " + gorBattleValue + "\n");
                    int lastResult = archer.battle(heroBattleValue, gorBattleValue, monsterBattling);
                    if (lastResult > 0) {
                        areaInfo.appendText("You have won the last round.\nMonster lost " + lastResult + " willpower.\nMonster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + archer.getStrengthPoint() + "  / Willpower: " + archer.getWillPower() + " / Hours used: " + archer.getHours() + "\n");
                    } else if (lastResult < 0) {
                        areaInfo.appendText("You have lost the last round.\nYou lost " + Math.abs(lastResult) + " willpower.\nMonster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + archer.getStrengthPoint() + " / Willpower: " + archer.getWillPower() + " / Hours used: " + archer.getHours() + "\n");
                    } else {
                        areaInfo.appendText("Last round ended in a Draw.\nMonster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + archer.getStrengthPoint() + " / Willpower: " + archer.getWillPower() + " / Hours used: " + archer.getHours() + "\n");
                    }
                    if (archer.getWillPower() == 0) {
                        playerLose(archer, monsterBattling);
                    } else if (monsterBattling.getWillPower() == 0) {
                        playerWin(monsterBattling);
                    }

                    stopRoll = false;
                }
            }

        } else {
            // if the player cannot play on because he has neither any hours left nor willpower to use for the overtime
            areaInfo.setText("");
            areaInfo.appendText("Battle vs. Monster / Over");

            areaInfo.appendText("\nYou cannot continue the battle because you don't have any hours left in the day.\nTry again next day.");

            rollButton.setDisabled(true);

        }



    }

    public void wizardBattleDialogue(Wizard wizard) {
        // wizard's dice roll, since wizard has a special way to decide dice value
        if (wizard.getCanPlay() && wizard.getWillPower() > 0) {
            // if the player can play on
            if (round == 0) {
                areaInfo.setText("");
                areaInfo.appendText("Battle started.\n");
                areaInfo.appendText("Monster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + wizard.getStrengthPoint() + " / Willpower: " + wizard.getWillPower() + " / Hours used: " + wizard.getHours() + "\n");
                startButton.remove();
                flipButton.setDisabled(false);
                round++;
            } else {

                // Roll dice
                Random rand = new Random();
                final int newRoll = rand.nextInt(6) + 1;
                areaInfo.appendText("Battle vs. Monster / Round " + round +"\n");
                areaInfo.appendText("Monster's current attributes:\nStrength: " + monsterBattling.getStrengthPoint() + "\nWillpower: " + monsterBattling.getWillPower() + "\n\n\nYour Strength: " + wizard.getStrengthPoint() + "\nWillpower: " + wizard.getWillPower() + "\nHours used: " + wizard.getHours() + "\nDice Rolled: " + newRoll + "\n");
                int bv;
                if (flip) {
                    bv = (7 - newRoll) + wizard.getStrengthPoint();
                    flip = false;
                } else {
                    bv = newRoll + wizard.getStrengthPoint();
                }
                final int heroBattleValue = bv;
                int gorBattleValue = monsterBattling.getStrengthPoint() + monsterBattling.rollDice();

                int lastResult = wizard.battle(heroBattleValue, gorBattleValue, monsterBattling);

                if (lastResult > 0) {
                    areaInfo.appendText("You have won the last round.\nMonster lost " + lastResult + " willpower.\nMonster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + wizard.getStrengthPoint() + "  / Willpower: " + wizard.getWillPower() + " / Hours used: " + wizard.getHours() + "\n");
                } else if (lastResult < 0) {
                    areaInfo.appendText("You have lost the last round.\nYou lost " + Math.abs(lastResult) + " willpower.\nMonster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + wizard.getStrengthPoint() + " / Willpower: " + wizard.getWillPower() + " / Hours used: " + wizard.getHours() + "\n");
                } else {
                    areaInfo.appendText("Last round ended in a Draw.\nMonster's attributes: Strength: " + monsterBattling.getStrengthPoint() + " / Willpower: " + monsterBattling.getWillPower() + "\nYour Strength: " + wizard.getStrengthPoint() + " / Willpower: " + wizard.getWillPower() + " / Hours used: " + wizard.getHours() + "\n");
                }

                if (wizard.getWillPower() == 0) {
                    playerLose(wizard, monsterBattling);
                } else if (monsterBattling.getWillPower() == 0) {
                    playerWin(monsterBattling);
                }




            }
        } else {
            // if the player cannot play on because he has neither any hours left nor willpower to use for the overtime
            areaInfo.setText("");
            areaInfo.appendText("Battle vs. Monster / Over");

            areaInfo.appendText("\nYou cannot continue the battle because you don't have any hours left in the day.\nTry again next day.");

            rollButton.setDisabled(true);

        }




    }



    public void leaveBattle(Monster monster) {
        if (monster instanceof Gor) {
            ((Gor) monster).resetGor();
        } else if (monster instanceof Skral) {
            ((Skral) monster).resetSkral();
        }

    }


    public void playerWin(final Monster monster) {
        new Dialog("Battle vs. Monster / Over", parent.skin) {
            {
                text("Monster has no more willpower left. You have won the battle.");

                button("Okay", false);
            }

            @Override
            protected void result(Object object) {
                leaveBattle(monster);
                parent.changeScreen(Andor.MULTIGAME);

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
                parent.changeScreen(Andor.MULTIGAME);

            }
        }.show(stage);
    }




    @Override
    public void show()
    {
        stage.clear();


        connectSocket();
        configSocketEvents();

        updateBattle();


        Gdx.input.setInputProcessor(stage);

        Table table = createTable();

        stage.addActor(table);



    }

    public Table createTable(){
        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
//        stage.addActor(table);

        final Hero myHero = parent.getMyHero();




//        titleLabel = new Label( "Choose Heroes upto "+parent.getNumOfPlayers(), parent.skin);
        startButton = new TextButton("Start the battle",parent.skin);
        backButton = new TextButton("Leave battle", parent.skin);
        rollButton = new TextButton("Roll dice",parent.skin);
        flipButton = new TextButton("Flip dice",parent.skin);
        stopRolling = new TextButton("Stop rolling", parent.skin);
        table.add(titleLabel).colspan(8);



        table.row().pad(10, 0, 0, 0);

        table.add(startButton).colspan(2);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {


                if (myHero instanceof Archer) {
                    archerBattleDialogue((Archer) myHero, ((Archer) myHero).getNumOfDice());
                } else if (parent.getMyHero() instanceof Wizard) {
                    wizardBattleDialogue((Wizard) myHero);
                } else {
                    battleDialog(parent.getMyHero() );
                }

            }
        });
        table.row().pad(30, 0, 0, 0);


        final ArrayList<String> availableHeroes = new ArrayList<String>();
        final ArrayList<String> availableMonster = new ArrayList<String>();
        for(Hero hero : HeroBattling ){
            availableHeroes.add(hero.getTypeOfHeroString());
        }
        availableMonster.add(monsterBattling.getMonsterType());



        if (availableHeroes.contains("Warrior")) {
            final Image warriorImage = new Image(warriorTexture);
            warriorImage.setSize(60, 80);
            warriorImage.setPosition(Gdx.graphics.getWidth() / 5 - warriorImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);


            table.add(warriorImage).width(120).height(160).padRight(10).colspan(1);



        }

        if (availableHeroes.contains("Archer")) {
            Image archerImage = new Image(archerTexture);
            archerImage.setSize(60, 80);
            archerImage.setPosition(Gdx.graphics.getWidth() * 2 / 5 - archerImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);

            table.add(archerImage).width(120).height(160).padRight(10).colspan(1);
        }

        if (availableHeroes.contains("Wizard") ) {
            Image wizardImage = new Image(wizardTexture);
            wizardImage.setSize(60, 80);
            wizardImage.setPosition(Gdx.graphics.getWidth() * 3 / 5 - wizardImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);

            table.add(wizardImage).width(120).height(160).padRight(10).colspan(1);
        }

        if (availableHeroes.contains("Dwarf")) {
            Image dwarfImage = new Image(dwarfTexture);
            dwarfImage.setSize(60, 80);
            dwarfImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - dwarfImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);

            table.add(dwarfImage).width(120).height(160).padRight(10).colspan(1);

        }

        if(availableMonster.contains("Skral")){
            Image skarlImage = new Image(skralTexture);
            skarlImage.setSize(60, 80);
            skarlImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - skarlImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);

            table.add(skarlImage).width(120).height(160).padRight(10).colspan(1);

        }
        if(availableMonster.contains("Gor")){
            Image gorImage = new Image(gorTexture);
            gorImage.setSize(60, 80);
            gorImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - gorImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);

            table.add(gorImage).width(120).height(160).padRight(10).colspan(1);

        }



        table.row().pad(10, 0, 0, 0);




        table.row().pad(10, 0, 0, 0);

        titleLabel2 =  new Label( "Information ", parent.skin);
        table.add(titleLabel2).colspan(8);

        table.row().pad(20, 0, 0, 0);


        areaInfo = new TextArea("",parent.skin);
        areaInfo.setDisabled(true);


        table.add(areaInfo).prefSize(500).colspan(8);

        areaInfo.appendText("Welcome to the battle screen. Start the battle.\n");
        areaInfo.appendText("The current player battling are : \n");
        for(Hero hero : parent.getHeroBattling()){
            areaInfo.appendText(hero.getTypeOfHeroString() +"\n");
        }
            table.row().pad(10, 0, 0, 0);


        table.add(stopRolling).colspan(2);
        stopRolling.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                   stopRoll = true;

                }
        });

        table.add(flipButton).colspan(2);
        flipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                flip=true;
            }
        });
        stopRolling.setDisabled(true);
        flipButton.setDisabled(true);




        table.row().pad(10, 0, 0, 0);


        table.add(backButton).colspan(2);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                playerLose(myHero, monsterBattling);

            }
        });




        table.add(rollButton).colspan(2);
        rollButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (myHero instanceof Archer) {
                    archerBattleDialogue((Archer) myHero, ((Archer) myHero).getNumOfDice());
                } else if (myHero instanceof Wizard) {
                    wizardBattleDialogue((Wizard) myHero);
                } else {
                    battleDialog(myHero);
                }


            }
        });
        rollButton.setDisabled(true);






        return table;
    }

    @Override
    public void render(float delta)
    {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));

        stage.getBatch().begin();
        stage.getBatch().setColor(Color.WHITE);
        stage.getBatch().draw(parent.andorMenu, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


    public void updateBattle(){

        Hero myHero = parent.getMyHero();


        JSONObject data = new JSONObject();
        try{
            areaInfo.appendText("- "+myHero.getTypeOfHeroString());
            String wantToJoin = myHero.getTypeOfHeroString();
            data.put("wantToJoin",wantToJoin);
            socket.emit("updateBattle", data);
        }catch(Exception e){
            Gdx.app.log("SocketIO", "Error joining battle");

        }

    }
    public void connectSocket(){

        try{
            socket =IO.socket("http://localhost:8080");
            //to make it work on the android emulator use http://10.0.2.2:8080
            socket.connect();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO","Connected");
            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONArray objects = (JSONArray)args[0];
                try {


                }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error getting the new player id");
                }
            }

        }).on("playerChose", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray)args[0];
                try {



                }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error handling the other player choosing");
                }
            }
        }).on("updateBattle", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject data = (JSONObject) args[0];

                try {

                areaInfo.appendText("- " + data.getString("wantToJoin"));

                }catch(Exception e){
                }
            }
        });



    }