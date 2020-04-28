package com.mygdx.game.views;

import com.mygdx.game.Equipment.Shield;
import com.mygdx.game.Equipment.Wineskin;
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
import com.mygdx.game.views.EquipmentScreen.EquipmentScreen;

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

    private Wineskin wineskin1=new Wineskin("Wineskin1");
    private Wineskin wineskin2=new Wineskin("Wineskin2");
    private Wineskin wineskin3=new Wineskin("Wineskin3");
    private Wineskin wineskin4=new Wineskin("Wineskin4");
    private Wineskin wineskin5=new Wineskin("Wineskin5");

    private Shield shield1 = new Shield("Shield1");
    private Shield shield2 = new Shield("Shield2");
    private Shield shield3 = new Shield("Shield3");
    private Shield shield4 = new Shield("Shield4");
    private Shield shield5 = new Shield("Shield5");

//    private TextureRegion myTextureRegion;
//    private TextureRegionDrawable myTexRegionDrawable;
//    private ImageButton button;


    public MerchantScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());
        availableItems.put("SP",999);
        //availableItems.put("Wineskin",5);
        availableItems.put("Wineskin1",1);
        availableItems.put("Wineskin2",1);
        availableItems.put("Wineskin3",1);
        availableItems.put("Wineskin4",1);
        availableItems.put("Wineskin5",1);
       // availableItems.put("Shield",4);
        availableItems.put("Shield1",1);
        availableItems.put("Shield2",1);
        availableItems.put("Shield3",1);
        availableItems.put("Shield4",1);
        availableItems.put("Bow", 3);
        availableItems.put("Helm",3);
        availableItems.put("Falcon",2);
        availableItems.put("Telescope",2);
        gameBoard = parent.getGameBoard();
        //    currentHero = parent.whoseTurn();

    }


    public void setWineskinAvailable(Wineskin wineskin,int integer){
        if(wineskin.getName().equals("Wineskin1")){
            availableItems.remove("Wineskin1");
            availableItems.put("Wineskin1",integer);
        }
        if(wineskin.getName().equals("Wineskin2")){
            availableItems.remove("Wineskin2");
            availableItems.put("Wineskin2",integer);
        }
        if(wineskin.getName().equals("Wineskin3")){
            availableItems.remove("Wineskin3");
            availableItems.put("Wineskin3",integer);
        }
        if(wineskin.getName().equals("Wineskin4")){
            availableItems.remove("Wineskin4");
            availableItems.put("Wineskin4",integer);
        }
        if(wineskin.getName().equals("Wineskin5")){
            availableItems.remove("Wineskin5");
            availableItems.put("Wineskin5",integer);
        }
    }

    public int getWineskinInStock(){
        int stock=0;
        if(availableItems.get("Wineskin1")==1){
            stock++;
        }
        if(availableItems.get("Wineskin2")==1){
            stock++;
        }
        if(availableItems.get("Wineskin3")==1){
            stock++;
        }
        if(availableItems.get("Wineskin4")==1){
            stock++;
        }
        if(availableItems.get("Wineskin5")==1){
            stock++;
        }
        return stock;
    }

    public void setShieldAvailable(Shield shield,int integer){
        if(shield.getName().equals("Shield1")){
            availableItems.remove("Shield1");
            availableItems.put("Shield1",integer);
        }
        if(shield.getName().equals("Shield2")){
            availableItems.remove("Shield2");
            availableItems.put("Shield2",integer);
        }
        if(shield.getName().equals("Shield3")){
            availableItems.remove("Shield3");
            availableItems.put("Shield3",integer);
        }
        if(shield.getName().equals("Shield4")){
            availableItems.remove("Shield4");
            availableItems.put("Shield4",integer);
        }
    }

    public int getShieldInStock(){
        int stock=0;
        if(availableItems.get("Shield1")==1){
            stock++;
        }
        if(availableItems.get("Shield2")==1){
            stock++;
        }
        if(availableItems.get("Shield3")==1){
            stock++;
        }
        if(availableItems.get("Shield4")==1){
            stock++;
        }
        return stock;
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

        if (getWineskinInStock()>=1) {
            wineskinTexture = new Texture(Gdx.files.internal("items/wineskin.png"));
            Image wineskinImage = new Image(wineskinTexture);
            wineskinImage.setSize(120, 160);
            wineskinImage.setPosition(Gdx.graphics.getWidth() * 2 / 5 - wineskinImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            wineskinImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(currentHero.getGold()>=2) {



                        if(getWineskinInStock()>=1){
                            new Dialog("Would you like to purchase a Wineskin?", parent.skin) {
                                {
                                    Wineskin ws ;
                                    if(availableItems.get("Wineskin1")==1){
                                        ws=wineskin1;
                                    }
                                    else if(availableItems.get("Wineskin2")==1){
                                        ws=wineskin2;
                                    }
                                    else if(availableItems.get("Wineskin3")==1){
                                        ws=wineskin3;
                                    }
                                    else if(availableItems.get("Wineskin4")==1){
                                        ws=wineskin4;
                                    }
                                    else if(availableItems.get("Wineskin5")==1){
                                        ws=wineskin5;
                                    }
                                    else ws=null;

                                    text("Note: You will have to finish your turn if you buy from the Merchant.\n\n" +
                                            "When hero chooses 'move action', he can use each side of the \n" +
                                            "wineskin token to move 1 space without advancing 1 hour.\n" +
                                            +getWineskinInStock()+" wineskin(s) in stock\n" +
                                            +availableItems.get("Wineskin1")+ " wineskin1 in stock\n" +
                                            +availableItems.get("Wineskin2")+ " wineskin2 in stock\n" +
                                            +availableItems.get("Wineskin3")+ " wineskin3 in stock\n" +
                                            +availableItems.get("Wineskin4")+ " wineskin4 in stock\n" +
                                            +availableItems.get("Wineskin5")+ " wineskin5 in stock\n" );

                                    button("Yes", ws);
                                    button("No", ws);
                                }

                                @Override
                                protected void result(Object object) {

                                    if (object != null) {
                                        ((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellWineskin(currentHero);

                                        if(((Wineskin)object)==wineskin1){
                                            setWineskinAvailable(wineskin1,0);
                                        }
                                        else if(((Wineskin)object)==wineskin2){
                                            setWineskinAvailable(wineskin2,0);
                                        }
                                        else if(((Wineskin)object)==wineskin3){
                                            setWineskinAvailable(wineskin3,0);
                                        }
                                        else if(((Wineskin)object)==wineskin4){
                                            setWineskinAvailable(wineskin4,0);
                                        }
                                        else if(((Wineskin)object)==wineskin5){
                                            setWineskinAvailable(wineskin5,0);
                                        }


                                        if(currentHero instanceof Warrior){
                                            parent.getEquipmentScreenWarrior().setWineskinAvailable((Wineskin)object,1);
                                        }
                                        else if(currentHero instanceof Archer){
                                            parent.getEquipmentScreenArcher().setWineskinAvailable((Wineskin)object,1);
                                        }
                                        else if(currentHero instanceof Wizard){
                                            parent.getEquipmentScreenWizard().setWineskinAvailable((Wineskin)object,1);
                                        }
                                        else if(currentHero instanceof Dwarf){
                                            parent.getEquipmentScreenDwarf().setWineskinAvailable((Wineskin)object,1);
                                        }
                                        object=null;
                                        //parent.getEquipmentScreenWarrior().setWineskinAvailable(1);
                                    } else {
                                    }
                                }
                            }.show(stage);
                        }
                        else if(getWineskinInStock()==0){
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

        if (getShieldInStock()>=1) {
            shieldTexture = new Texture(Gdx.files.internal("items/shield.png"));
            Image shieldImage = new Image(shieldTexture);
            shieldImage.setSize(120, 160);
            shieldImage.setPosition(Gdx.graphics.getWidth() * 3 / 5 - shieldImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            shieldImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(currentHero.getGold()>=2) {



                        if(getShieldInStock()>=1){
                            new Dialog("Would you like to purchase a Shield?", parent.skin) {
                                {
                                    Shield sld ;
                                    if(availableItems.get("Shield1")==1){
                                        sld=shield1;
                                    }
                                    else if(availableItems.get("Shield2")==1){
                                        sld=shield2;
                                    }
                                    else if(availableItems.get("Shield3")==1){
                                        sld=shield3;
                                    }
                                    else if(availableItems.get("Shield4")==1){
                                        sld=shield4;
                                    }
                                    else sld=null;

                                    text("Note: You will have to finish your turn if you buy from the Merchant.\n\n" +
                                            "Each side of the Shield can be used once, to either \n" +
                                            "prevent loss of one's Will Power after a battle round,\n" +
                                            "or to rend off a negative event card.\n" +
                                            +getShieldInStock()+" shield(s) in stock\n" +
                                            +availableItems.get("Shield1")+" shield1 in stock.\n" +
                                            +availableItems.get("Shield2")+" shield2 in stock.\n" +
                                            +availableItems.get("Shield3")+" shield3 in stock.\n" +
                                            +availableItems.get("Shield4")+" shield4 in stock.\n" );

                                    button("Yes", sld);
                                    button("No", sld);
                                }

                                @Override
                                protected void result(Object object) {

                                    if (object != null) {
                                        ((Merchant) gameBoard.getRegion(currentHero.getPosition())).sellShield(currentHero);
                                        if((Shield)object==shield1){
                                            setShieldAvailable(shield1,0);
                                        }
                                        else if((Shield)object==shield2){
                                            setShieldAvailable(shield2,0);
                                        }
                                        else if((Shield)object==shield3){
                                            setShieldAvailable(shield3,0);
                                        }
                                        else if((Shield)object==shield4){
                                            setShieldAvailable(shield4,0);
                                        }

                                        if(currentHero instanceof Warrior){
                                            parent.getEquipmentScreenWarrior().setShieldAvailable((Shield)object,1);
                                        }
                                        else if(currentHero instanceof Archer){
                                            parent.getEquipmentScreenArcher().setShieldAvailable((Shield)object,1);
                                        }
                                        else if(currentHero instanceof Wizard){
                                            parent.getEquipmentScreenWizard().setShieldAvailable((Shield)object,1);
                                        }
                                        else if(currentHero instanceof Dwarf){
                                            parent.getEquipmentScreenDwarf().setShieldAvailable((Shield)object,1);
                                        }
                                        object=null;

                                    } else {
                                    }
                                }
                            }.show(stage);
                        }
                        else if(getShieldInStock()==0){
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
