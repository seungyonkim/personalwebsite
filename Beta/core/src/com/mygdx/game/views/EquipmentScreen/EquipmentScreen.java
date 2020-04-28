package com.mygdx.game.views.EquipmentScreen;

import com.mygdx.game.board.Board;
import com.mygdx.game.etc.Merchant;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;
import com.mygdx.game.character.Archer;
import com.mygdx.game.character.Dwarf;
import com.mygdx.game.character.Hero;
import com.mygdx.game.character.Warrior;
import com.mygdx.game.character.Wizard;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.xml.soap.Text;

public class EquipmentScreen implements Screen {

    private Andor parent;
    private Stage stage;
    private Board gameBoard;
    private Hero currentHero ;

    private HashMap<String, Integer> availableItems = new HashMap<>();

//    private Skin skin;

    private Texture wineskinTexture;
    private Texture shieldTexture;
    private Texture bowTexture;
    private Texture helmTexture;
    private Texture falconTexture;
    private Texture telescopeTexture;



    private Label titleLabel;



//    private TextureRegion myTextureRegion;
//    private TextureRegionDrawable myTexRegionDrawable;
//    private ImageButton button;


    public EquipmentScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());
        availableItems.put("Wineskin",0);
        availableItems.put("Shield",0);
        availableItems.put("Bow", 0);
        availableItems.put("Helm",0);
        availableItems.put("Falcon",0);
        availableItems.put("Telescope",0);
        gameBoard = parent.getGameBoard();
        currentHero = parent.whoseTurn();


        //currentHero = parent.whoseTurn();

    }

    private static boolean wineskinActivated=false;
    public static boolean activateWineskin(){return wineskinActivated;}
    public static void usedWineskin(){wineskinActivated=false;}


    public void setWineskinAvailable(int num){
        int currentNum = availableItems.get("Wineskin");
        availableItems.remove("Wineskin");
        availableItems.put("Wineskin",currentNum + (num*2));
//        if(bool){
//            numWineskin=2;
//        }
    }
    public void setShieldAvailable(int num){
        int currentNum = availableItems.get("Shield");
        availableItems.remove("Shield");
        availableItems.put("Shield",currentNum + (num*2));
//        if(bool){
//            numShield=2;
//        }
    }
    public void setBowAvailable(int num){
        int currentNum = availableItems.get("Bow");
        availableItems.remove("Bow");
        availableItems.put("Bow",currentNum+num);
    }
    public void setHelmAvailable(int num){
        int currentNum = availableItems.get("Helm");
        availableItems.remove("Helm");
        availableItems.put("Helm",currentNum+num);
    }
    public void setFalconAvailable(int num){
        int currentNum = availableItems.get("Falcon");
        availableItems.remove("Falcon");
        availableItems.put("Falcon",currentNum+num);
    }
    public void setTelescopeAvailable(int num){
        int currentNum = availableItems.get("Telescope");
        availableItems.remove("Telescope");
        availableItems.put("Telescope",currentNum+num);
    }


    @Override
    public void show()
    {
        stage.clear();

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
//        stage.addActor(table);


//        titleLabel = new Label( "Choose Heroes upto "+parent.getNumOfPlayers(), parent.skin);
        titleLabel = new Label( "Available Equipments:", parent.skin);
        TextButton backButton = new TextButton("Back to Game", parent.skin);


        table.add(titleLabel).colspan(6);
        table.row().pad(50, 0, 0, 0);




        if (availableItems.get("Wineskin")>=1) {
            wineskinTexture = new Texture(Gdx.files.internal("items/wineskin.png"));
            Image wineskinImage = new Image(wineskinTexture);
            wineskinImage.setSize(120, 160);
            wineskinImage.setPosition(Gdx.graphics.getWidth() * 2 / 5 - wineskinImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            wineskinImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(availableItems.get("Wineskin")>=1) {

                        new Dialog("Would you like to activate Wineskin?", parent.skin) {
                            {
                                text(availableItems.get("Wineskin") + " Wineskin(s) available.\n\n" +
                                        "By activating a wineskin," +
                                        "When hero chooses 'move action', he can use the \n" +
                                        "wineskin token to move 1 space without advancing 1 hour.");

                                button("Yes", "true");
                                button("No", "false");
                            }

                            @Override
                            protected void result(Object object) {

                                if (object == "true") {
                                    //activate wineskin
                                    //currentHero.activateEquipment(null);
                                    wineskinActivated=true;
                                    System.out.println("activate wineskin from hero's equipment bag");
                                    int numWineskin = availableItems.get("Wineskin");
                                    availableItems.remove("Wineskin");
                                    availableItems.put("Wineskin", numWineskin - 1);
                                    if(availableItems.get("Wineskin")%2==0){
                                        parent.getMerchantScreen().setWineskinAvailable(1);
                                    }


                                } else {
                                }
                            }
                        }.show(stage);

                    }
                    else if (availableItems.get("Wineskin")==0){
                        new Dialog("You have used all of your Wineskins.", parent.skin) {
                            {
                                text("No more wineskin available.\n\n" +
                                        "Please go to the merchant store to purchase more items.\n");

                                button("Dismiss", "true");
                            }

                            @Override
                            protected void result(Object object) {

                            }
                        }.show(stage);
                    }



                }
            });
            table.add(wineskinImage).width(90).height(130).padRight(10);
        }

        if (availableItems.get("Shield")>=1) {
            shieldTexture = new Texture(Gdx.files.internal("items/shield.png"));
            Image shieldImage = new Image(shieldTexture);
            shieldImage.setSize(120, 160);
            shieldImage.setPosition(Gdx.graphics.getWidth() * 3 / 5 - shieldImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            shieldImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(availableItems.get("Shield")>=1) {

                        new Dialog("Would you like to activate Shield?", parent.skin) {
                            {
                                text(availableItems.get("Shield")+" Shield(s) available.\n\n"+
                                        "By activating a shield, a hero can either \n" +
                                        "prevent loss of one's Will Power after a battle round,\n" +
                                        "or rend off a negative event card.");

                                button("Yes", "true");
                                button("No", "false");
                            }

                            @Override
                            protected void result(Object object) {

                                if (object == "true") {
                                    currentHero.activateEquipment(null);
                                    int numShield = availableItems.get("Shield");
                                    availableItems.remove("Shield");
                                    availableItems.put("Shield",numShield-1);

                                    if(availableItems.get("Shield")%2==0){
                                        parent.getMerchantScreen().setShieldAvailable(1);
                                    }

                                } else {
                                }
                            }
                        }.show(stage);
                    }
                    if (availableItems.get("Shield")==0){
                        new Dialog("You have used all of your Shields.", parent.skin) {
                            {
                                text("No more shield available.\n\n" +
                                        "Please go to the merchant store to purchase more items.\n");

                                button("Dismiss", "true");
                            }

                            @Override
                            protected void result(Object object) {

                            }
                        }.show(stage);
                    }

                }
            });
            table.add(shieldImage).width(90).height(130).padRight(10);
        }

        if (availableItems.get("Bow")>=1) {
            bowTexture = new Texture(Gdx.files.internal("items/bow.png"));
            Image bowImage = new Image(bowTexture);
            bowImage.setSize(120, 160);
            bowImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - bowImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            bowImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(availableItems.get("Bow")>=1) {
                        new Dialog("Would you like to activate Bow?", parent.skin) {
                            {
                                text("By activating bow, \n" +
                                        "a hero attack a creature when on an adjacent space.\n" +
                                        "Moreover, the Hero must roll his dice one at a time \n" +
                                        "and decide when to stop rolling. Only the last roll counts.");

                                button("Yes", "true");
                                button("No", "false");
                            }

                            @Override
                            protected void result(Object object) {

                                if (object == "true") {
                                    currentHero.activateEquipment(null);
                                    //setBowAvailable(false);
                                    int numBow = availableItems.get("Bow");
                                    availableItems.remove("Bow");
                                    availableItems.put("Bow",numBow-1);

                                    parent.getMerchantScreen().setBowAvailable(1);

                                } else {
                                }
                            }
                        }.show(stage);

                    }
                    else if(availableItems.get("Bow")==0){
                        new Dialog("You have already activated your Bow.", parent.skin) {
                            {
                                text("No more bow available.\n\n" +
                                        "Please go to the merchant store to purchase more items.\n");

                                button("Dismiss", "true");
                            }

                            @Override
                            protected void result(Object object) {

                            }
                        }.show(stage);
                    }

                }
            });
            table.add(bowImage).width(90).height(130).padRight(10);
        }

        if (availableItems.get("Helm")>=1) {
            helmTexture = new Texture(Gdx.files.internal("items/helm.png"));
            Image helmImage = new Image(helmTexture);
            helmImage.setSize(120, 160);
            helmImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - helmImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            helmImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(availableItems.get("Helm")>=1) {

                        new Dialog("Would you like to activate Helm?", parent.skin) {
                            {
                                text("By activating helm,\n" +
                                        "identical dice values are totaled up in a battle\n" +
                                        "with the help of a Helm.");

                                button("Yes", "true");
                                button("No", "false");
                            }

                            @Override
                            protected void result(Object object) {

                                if (object == "true") {
                                    currentHero.activateEquipment(null);
                                    int numHelm = availableItems.get("Helm");
                                    availableItems.remove("Helm");
                                    availableItems.put("Helm",numHelm-1);
                                    parent.getMerchantScreen().setHelmAvailable(1);

                                } else {
                                }
                            }
                        }.show(stage);

                    }
                    else if(availableItems.get("Helm")==0){
                        new Dialog("You have already activated your Helm.", parent.skin) {
                            {
                                text("No more helm available.\n\n" +
                                        "Please go to the merchant store to purchase more items.\n");

                                button("Dismiss", "true");
                            }

                            @Override
                            protected void result(Object object) {

                            }
                        }.show(stage);
                    }

                }
            });
            table.add(helmImage).width(90).height(130).padRight(10);
        }

        if (availableItems.get("Falcon")>=1) {
            falconTexture = new Texture(Gdx.files.internal("items/falcon.png"));
            Image falconImage = new Image(falconTexture);
            falconImage.setSize(120, 160);
            falconImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - falconImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            falconImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(availableItems.get("Falcon")>=1) {
                        new Dialog("Would you like to activate Falcon?", parent.skin) {
                            {
                                text("By activating falcon,\n" +
                                        "two Heros can trade any quantity of small articles, gold , or gemstones\n" +
                                        "even if they're not standing at the same region.\n" +
                                        "Note: a Falcon can only be used once a day.");

                                button("Yes", "true");
                                button("No", "false");
                            }

                            @Override
                            protected void result(Object object) {

                                if (object == "true") {
                                    currentHero.activateEquipment(null);
                                    int numFalcon = availableItems.get("Falcon");
                                    availableItems.remove("Falcon");
                                    availableItems.put("Falcon",numFalcon-1);

                                    parent.getMerchantScreen().setFalconAvailable(1);

                                } else {
                                }
                            }
                        }.show(stage);

                    }
                    else if(availableItems.get("Falcon")==0){
                        new Dialog("You have already activated your Falcon.", parent.skin) {
                            {
                                text("No more falcon available.\n\n" +
                                        "Please go to the merchant store to purchase more items.\n");

                                button("Dismiss", "true");
                            }

                            @Override
                            protected void result(Object object) {

                            }
                        }.show(stage);
                    }

                }
            });
            table.add(falconImage).width(90).height(130).padRight(10);
        }

        if (availableItems.get("Telescope")>=1) {
            telescopeTexture = new Texture(Gdx.files.internal("items/telescope.png"));
            Image telescopeImage = new Image(telescopeTexture);
            telescopeImage.setSize(120, 160);
            telescopeImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - telescopeImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            telescopeImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(availableItems.get("Telescope")>=1) {
                        new Dialog("Would you like to activate Telescope?", parent.skin) {
                            {
                                text("Description: You will have to finish your turn if you buy from the Merchant.\n\n" +
                                        "A Hero may uncover all tokens on adjacent spaces, \n" +
                                        "but not when just passing through. \n" +
                                        "This does not activate the tokens, however.");

                                button("Yes", "true");
                                button("No", "false");
                            }

                            @Override
                            protected void result(Object object) {

                                if (object == "true") {
                                    currentHero.activateEquipment(null);
                                    int numTelescope = availableItems.get("Telescope");
                                    availableItems.remove("Telescope");
                                    availableItems.put("Telescope",numTelescope-1);

                                    parent.getMerchantScreen().setTelescopeAvailable(1);

                                } else {
                                }
                            }
                        }.show(stage);
                    }
                    else if(availableItems.get("Telescope")==0){
                        new Dialog("You have already activated your Telescope.", parent.skin) {
                            {
                                text("No more telescope available.\n\n" +
                                        "Please go to the merchant store to purchase more items.\n");

                                button("Dismiss", "true");
                            }

                            @Override
                            protected void result(Object object) {

                            }
                        }.show(stage);
                    }

                }
            });
            table.add(telescopeImage).width(90).height(130).padRight(10);
        }

        table.row().pad(30, 0, 0, 0);
        table.add(backButton).colspan(7);

        stage.addActor(table);


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.MULTIGAME);
            }
        });

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));

        stage.getBatch().begin();
        stage.getBatch().setColor(Color.WHITE); // to prevent the flickering with the dialog
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


}