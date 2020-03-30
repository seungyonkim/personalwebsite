package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.mygdx.game.monster.Gor;

import org.json.JSONArray;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.ArrayList;

public class MultiGameScreen implements Screen {

    private Andor parent;
    private Stage stage;
    private Socket socket;


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

    private Rectangle farmer;
    private Rectangle coveredFog;
    private Rectangle well;
    //    private Rectangle gor;
    private ArrayList<Rectangle> gors;

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
    private TextButton chat;

//    private BitmapFont font;

    private boolean skipping;

    private Texture warriorPortraitTexture;
    private Image warriorPortraitImage;
    private Texture archerPortraitTexture;
    private Image archerPortraitImage;
    private Texture wizardPortraitTexture;
    private Image wizardPortraitImage;
    private Texture dwarfPortraitTexture;
    private Image dwarfPortraitImage;

    public MultiGameScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());


        gameBoard = parent.getGameBoard();

        playerHeroes = parent.getPlayerHeroes();

        pathButtons = new ArrayList<TextButton>();

        skipping = true;
        socket = parent.getSocket();

//        font = new BitmapFont();

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

        farmer = new Rectangle();
        farmer.width = 300;
        farmer.height = 500;

        coveredFog = new Rectangle();
        coveredFog.width = 300;
        coveredFog.height = 300;

        well = new Rectangle();
        well.width = 300;
        well.height = 300;

//        gor = new Rectangle();
//        gor.width = 300;
//        gor.height = 400;
        gors = new ArrayList<Rectangle>();



        player = new Rectangle();
        player.x = parent.andorBoard.getWidth()/2 - 300/2;
        player.y = parent.andorBoard.getHeight()/2 - 500/2;
        player.width = 300;
        player.height = 500;

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

//        warrior = new Rectangle();
//        warrior.width = 300;
//        warrior.height = 500;
//        warrior.x = parent.andorBoard.getWidth()*2519/9861 - warrior.width/2;
//        warrior.y = parent.andorBoard.getHeight()*3729/6476 - warrior.height/2;


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

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        connectSocket();
        configSocketEvents();

        availableRegions = parent.whoseTurn().getAvailableRegions(gameBoard);
