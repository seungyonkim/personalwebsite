package com.mygdx.game.views;

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


public class MerchantScreen implements Screen {

    private Andor parent;
    private Stage stage;
    private Board gameBoard;
    private Hero currentHero ;

    private HashMap<String, Integer> availableItems = new HashMap<>();

//    private Skin skin;

    private Texture spTexture;
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


    public MerchantScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());
        availableItems.put("SP",999);
        availableItems.put("Wineskin",5);
        availableItems.put("Shield",4);
        availableItems.put("Bow", 3);
        availableItems.put("Helm",3);
        availableItems.put("Falcon",2);
        availableItems.put("Telescope",2);
        gameBoard = parent.getGameBoard();
        //    currentHero = parent.whoseTurn();

    }


    public void setWineskinAvailable(int num){
        int currentNum = availableItems.get("Wineskin");
        availableItems.remove("Wineskin");
        availableItems.put("Wineskin",currentNum+num);
    }
    public void setShieldAvailable(int num){
        int currentNum = availableItems.get("Shield");
        availableItems.remove("Shield");
        availableItems.put("Shield",currentNum+num);
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
       // currentHero = parent.whoseTurn();
        currentHero = parent.getMyHero();

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
//        stage.addActor(table);

//        titleLabel = new Label( "Choose Heroes upto "+parent.getNumOfPlayers(), parent.skin);
        titleLabel = new Label( "Available Items:", parent.skin);
        TextButton backButton = new TextButton("Back to Game", parent.skin);


        table.add(titleLabel).colspan(7);
        table.row().pad(50, 0, 0, 0);


        if (availableItems.get("SP")>=1) {
            spTexture = new Texture(Gdx.files.internal("items/strengthpoints.png"));
            Image spImage = new Image(spTexture);
            spImage.setSize(120, 160);
            spImage.setPosition(Gdx.graphics.getWidth()/5 - spImage.getWidth()/2, Gdx.graphics.getHeight()/4);
            spImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(currentHero.getGold()>=2) {
                        new Dialog("Would you like to purchase 1 Strength Power?", parent.skin) {
                            {
                                text("Note: You will have to finish your turn if you buy from the Merchant.\n\n" +
                                        "With each strength point, ");

                                button("Yes", "true");
                                button("No", "false");
                            }

                            @Override
                            protected void result(Object object) {

                                if (object == "true") {
                                    ((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellSP(currentHero);

                                    // setSPAvailable(false);

                                } else {

                                }
                            }
                        }.show(stage);
                    }
                    else{

                        new Dialog("No more gold", parent.skin) {
                            {
                                text("You do not have enough gold.\n" +
                                        "Please visit again at another time!");

                                button("OK", "true");
                            }

                            @Override
                            protected void result(Object object) {

                                parent.changeScreen(Andor.MULTIGAME);

                            }
                        }.show(stage);

                    }




                }
            });
            table.add(spImage).width(90).height(130).padRight(10);
        }

        if (availableItems.get("Wineskin")>=1) {
            wineskinTexture = new Texture(Gdx.files.internal("items/wineskin.png"));
            Image wineskinImage = new Image(wineskinTexture);
            wineskinImage.setSize(120, 160);
            wineskinImage.setPosition(Gdx.graphics.getWidth() * 2 / 5 - wineskinImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            wineskinImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(currentHero.getGold()>=2) {
                        if(availableItems.get("Wineskin")>=1){
                            new Dialog("Would you like to purchase a Wineskin?", parent.skin) {
                                {
                                    text("Note: You will have to finish your turn if you buy from the Merchant.\n\n" +
                                            "When hero chooses 'move action', he can use each side of the \n" +
                                            "wineskin token to move 1 space without advancing 1 hour.\n" +
                                            +availableItems.get("Wineskin")+ " wineskins in stock");

                                    button("Yes", "true");
                                    button("No", "false");
                                }

                                @Override
                                protected void result(Object object) {

                                    if (object == "true") {
                                        ((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellWineskin(currentHero);


                                        int num = availableItems.get("Wineskin");
                                        availableItems.remove("Wineskin");
                                        availableItems.put("Wineskin",num-1);

                                        if(currentHero instanceof Warrior){
                                            parent.getEquipmentScreenWarrior().setWineskinAvailable(1);
                                        }
                                        else if(currentHero instanceof Archer){
                                            parent.getEquipmentScreenArcher().setWineskinAvailable(1);
                                        }
                                        else if(currentHero instanceof Wizard){
                                            parent.getEquipmentScreenWizard().setWineskinAvailable(1);
                                        }
                                        else if(currentHero instanceof Dwarf){
                                            parent.getEquipmentScreenDwarf().setWineskinAvailable(1);
                                        }

                                        //parent.getEquipmentScreenWarrior().setWineskinAvailable(1);
                                    } else {
                                    }
                                }
                            }.show(stage);
                        }
                        else if(availableItems.get("Wineskin")==0){
                            new Dialog("No more wineskin", parent.skin) {
                                {
                                    text("Wineskin out of stock.\n" +
                                            "Please visit again at another time!");

                                    button("OK", "true");
                                }

                                @Override
                                protected void result(Object object) {

                                }
                            }.show(stage);
                        }


                    }
                    else{
                        if(currentHero.getGold()==0){
                            new Dialog("No more gold", parent.skin) {
                                {
                                    text("You do not have enough gold.\n" +
                                            "Please visit again at another time!");

                                    button("OK", "true");
                                }

                                @Override
                                protected void result(Object object) {

                                    parent.changeScreen(Andor.MULTIGAME);
                                }
                            }.show(stage);
                        }



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

                    if(currentHero.getGold()>=2) {

                        if(availableItems.get("Shield")>=1){
                            new Dialog("Would you like to purchase a Shield?", parent.skin) {
                                {
                                    text("Note: You will have to finish your turn if you buy from the Merchant.\n\n" +
                                            "Each side of the Shield can be used once, to either \n" +
                                            "prevent loss of one's Will Power after a battle round,\n" +
                                            "or to rend off a negative event card.\n" +
                                            +availableItems.get("Shield")+" shields in stock.");

                                    button("Yes", "true");
                                    button("No", "false");
                                }

                                @Override
                                protected void result(Object object) {

                                    if (object == "true") {
                                        ((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellShield(currentHero);
                                        int num = availableItems.get("Shield");
                                        availableItems.remove("Shield");
                                        availableItems.put("Shield",num-1);

                                        if(currentHero instanceof Warrior){
                                            parent.getEquipmentScreenWarrior().setShieldAvailable(1);
                                        }
                                        else if(currentHero instanceof Archer){
                                            parent.getEquipmentScreenArcher().setShieldAvailable(1);
                                        }
                                        else if(currentHero instanceof Wizard){
                                            parent.getEquipmentScreenWizard().setShieldAvailable(1);
                                        }
                                        else if(currentHero instanceof Dwarf){
                                            parent.getEquipmentScreenDwarf().setShieldAvailable(1);
                                        }


                                    } else {
                                    }
                                }
                            }.show(stage);
                        }
                        else if(availableItems.get("Sheild")==0){
                            new Dialog("No more shield", parent.skin) {
                                {
                                    text("Shield out of stock.\n" +
                                            "Please visit again at another time!");

                                    button("OK", "true");
                                }

                                @Override
                                protected void result(Object object) {

                                }
                            }.show(stage);
                        }
                    }
                    else{
                        new Dialog("No more gold", parent.skin) {
                            {
                                text("You do not have enough gold.\n" +
                                        "Please visit again at another time!");

                                button("OK", "true");
                            }

                            @Override
                            protected void result(Object object) {

                                parent.changeScreen(Andor.MULTIGAME);
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

                    if(currentHero.getGold()>=2) {
                        if(availableItems.get("Bow")>=1){
                            new Dialog("Would you like to purchase a Bow?", parent.skin) {
                                {
                                    text("Note: You will have to finish your turn if you buy from the Merchant.\n\n" +
                                            "A Hero with a bow can attack a creature when on an adjacent space.\n" +
                                            "Moreover, the Hero must roll his dice one at a time \n" +
                                            "and decide when to stop rolling. Only the last roll counts.\n" +
                                            +availableItems.get("Bow")+ " bows in stock");

                                    button("Yes", "true");
                                    button("No", "false");
                                }

                                @Override
                                protected void result(Object object) {

                                    if (object == "true") {
                                        ((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellBow(currentHero);
                                        int num = availableItems.get("Bow");
                                        availableItems.remove("Bow");
                                        availableItems.put("Bow",num-1);

                                        if(currentHero instanceof Warrior){
                                            parent.getEquipmentScreenWarrior().setBowAvailable(1);
                                        }
                                        else if(currentHero instanceof Archer){
                                            parent.getEquipmentScreenArcher().setBowAvailable(1);
                                        }
                                        else if(currentHero instanceof Wizard){
                                            parent.getEquipmentScreenWizard().setBowAvailable(1);
                                        }
                                        else if(currentHero instanceof Dwarf){
                                            parent.getEquipmentScreenDwarf().setBowAvailable(1);
                                        }

                                    } else {
                                    }
                                }
                            }.show(stage);
                        }
                        else if(availableItems.get("Bow")==0){
                            new Dialog("No more bow", parent.skin) {
                                {
                                    text("Bow out of stock.\n" +
                                            "Please visit again at another time!");

                                    button("OK", "true");
                                }

                                @Override
                                protected void result(Object object) {

                                }
                            }.show(stage);
                        }

                    }
                    else{
                        new Dialog("No more gold", parent.skin) {
                            {
                                text("You do not have enough gold.\n" +
                                        "Please visit again at another time!");

                                button("OK", "true");
                            }

                            @Override
                            protected void result(Object object) {

                                parent.changeScreen(Andor.MULTIGAME);
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

                    if(currentHero.getGold()>=2) {
                        if(availableItems.get("Helm")>=1){
                            new Dialog("Would you like to purchase a Helm?", parent.skin) {
                                {
                                    text("Note: You will have to finish your turn if you buy from the Merchant.\n\n" +
                                            "Identical dice values are totaled up in a battle\n" +
                                            "with the help of a Helm.\n" +
                                            +availableItems.get("Helm")+" helms in stock");

                                    button("Yes", "true");
                                    button("No", "false");
                                }

                                @Override
                                protected void result(Object object) {

                                    if (object == "true") {
                                        ((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellHelm(currentHero);
                                        int num = availableItems.get("Helm");
                                        availableItems.remove("Helm");
                                        availableItems.put("Helm",num-1);

                                        if(currentHero instanceof Warrior){
                                            parent.getEquipmentScreenWarrior().setHelmAvailable(1);
                                        }
                                        else if(currentHero instanceof Archer){
                                            parent.getEquipmentScreenArcher().setHelmAvailable(1);
                                        }
                                        else if(currentHero instanceof Wizard){
                                            parent.getEquipmentScreenWizard().setHelmAvailable(1);
                                        }
                                        else if(currentHero instanceof Dwarf){
                                            parent.getEquipmentScreenDwarf().setHelmAvailable(1);
                                        }


                                    } else {
                                    }
                                }
                            }.show(stage);
                        }
                        else if(availableItems.get("Helm")==0){
                            new Dialog("No more helm", parent.skin) {
                                {
                                    text("Helm out of stock.\n" +
                                            "Please visit again at another time!");

                                    button("OK", "true");
                                }

                                @Override
                                protected void result(Object object) {

                                }
                            }.show(stage);
                        }

                    }
                    else{
                        new Dialog("No more gold", parent.skin) {
                            {
                                text("You do not have enough gold.\n" +
                                        "Please visit again at another time!");

                                button("OK", "true");
                            }

                            @Override
                            protected void result(Object object) {

                                parent.changeScreen(Andor.MULTIGAME);
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
                    if(currentHero.getGold()>=2) {
                        if(availableItems.get("Falcon")>=1){
                            new Dialog("Would you like to purchase a Falcon?", parent.skin) {
                                {
                                    text("Note: You will have to finish your turn if you buy from the Merchant\n\n" +
                                            "Two Heros can trade any quantity of small articles, gold , or gemstones\n" +
                                            "even if they're not standing at the same region.\n" +
                                            "A Falcon can only be used once a day.\n" +
                                            + availableItems.get("Falcon") + " falcons in stock");

                                    button("Yes", "true");
                                    button("No", "false");
                                }

                                @Override
                                protected void result(Object object) {

                                    if (object == "true") {
                                        ((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellFalcon(currentHero);
                                        int num = availableItems.get("Falcon");
                                        availableItems.remove("Falcon");
                                        availableItems.put("Falcon",num-1);

                                        if(currentHero instanceof Warrior){
                                            parent.getEquipmentScreenWarrior().setFalconAvailable(1);
                                        }
                                        else if(currentHero instanceof Archer){
                                            parent.getEquipmentScreenArcher().setFalconAvailable(1);
                                        }
                                        else if(currentHero instanceof Wizard){
                                            parent.getEquipmentScreenWizard().setFalconAvailable(1);
                                        }
                                        else if(currentHero instanceof Dwarf){
                                            parent.getEquipmentScreenDwarf().setFalconAvailable(1);
                                        }

                                    } else {
                                    }
                                }
                            }.show(stage);
                        }
                        else if(availableItems.get("Falcon")==0){
                            new Dialog("No more falcon", parent.skin) {
                                {
                                    text("Falcon out of stock.\n" +
                                            "Please visit again at another time!");

                                    button("OK", "true");
                                }

                                @Override
                                protected void result(Object object) {

                                }
                            }.show(stage);
                        }


                    }
                    else{
                        new Dialog("No more gold", parent.skin) {
                            {
                                text("You do not have enough gold.\n" +
                                        "Please visit again at another time!");

                                button("OK", "true");
                            }

                            @Override
                            protected void result(Object object) {

                                parent.changeScreen(Andor.MULTIGAME);
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
                    if(currentHero.getGold()>=2) {
                        if(availableItems.get("Telescope")>=1){
                            new Dialog("Would you like to purchase a Telescope?", parent.skin) {
                                {
                                    text("Note: You will have to finish your turn if you buy from the Merchant.\n\n" +
                                            "A Hero may uncover all tokens on adjacent spaces, \n" +
                                            "but not when just passing through. \n" +
                                            "This does not activate the tokens, however.\n" +
                                            + availableItems.get("Telescope") + " telescopes in stock.");

                                    button("Yes", "true");
                                    button("No", "false");
                                }

                                @Override
                                protected void result(Object object) {

                                    if (object == "true") {
                                        ((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellTelescope(currentHero);
                                        int num = availableItems.get("Telescope");
                                        availableItems.remove("Telescope");
                                        availableItems.put("Telescope",num-1);

                                        if(currentHero instanceof Warrior){
                                            parent.getEquipmentScreenWarrior().setTelescopeAvailable(1);
                                        }
                                        else if(currentHero instanceof Archer){
                                            parent.getEquipmentScreenArcher().setTelescopeAvailable(1);
                                        }
                                        else if(currentHero instanceof Wizard){
                                            parent.getEquipmentScreenWizard().setTelescopeAvailable(1);
                                        }
                                        else if(currentHero instanceof Dwarf){
                                            parent.getEquipmentScreenDwarf().setTelescopeAvailable(1);
                                        }


                                    } else {
                                    }
                                }
                            }.show(stage);
                        }
                        else if(availableItems.get("Telescope")==0){
                            new Dialog("No more telescope", parent.skin) {
                                {
                                    text("Telescope out of stock.\n" +
                                            "Please visit again at another time!");

                                    button("OK", "true");
                                }

                                @Override
                                protected void result(Object object) {

                                }
                            }.show(stage);
                        }

                    }
                    else{
                        new Dialog("No more gold", parent.skin) {
                            {
                                text("You do not have enough gold.\n" +
                                        "Please visit again at another time!");

                                button("OK", "true");
                            }

                            @Override
                            protected void result(Object object) {

                                parent.changeScreen(Andor.MULTIGAME);
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
