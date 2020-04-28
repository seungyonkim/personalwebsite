package com.mygdx.game.views.EquipmentScreen;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.soap.Text;

public class EquipmentScreen implements Screen {

    private Andor parent;
    private Stage stage;
    private Board gameBoard;
    private Hero currentHero ;

    private HashMap<String, Integer> availableItems = new HashMap<>();

//    private Skin skin;

    private Texture wineskinTexture = new Texture(Gdx.files.internal("items/wineskin.png"));;
    private Texture shieldTexture = new Texture(Gdx.files.internal("items/shield.png"));
    private Texture bowTexture = new Texture(Gdx.files.internal("items/bow.png"));
    private Texture helmTexture = new Texture(Gdx.files.internal("items/helm.png"));
    private Texture falconTexture = new Texture(Gdx.files.internal("items/falcon.png"));
    private Texture telescopeTexture = new Texture(Gdx.files.internal("items/telescope.png"));


    private Wineskin wineskin1=null;
    private Wineskin wineskin2=null;
    private Wineskin wineskin3=null;
    private Wineskin wineskin4=null;
    private Wineskin wineskin5=null;

    private Shield shield1 = null;
    private Shield shield2 = null;
    private Shield shield3 = null;
    private Shield shield4 = null;



    private Label titleLabel;

    public static boolean usedFalconToday=false;
    public static void setUsedFalconToday(boolean bool){
        usedFalconToday=bool;
    }



//    private TextureRegion myTextureRegion;
//    private TextureRegionDrawable myTexRegionDrawable;
//    private ImageButton button;