//        System.out.println("Current Hero position: "+parent.whoseTurn().getPosition());
//        for (Region r : availableRegions) {
//            System.out.println(r.getPosition());
//        }


        // Portrait of the current player hero
        final Hero currentHero = parent.whoseTurn();

        if (currentHero instanceof Warrior) {
            warriorPortraitImage.setSize(Gdx.graphics.getWidth()*45/640, Gdx.graphics.getHeight()*70/480);
//            parent.andorBoard.getWidth()*316/1115
            warriorPortraitImage.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-warriorPortraitImage.getHeight()-5);
            warriorPortraitImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Warrior Portrait Clicked.");
                }
            });
            stage.addActor(warriorPortraitImage);
        } else if (currentHero instanceof Archer) {
            archerPortraitImage.setSize(Gdx.graphics.getWidth()*45/640, Gdx.graphics.getHeight()*70/480);
            archerPortraitImage.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-archerPortraitImage.getHeight()-5);
            archerPortraitImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Archer Portrait Clicked.");
                }
            });
            stage.addActor(archerPortraitImage);
        } else if (currentHero instanceof Wizard) {
            wizardPortraitImage.setSize(Gdx.graphics.getWidth()*45/640, Gdx.graphics.getHeight()*70/480);
            wizardPortraitImage.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-wizardPortraitImage.getHeight()-5);
            wizardPortraitImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Wizard Portrait Clicked.");
                }
            });
            stage.addActor(wizardPortraitImage);
        } else if (currentHero instanceof Dwarf) {
            dwarfPortraitImage.setSize(Gdx.graphics.getWidth()*45/640, Gdx.graphics.getHeight()*70/480);
            dwarfPortraitImage.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-dwarfPortraitImage.getHeight()-5);
            dwarfPortraitImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Dwarf Portrait Clicked.");
                }
            });
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

        ///////////////////////////
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

        pathButtons.clear();
        float newY = pathButtonImage.getY();
        for (int i = 0; i < availableRegions.size(); i++) {
            final Region region = availableRegions.get(i);
            TextButton pathButton = new TextButton(String.valueOf(region.getPosition()), parent.skin);
            pathButton.setSize(Gdx.graphics.getWidth()*30/640, Gdx.graphics.getWidth()*30/640);
            newY -= pathButton.getHeight();
//            pathButton.setPosition(Gdx.graphics.getWidth()-pathButton.getWidth()-10, newY);
            pathButton.setPosition(pathButtonImage.getX(), newY);
            pathButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    currentHero.moveTo(gameBoard.getRegion(currentHero.getPosition()), region);
                    currentHero.setMoved();
                    System.out.println("Hero "+currentHero.getTypeOfHero()+" moved to region "+currentHero.getPosition());
                    System.out.println("Hero "+currentHero.getTypeOfHero()+" used "+currentHero.getHours()+" hours in total in the day.");
                    if (currentHero instanceof Warrior) {
                        int x = gameBoard.getRegion(currentHero.getPosition()).getX();
                        int y = gameBoard.getRegion(currentHero.getPosition()).getY();
                        warrior.x = calcX(x) - warrior.width/2;
                        warrior.y = calcY(y) - warrior.height/2;
                        skipping = false;
                    } else if (currentHero instanceof Archer) {
                        int x = gameBoard.getRegion(currentHero.getPosition()).getX();
                        int y = gameBoard.getRegion(currentHero.getPosition()).getY();
                        archer.x = calcX(x) - archer.width/2;
                        archer.y = calcY(y) - archer.height/2;
                        skipping = false;
                    } else if (currentHero instanceof Wizard) {
                        int x = gameBoard.getRegion(currentHero.getPosition()).getX();
                        int y = gameBoard.getRegion(currentHero.getPosition()).getY();
                        wizard.x = calcX(x) - wizard.width/2;
                        wizard.y = calcY(y) - wizard.height/2;
                        skipping = false;
                    } else if (currentHero instanceof Dwarf) {
                        int x = gameBoard.getRegion(currentHero.getPosition()).getX();
                        int y = gameBoard.getRegion(currentHero.getPosition()).getY();
                        dwarf.x = calcX(x) - dwarf.width/2;
                        dwarf.y = calcY(y) - dwarf.height/2;
                        skipping = false;
                    }
                    updateMove();

                    show();

                }
            });
            pathButtons.add(pathButton);
            stage.addActor(pathButton);
        }

        //////////////////////////


        // Add button to skip/finish turn
        if (skipping) {
            TextButton skipTurn = new TextButton("Skip Turn", parent.skin);
            skipTurn.setPosition(10, 10);
            skipTurn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    currentHero.incrementHours();
                    parent.nextTurn();
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
                    show();
                }
            });
            stage.addActor(finishTurn);

            skipping = true;
        }

        if ((currentHero.getHours() >= 7) && (currentHero.getWillPower() >= 2)) {
            TextButton finishDay = new TextButton("Finish Day", parent.skin);
            finishDay.setPosition(100, 10);
            finishDay.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Finish Day for This Hero
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




        // Buttons to drop/pickup gold
        dropGoldButton = new TextButton("Drop Gold", parent.skin);
        pickUpGoldButton = new TextButton("Pickup Gold", parent.skin);
//        if (currentHero.getGold() > 0) {
        dropGoldButton.setPosition(Gdx.graphics.getWidth() - dropGoldButton.getWidth() - 10, 10);
        dropGoldButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Perform Drop Gold
            }
        });
        stage.addActor(dropGoldButton);
//        }

//        if (gameBoard.getRegion(currentHero.getPosition()).getGold() > 0) {
        pickUpGoldButton.setPosition(Gdx.graphics.getWidth()-pickUpGoldButton.getWidth()-10, dropGoldButton.getHeight()+15);
        pickUpGoldButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Perform Pickup Gold
            }
        });
        stage.addActor(pickUpGoldButton);