    public EquipmentScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());
        //availableItems.put("Wineskin",5);
        availableItems.put("Wineskin1",0);
        availableItems.put("Wineskin2",0);
        availableItems.put("Wineskin3",0);
        availableItems.put("Wineskin4",0);
        availableItems.put("Wineskin5",0);
        // availableItems.put("Shield",4);
        availableItems.put("Shield1",0);
        availableItems.put("Shield2",0);
        availableItems.put("Shield3",0);
        availableItems.put("Shield4",0);
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




    public void setWineskinAvailable(Wineskin wineskin,int integer){

        if(wineskin.getName().equals("Wineskin1")){
            wineskin1=wineskin;
            availableItems.remove("Wineskin1");
            availableItems.put("Wineskin1",integer);
            System.out.println("W1 added to equipment bag");
        }
        if(wineskin.getName().equals("Wineskin2")){
            wineskin2=wineskin;
            availableItems.remove("Wineskin2");
            availableItems.put("Wineskin2",integer);
            System.out.println("W2 added to equipment bag");
        }
        if(wineskin.getName().equals("Wineskin3")){
            wineskin3=wineskin;
            availableItems.remove("Wineskin3");
            availableItems.put("Wineskin3",integer);
            System.out.println("W3 added to equipment bag");
        }
        if(wineskin.getName().equals("Wineskin4")){
            wineskin4=wineskin;
            availableItems.remove("Wineskin4");
            availableItems.put("Wineskin4",integer);
            System.out.println("W4 added to equipment bag");
        }
        if(wineskin.getName().equals("Wineskin5")){
            wineskin5=wineskin;
            availableItems.remove("Wineskin5");
            availableItems.put("Wineskin5",integer);
            System.out.println("W5 added to equipment bag");
        }

        System.out.println("how many wineskin: "+getWineskinInStock());
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
            shield1=shield;
            availableItems.remove("Shield1");
            availableItems.put("Shield1",integer);
        }
        if(shield.getName().equals("Shield2")){
            shield2=shield;
            availableItems.remove("Shield2");
            availableItems.put("Shield2",integer);
        }
        if(shield.getName().equals("Shield3")){
            shield3=shield;
            availableItems.remove("Shield3");
            availableItems.put("Shield3",integer);
        }
        if(shield.getName().equals("Shield4")){
            shield4=shield;
            availableItems.remove("Shield4");
            availableItems.put("Shield4",integer);
        }
        System.out.println("how many shield : "+getShieldInStock());
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





        if (getWineskinInStock()>=1) {

            Image wineskinImage = new Image(wineskinTexture);
            wineskinImage.setSize(120, 160);
            wineskinImage.setPosition(Gdx.graphics.getWidth() * 2 / 5 - wineskinImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            wineskinImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(getWineskinInStock()>=1) {

                        new Dialog("Would you like to activate Wineskin?", parent.skin) {

                            {
                                Wineskin ws ;
                                String str="";
                                if(availableItems.get("Wineskin1")==1){
                                    if(wineskin1.getUsedOnce()){
                                        str=str+"Wineskin1: 1/2 used";
                                    }
                                    else if(!wineskin1.getUsedOnce()){
                                        str=str+"Wineskin1: 0/2 used";
                                    }
                                }
                                if(availableItems.get("Wineskin2")==1){
                                    if(wineskin2.getUsedOnce()){
                                        str=str+"Wineskin2: 1/2 used";
                                    }
                                    else if(!wineskin2.getUsedOnce()){
                                        str=str+"Wineskin2: 0/2 used";
                                    }
                                }
                                if(availableItems.get("Wineskin3")==1){
                                    if(wineskin3.getUsedOnce()){
                                        str=str+"Wineskin3: 1/2 used";
                                    }
                                    else if(!wineskin3.getUsedOnce()){
                                        str=str+"Wineskin3: 0/2 used";
                                    }
                                }
                                if(availableItems.get("Wineskin4")==1){
                                    if(wineskin4.getUsedOnce()){
                                        str=str+"Wineskin4: 1/2 used";
                                    }
                                    else if(!wineskin4.getUsedOnce()){
                                        str=str+"Wineskin4: 0/2 used";
                                    }
                                }
                                if(availableItems.get("Wineskin5")==1){
                                    if(wineskin5.getUsedOnce()){
                                        str=str+"Wineskin5: 1/2 used";
                                    }
                                    else if(!wineskin5.getUsedOnce()){
                                        str=str+"Wineskin5: 0/2 used";
                                    }
                                }


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
                                System.out.println(str);

                                text(str +
                                        "\nBy activating a wineskin," +
                                        "When hero chooses 'move action', he can use the \n" +
                                        "wineskin token to move 1 space without advancing 1 hour.");

                                button("Yes", ws);
                                button("No", null);
                            }

                            @Override
                            protected void result(Object object) {

                                if (object !=null) {
                                    //activate wineskin
                                    //currentHero.activateEquipment(null);
                                    wineskinActivated=true;
                                    System.out.println("activate wineskin from hero's equipment bag");


                                    if(((Wineskin)object).getUsedOnce()){
                                        availableItems.remove(((Wineskin)object).getName());
                                        availableItems.put(((Wineskin)object).getName(), 0);

                                        ((Wineskin)object).setUsedOnce(false);
                                        parent.getMerchantScreen().setWineskinAvailable(((Wineskin)object),1);

                                    }
                                    else if(!((Wineskin)object).getUsedOnce()){
                                        ((Wineskin)object).setUsedOnce(true);
                                    }

                                    object=null;


                                } else {
                                }
                            }
                        }.show(stage);

                    }
                    else if (getWineskinInStock()==0){
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



        if (getShieldInStock()>=1) {

            Image shieldImage = new Image(shieldTexture);
            shieldImage.setSize(120, 160);
            shieldImage.setPosition(Gdx.graphics.getWidth() * 3 / 5 - shieldImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            shieldImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(getShieldInStock()>=1) {

                        new Dialog("Would you like to activate Shield?", parent.skin) {
                            {
                                Shield sld ;
                                String str="";


                                if(availableItems.get("Shield1")==1){
                                    if(shield1.getUsedOnce()){
                                        str=str+"Shield1: 1/2 used\n";
                                    }
                                    else if(!shield1.getUsedOnce()){
                                        str=str+"Shield1: 0/2 used\n";
                                    }
                                }
                                if(availableItems.get("Shield2")==1){
                                    if(shield2.getUsedOnce()){
                                        str=str+"Shield2: 1/2 used\n";
                                    }
                                    else if(!shield2.getUsedOnce()){
                                        str=str+"Shield2: 0/2 used\n";
                                    }
                                }
                                if(availableItems.get("Shield3")==1){
                                    if(shield3.getUsedOnce()){
                                        str=str+"Shield3: 1/2 used\n";
                                    }
                                    else if(!shield3.getUsedOnce()){
                                        str=str+"Shield3: 0/2 used\n";
                                    }
                                }
                                if(availableItems.get("Shield4")==1){
                                    if(shield4.getUsedOnce()){
                                        str=str+"Shield4: 1/2 used\n";
                                    }
                                    else if(!shield4.getUsedOnce()){
                                        str=str+"Shield4: 0/2 used\n";
                                    }
                                }


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

                                text(str+
                                        "\nBy activating a shield, a hero can either \n" +
                                        "prevent loss of one's Will Power after a battle round,\n" +
                                        "or rend off a negative event card.");

                                button("Yes", sld);
                                button("No", null);
                            }

                            @Override
                            protected void result(Object object) {

                                if (object !=null) {
                                    //currentHero.activateEquipment(null);

                                    if(((Shield)object).getUsedOnce()){
                                        availableItems.remove(((Shield)object).getName());
                                        availableItems.put(((Shield)object).getName(), 0);

                                        ((Shield)object).setUsedOnce(false);
                                        parent.getMerchantScreen().setShieldAvailable(((Shield)object),1);

                                    }
                                    else if(!((Shield)object).getUsedOnce()){
                                        ((Shield)object).setUsedOnce(true);
                                    }
                                    object=null;


                                } else {
                                }
                            }
                        }.show(stage);
                    }
                    else if (getShieldInStock()==0){
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

        if (availableItems.get("Falcon")>=1&&!usedFalconToday) {

            Image falconImage = new Image(falconTexture);
            falconImage.setSize(120, 160);
            falconImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - falconImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            falconImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(availableItems.get("Falcon")>=1&&!usedFalconToday) {
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
                                    //currentHero.activateEquipment(null);
                                    usedFalconToday=true;
                                    parent.getUseFalconScreen().setWineskinStock(wineskin1,wineskin2,wineskin3,wineskin4,wineskin5);
                                    parent.getUseFalconScreen().setShieldStock(shield1,shield2,shield3,shield4);
                                    parent.getUseFalconScreen().setAvailableItems(
                                            getWineskinInStock(),
                                            getShieldInStock(),
                                            availableItems.get("Bow"),
                                            availableItems.get("Helm"),
                                            availableItems.get("Falcon"),
                                            availableItems.get("Telescope"));
                                    parent.changeScreen(Andor.USE_FALCON);

                                    //int numFalcon = availableItems.get("Falcon");
                                   // availableItems.remove("Falcon");
                                   // availableItems.put("Falcon",numFalcon-1);

                                    //parent.getMerchantScreen().setFalconAvailable(1);

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

        table.row().pad(30, 0, 0, 0);
        TextButton equipmentbagArcher = new TextButton("Archer Equipemtn Page",parent.skin);
        table.add(equipmentbagArcher).colspan(9);

        stage.addActor(table);


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.MULTIGAME);
            }
        });

        equipmentbagArcher.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                parent.changeScreen(Andor.EQUIPMENT_ARCHER);
            }
        });




    }
    private Label titleLabel2;
    private SelectBox<String> toWhom;

    private SelectBox<Integer> wineskinBox;
    private SelectBox<Integer> shieldBox;
    private SelectBox<Integer> helmBox;
    private SelectBox<Integer> bowBox;
    private SelectBox<Integer> falconBox;
    private SelectBox<Integer> telescopeBox;





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