//        }


        // Buttons to pickup/drop off farmer
        dropFarmer = new TextButton("Drop Off Farmer", parent.skin);
        pickUpFarmer = new TextButton("Pickup Farmer", parent.skin);
//        if () {
        dropFarmer.setPosition(Gdx.graphics.getWidth()-dropFarmer.getWidth()-110, 10);
        dropFarmer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Perform Drop Gold
            }
        });
        stage.addActor(dropFarmer);
//        }

//        if (gameBoard.getRegion(currentHero.getPosition()).getFarmers().size() > 0) {
        pickUpFarmer.setPosition(Gdx.graphics.getWidth()-pickUpFarmer.getWidth()-110, dropFarmer.getHeight()+15);
        pickUpFarmer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Perform Pickup Gold
            }
        });
        stage.addActor(pickUpFarmer);
//        }
        chat = new TextButton("Chat",parent.skin);
        chat.setPosition(Gdx.graphics.getWidth() - chat.getWidth() - 10, pickUpFarmer.getHeight()+50);
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.CHAT);
            }
        });
        stage.addActor(chat);
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
        stage.getBatch().draw(parent.andorBoard, 0, 0);
//        parent.batch.draw(playerTexture, player.x, player.y, player.width, player.height);

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

        // Draw gors
        for (Rectangle gor : gors) {
            stage.getBatch().draw(gorTexture, gor.x, gor.y, gor.width, gor.height);
        }

//        // Display hero information
//        font.getData().setScale(10.0f);
//        font.draw(stage.getBatch(), "GOLD: "+parent.whoseTurn().getGold()
//                +"\nSTRENGTH: "+parent.whoseTurn().getStrengthPoint()
//                +"\nWILLPOWER: "+parent.whoseTurn().getWillPower()
//                +"\nUSED HOURS: "+parent.whoseTurn().getHours(), calcX(370), calcY(10));



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
        chat.setPosition(Gdx.graphics.getWidth()-chat.getWidth()-10, pickUpFarmer.getHeight()+50);
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
        archerTexture.dispose();
        wizardTexture.dispose();
        dwarfTexture.dispose();
    }

    public void updateMove(){

        Hero currentHero = parent.whoseTurn();
        if( currentHero.hasMoved()){
            JSONObject data = new JSONObject();
            try{
                int x = gameBoard.getRegion(currentHero.getPosition()).getX();
                int y = gameBoard.getRegion(currentHero.getPosition()).getY();
                data.put("x",x);
                data.put("y",y);
                socket.emit("playerMoved", data);
            }catch(Exception e){
                Gdx.app.log("SocketIO", "Error moving the Player");

            }
        }
    }
    public void configSocketEvents(){
        socket.on("playerMoved", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray) args[0];
                Hero currentHero = parent.whoseTurn();

                try {
                    for(int i =0 ;i < objects.length();i++){

                        
                        String id = ((String) objects.getJSONObject(i).getString("id"));

                        float x = ((float) objects.getJSONObject(i).getInt("x"));
                        float y = ((float) objects.getJSONObject(i).getInt("y"));
                        Region from = gameBoard.getRegion(currentHero.getPosition());
                        Region to = findRegion(x,y);
                        currentHero.moveTo(from,to);


                    }





                }catch(Exception e){
                   Gdx.app.log("SocketIO", "Error handling the other player moves");
                }
            }
        });

    }
    public void connectSocket(){

        try{
            socket =IO.socket("http://localhost:8080");
            socket.connect();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public Region findRegion(float x , float y ){
        for(int i =0 ;i < availableRegions.size();i++){
            if(availableRegions.get(i).getX() == x && availableRegions.get(i).getY()== y){
                return availableRegions.get(i);
            }
        }
        System.out.println("Cannot find the new region");
        return null;
    }
}
