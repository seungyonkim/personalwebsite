package com.mygdx.game.views.EquipmentScreen;

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
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;
import com.mygdx.game.Equipment.Shield;
import com.mygdx.game.Equipment.Wineskin;
import com.mygdx.game.board.Board;
import com.mygdx.game.character.Hero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UseFalconScreen implements Screen {
    private Label titleLabel2;
    private SelectBox<String> toWhom;

    private SelectBox<Integer> wineskinBox;
    private SelectBox<Integer> shieldBox;
    private SelectBox<Integer> helmBox;
    private SelectBox<Integer> bowBox;
    private SelectBox<Integer> falconBox;
    private SelectBox<Integer> telescopeBox;


    private SelectBox<String> wineskinBox2;
    private SelectBox<String> shieldBox2;
    private SelectBox<String> helmBox2;
    private SelectBox<String> bowBox2;
    private SelectBox<String> falconBox2;
    private SelectBox<String> telescopeBox2;

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



    private Label titleLabel;

    private int wineskinNum;
    private int shieldNum;
    private int bowNum;
    private int helmNum;
    private int falconNum;
    private int telescopeNum;

    private Wineskin wineskin1=null;
    private Wineskin wineskin2=null;
    private Wineskin wineskin3=null;
    private Wineskin wineskin4=null;
    private Wineskin wineskin5=null;

    private Shield shield1 = null;
    private Shield shield2 = null;
    private Shield shield3 = null;
    private Shield shield4 = null;


//    private TextureRegion myTextureRegion;
//    private TextureRegionDrawable myTexRegionDrawable;
//    private ImageButton button;


    public UseFalconScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());

        wineskinNum=0;
        shieldNum=0;
        bowNum=0;
        helmNum=0;
        falconNum=0;
        telescopeNum=0;

        gameBoard = parent.getGameBoard();
        currentHero = parent.whoseTurn();
    }

    public void setWineskinStock(Wineskin w1,Wineskin w2, Wineskin w3, Wineskin w4, Wineskin w5){
        wineskin1=w1;
        wineskin2=w2;
        wineskin3=w3;
        wineskin4=w4;
        wineskin5=w5;
    }
    public void setShieldStock(Shield s1, Shield s2, Shield s3, Shield s4){
        shield1=s1;
        shield2=s2;
        shield3=s3;
        shield4=s4;
    }




    @Override
    public void show()
    {
        stage.clear();

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);


        titleLabel = new Label( "Available Equipments:", parent.skin);
        TextButton backButton = new TextButton("Back to Game", parent.skin);


        table.add(titleLabel).colspan(6);
        table.row().pad(50, 0, 0, 0);


        Table table2 = createTable();
        stage.addActor(table2);

        table.add(backButton).colspan(7);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.MULTIGAME);
            }
        });

    }

    public void setAvailableItems(int wineskin,int shield, int bow, int helm, int falcon, int telescope){
        wineskinNum = wineskin;
        shieldNum = shield;
        bowNum = bow;
        helmNum = helm;
        falconNum = falcon;
        telescopeNum = telescope;
    }


    public Table createTable(){
        Table table = new Table();
        table.setFillParent(true);


        titleLabel = new Label( "Please specify the items and their amount and the Receiver :", parent.skin);
        //TextButton backButton = new TextButton("Back", parent.skin); //back to Equipment bag


        ArrayList<Hero> availableHeroes =  parent.getPlayerHeroes();
        Iterator<Hero> iter = availableHeroes.iterator();
        Array<String> availableHeroesString = new Array<>();

        String currentHeroString = currentHero.toString();
        if(currentHeroString.contains("Warrior")){
            currentHeroString="Warrior";
        }
        if(currentHeroString.contains("Archer")){
            currentHeroString="Archer";
        }
        if(currentHeroString.contains("Wizard")){
            currentHeroString="Wizard";
        }
        if(currentHeroString.contains("Dwarf")){
            currentHeroString="Dwarf";
        }


        while(iter.hasNext()){
            String str = iter.next().toString();
            //str = str.substring(25);
            //System.out.println(str);

            if(str.contains("Warrior")){
                str="Warrior";
            }
            if(str.contains("Archer")){
                str="Archer";
            }
            if(str.contains("Wizard")){
                str="Wizard";
            }
            if(str.contains("Dwarf")){
                str="Dwarf";
            }
            if(!str.equals(currentHeroString)){
                availableHeroesString.add(str);
            }

        }




        // startButton = new TextButton("Start",parent.skin);
        table.add(titleLabel).colspan(4);

        toWhom = new SelectBox<String>(parent.skin);


        toWhom.setItems(availableHeroesString);
       // toWhom.setItems(String.valueOf(availableHeroes));
        table.add(toWhom).width(150).colspan(4);
        toWhom.setDisabled(false);






        table.row().pad(10, 0, 0, 0);

        TextButton wineskinButton = new TextButton("Wineskin", parent.skin);
        table.add(wineskinButton).width(60).height(20).padRight(70).colspan(1);


        TextButton shieldButton = new TextButton("Shield", parent.skin);
        table.add(shieldButton).width(60).height(20).padRight(70).colspan(1);

        TextButton bowButton = new TextButton("Bow", parent.skin);
        table.add(bowButton).width(60).height(20).padRight(70).colspan(1);

        TextButton helmButton = new TextButton("Helm", parent.skin);
        table.add(helmButton).width(60).height(20).padRight(70).colspan(1);

        TextButton falconButton = new TextButton("Falcon", parent.skin);
        table.add(falconButton).width(60).height(20).padRight(70).colspan(1);

        TextButton telescopeButton = new TextButton("Telescope", parent.skin);
        table.add(telescopeButton).width(60).height(20).padRight(70).colspan(1);



        table.row().pad(10, 0, 0, 0);

        wineskinBox = new SelectBox<Integer>(parent.skin);
        if(wineskinNum==0){
            wineskinBox.setItems(0);
        }
        if(wineskinNum==1){
            wineskinBox.setItems(0,1);
        }
        if(wineskinNum==2){
            wineskinBox.setItems(0,1,2);
        }
        if(wineskinNum==3){
            wineskinBox.setItems(0,1,2,3);
        }
        if(wineskinNum==4){
            wineskinBox.setItems(0,1,2,3,4);
        }
        if(wineskinNum==5){
            wineskinBox.setItems(0,1,2,3,4,5);
        }

        table.add(wineskinBox).width(60).height(20).padRight(70).colspan(1);
        wineskinBox.setDisabled(false);



        shieldBox = new SelectBox<Integer>(parent.skin);
        if(shieldNum==0){
            shieldBox.setItems(0);
        }
        if(shieldNum==1){
            shieldBox.setItems(0,1);
        }
        if(shieldNum==2){
            shieldBox.setItems(0,1,2);
        }
        if(shieldNum==3){
            shieldBox.setItems(0,1,2,3);
        }
        if(shieldNum==4){
            shieldBox.setItems(0,1,2,3,4);
        }

        table.add(shieldBox).width(60).height(20).padRight(70).colspan(1);
        shieldBox.setDisabled(false);


        bowBox = new SelectBox<Integer>(parent.skin);
        if(bowNum==0){
            bowBox.setItems(0);
        }
        if(bowNum==1){
            bowBox.setItems(0,1);
        }
        if(bowNum==2){
            bowBox.setItems(0,1,2);
        }
        if(bowNum==3){
            bowBox.setItems(0,1,2,3);
        }
        table.add(bowBox).width(60).height(20).padRight(70).colspan(1);
        bowBox.setDisabled(false);



        helmBox = new SelectBox<Integer>(parent.skin);
        if(helmNum==0){
            helmBox.setItems(0);
        }
        if(helmNum==1){
            helmBox.setItems(0,1);
        }
        if(helmNum==2){
            helmBox.setItems(0,1,2);
        }
        if(helmNum==3){
            helmBox.setItems(0,1,2,3);
        }
        table.add(helmBox).width(60).height(20).padRight(70).colspan(1);
        helmBox.setDisabled(false);



        falconBox = new SelectBox<Integer>(parent.skin);
        if(falconNum==0){
            falconBox.setItems(0);
        }
        if(falconNum==1){
            falconBox.setItems(0,1);
        }
        if(falconNum==2){
            falconBox.setItems(0,1,2);
        }
        table.add(falconBox).width(60).height(20).padRight(70).colspan(1);
        falconBox.setDisabled(false);


        telescopeBox = new SelectBox<Integer>(parent.skin);
        if(telescopeNum==0){
            telescopeBox.setItems(0);
        }
        if(telescopeNum==1){
            telescopeBox.setItems(0,1);
        }
        if(telescopeNum==2){
            telescopeBox.setItems(0,1,2);
        }
        table.add(telescopeBox).width(60).height(20).padRight(70).colspan(1);
        telescopeBox.setDisabled(false);




        table.row().pad(20, 0, 0, 0);


        TextButton startButton = new TextButton("Send", parent.skin);
        table.add(startButton).colspan(6);
        final String finalCurrentHeroString = currentHeroString;
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                int selectedWineskin= wineskinBox.getSelected();
                int selectedShield= shieldBox.getSelected();
                int selectedBow= bowBox.getSelected();
                int selectedHelm= helmBox.getSelected();
                int selectedTelescope= telescopeBox.getSelected();
                int selectedFalcon= falconBox.getSelected();
                String selectedHero= toWhom.getSelected();

                System.out.println("selected Wineskin: "+selectedWineskin);
                System.out.println("selected Shield: "+selectedShield);
                System.out.println("selected Bow: "+selectedBow);
                System.out.println("selected Helm: "+selectedHelm);
                System.out.println("selected Telescope: "+selectedTelescope);
                System.out.println("selected Falcon: "+selectedFalcon);
                System.out.println("To: "+selectedHero);

                //decrement hero's own equipment
                if(finalCurrentHeroString.equals("Warrior")){
                    if(selectedWineskin>=1){
                        int numToSend = selectedWineskin;
                        if(numToSend!=0 && wineskin1!=null){
                            parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin1,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin2!=null){
                            parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin2,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin3!=null){
                            parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin3,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin4!=null){
                            parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin4,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin5!=null){
                            parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin5,0);
                            numToSend--;
                        }

                    }
                    if(selectedShield>=1){
                        int numToSend= selectedShield;
                        if(numToSend!=0 && shield1!=null){
                            parent.getEquipmentScreenWarrior().setShieldAvailable(shield1,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield2!=null){
                            parent.getEquipmentScreenWarrior().setShieldAvailable(shield2,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield3!=null){
                            parent.getEquipmentScreenWarrior().setShieldAvailable(shield3,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield4!=null){
                            parent.getEquipmentScreenWarrior().setShieldAvailable(shield4,0);
                            numToSend--;
                        }

                    }


                    parent.getEquipmentScreenWarrior().setBowAvailable(-selectedBow);
                    parent.getEquipmentScreenWarrior().setHelmAvailable(-selectedHelm);
                    parent.getEquipmentScreenWarrior().setFalconAvailable(-selectedFalcon);
                    parent.getEquipmentScreenWarrior().setTelescopeAvailable(-selectedTelescope);
                }
                if(finalCurrentHeroString.equals("Archer")){
                    if(selectedWineskin>=1){
                        int numToSend = selectedWineskin;
                        if(numToSend!=0 && wineskin1!=null){
                            parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin1,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin2!=null){
                            parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin2,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin3!=null){
                            parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin3,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin4!=null){
                            parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin4,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin5!=null){
                            parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin5,0);
                            numToSend--;
                        }

                    }

                    if(selectedShield>=1){
                        int numToSend= selectedShield;
                        if(numToSend!=0 && shield1!=null){
                            parent.getEquipmentScreenArcher().setShieldAvailable(shield1,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield2!=null){
                            parent.getEquipmentScreenArcher().setShieldAvailable(shield2,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield3!=null){
                            parent.getEquipmentScreenArcher().setShieldAvailable(shield3,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield4!=null){
                            parent.getEquipmentScreenArcher().setShieldAvailable(shield4,0);
                            numToSend--;
                        }

                    }
                    parent.getEquipmentScreenArcher().setBowAvailable(-selectedBow);
                    parent.getEquipmentScreenArcher().setHelmAvailable(-selectedHelm);
                    parent.getEquipmentScreenArcher().setFalconAvailable(-selectedFalcon);
                    parent.getEquipmentScreenArcher().setTelescopeAvailable(-selectedTelescope);
                }
                if(finalCurrentHeroString.equals("Wizard")){
                    if(selectedWineskin>=1){
                        int numToSend = selectedWineskin;
                        if(numToSend!=0 && wineskin1!=null){
                            parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin1,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin2!=null){
                            parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin2,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin3!=null){
                            parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin3,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin4!=null){
                            parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin4,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin5!=null){
                            parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin5,0);
                            numToSend--;
                        }

                    }
                    if(selectedShield>=1){
                        int numToSend= selectedShield;
                        if(numToSend!=0 && shield1!=null){
                            parent.getEquipmentScreenWizard().setShieldAvailable(shield1,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield2!=null){
                            parent.getEquipmentScreenWizard().setShieldAvailable(shield2,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield3!=null){
                            parent.getEquipmentScreenWizard().setShieldAvailable(shield3,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield4!=null){
                            parent.getEquipmentScreenWizard().setShieldAvailable(shield4,0);
                            numToSend--;
                        }

                    }
                    parent.getEquipmentScreenWizard().setBowAvailable(-selectedBow);
                    parent.getEquipmentScreenWizard().setHelmAvailable(-selectedHelm);
                    parent.getEquipmentScreenWizard().setFalconAvailable(-selectedFalcon);
                    parent.getEquipmentScreenWizard().setTelescopeAvailable(-selectedTelescope);
                }
                if(finalCurrentHeroString.equals("Dwarf")){
                    if(selectedWineskin>=1){
                        int numToSend = selectedWineskin;
                        if(numToSend!=0 && wineskin1!=null){
                            parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin1,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin2!=null){
                            parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin2,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin3!=null){
                            parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin3,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin4!=null){
                            parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin4,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && wineskin5!=null){
                            parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin5,0);
                            numToSend--;
                        }

                    }
                    if(selectedShield>=1){
                        int numToSend= selectedShield;
                        if(numToSend!=0 && shield1!=null){
                            parent.getEquipmentScreenDwarf().setShieldAvailable(shield1,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield2!=null){
                            parent.getEquipmentScreenDwarf().setShieldAvailable(shield2,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield3!=null){
                            parent.getEquipmentScreenDwarf().setShieldAvailable(shield3,0);
                            numToSend--;
                        }
                        if(numToSend!=0 && shield4!=null){
                            parent.getEquipmentScreenDwarf().setShieldAvailable(shield4,0);
                            numToSend--;
                        }

                    }
                    parent.getEquipmentScreenDwarf().setBowAvailable(-selectedBow);
                    parent.getEquipmentScreenDwarf().setHelmAvailable(-selectedHelm);
                    parent.getEquipmentScreenDwarf().setFalconAvailable(-selectedFalcon);
                    parent.getEquipmentScreenDwarf().setTelescopeAvailable(-selectedTelescope);
                }

                //send the selected equipments to peer hero

                    if(selectedHero=="Warrior"){
                        if(selectedWineskin>=1){
                            int numToSend = selectedWineskin;
                            if(numToSend!=0 && wineskin1!=null){
                                parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin1,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin2!=null){
                                parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin2,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin3!=null){
                                parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin3,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin4!=null){
                                parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin4,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin5!=null){
                                parent.getEquipmentScreenWarrior().setWineskinAvailable(wineskin5,1);
                                numToSend--;
                            }

                        }
                        if(selectedShield>=1){
                            int numToSend= selectedShield;
                            if(numToSend!=0 && shield1!=null){
                                parent.getEquipmentScreenWarrior().setShieldAvailable(shield1,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield2!=null){
                                parent.getEquipmentScreenWarrior().setShieldAvailable(shield2,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield3!=null){
                                parent.getEquipmentScreenWarrior().setShieldAvailable(shield3,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield4!=null){
                                parent.getEquipmentScreenWarrior().setShieldAvailable(shield4,1);
                                numToSend--;
                            }

                        }
                        parent.getEquipmentScreenWarrior().setBowAvailable(selectedBow);
                        parent.getEquipmentScreenWarrior().setHelmAvailable(selectedHelm);
                        parent.getEquipmentScreenWarrior().setFalconAvailable(selectedFalcon);
                        parent.getEquipmentScreenWarrior().setTelescopeAvailable(selectedTelescope);


                    }
                    if(selectedHero=="Archer"){
                        if(selectedWineskin>=1){
                            int numToSend = selectedWineskin;
                            if(numToSend!=0 && wineskin1!=null){
                                parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin1,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin2!=null){
                                parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin2,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin3!=null){
                                parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin3,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin4!=null){
                                parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin4,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin5!=null){
                                parent.getEquipmentScreenArcher().setWineskinAvailable(wineskin5,1);
                                numToSend--;
                            }

                        }
                        if(selectedShield>=1){
                            int numToSend= selectedShield;
                            if(numToSend!=0 && shield1!=null){
                                parent.getEquipmentScreenArcher().setShieldAvailable(shield1,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield2!=null){
                                parent.getEquipmentScreenArcher().setShieldAvailable(shield2,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield3!=null){
                                parent.getEquipmentScreenArcher().setShieldAvailable(shield3,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield4!=null){
                                parent.getEquipmentScreenArcher().setShieldAvailable(shield4,1);
                                numToSend--;
                            }

                        }
                        parent.getEquipmentScreenArcher().setBowAvailable(selectedBow);
                        parent.getEquipmentScreenArcher().setHelmAvailable(selectedHelm);
                        parent.getEquipmentScreenArcher().setFalconAvailable(selectedFalcon);
                        parent.getEquipmentScreenArcher().setTelescopeAvailable(selectedTelescope);
                    }

                    if(selectedHero=="Wizard"){
                        if(selectedWineskin>=1){
                            int numToSend = selectedWineskin;
                            if(numToSend!=0 && wineskin1!=null){
                                parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin1,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin2!=null){
                                parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin2,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin3!=null){
                                parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin3,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin4!=null){
                                parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin4,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin5!=null){
                                parent.getEquipmentScreenWizard().setWineskinAvailable(wineskin5,1);
                                numToSend--;
                            }

                        }
                        if(selectedShield>=1){
                            int numToSend= selectedShield;
                            if(numToSend!=0 && shield1!=null){
                                parent.getEquipmentScreenWizard().setShieldAvailable(shield1,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield2!=null){
                                parent.getEquipmentScreenWizard().setShieldAvailable(shield2,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield3!=null){
                                parent.getEquipmentScreenWizard().setShieldAvailable(shield3,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield4!=null){
                                parent.getEquipmentScreenWizard().setShieldAvailable(shield4,1);
                                numToSend--;
                            }

                        }
                        parent.getEquipmentScreenWizard().setBowAvailable(selectedBow);
                        parent.getEquipmentScreenWizard().setHelmAvailable(selectedHelm);
                        parent.getEquipmentScreenWizard().setFalconAvailable(selectedFalcon);
                        parent.getEquipmentScreenWizard().setTelescopeAvailable(selectedTelescope);

                    }
                    if(selectedHero=="Dwarf"){
                        if(selectedWineskin>=1){
                            int numToSend = selectedWineskin;
                            if(numToSend!=0 && wineskin1!=null){
                                parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin1,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin2!=null){
                                parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin2,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin3!=null){
                                parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin3,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin4!=null){
                                parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin4,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && wineskin5!=null){
                                parent.getEquipmentScreenDwarf().setWineskinAvailable(wineskin5,1);
                                numToSend--;
                            }

                        }
                        if(selectedShield>=1){
                            int numToSend= selectedShield;
                            if(numToSend!=0 && shield1!=null){
                                parent.getEquipmentScreenDwarf().setShieldAvailable(shield1,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield2!=null){
                                parent.getEquipmentScreenDwarf().setShieldAvailable(shield2,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield3!=null){
                                parent.getEquipmentScreenDwarf().setShieldAvailable(shield3,1);
                                numToSend--;
                            }
                            if(numToSend!=0 && shield4!=null){
                                parent.getEquipmentScreenDwarf().setShieldAvailable(shield4,1);
                                numToSend--;
                            }

                        }
                        parent.getEquipmentScreenDwarf().setBowAvailable(selectedBow);
                        parent.getEquipmentScreenDwarf().setHelmAvailable(selectedHelm);
                        parent.getEquipmentScreenDwarf().setFalconAvailable(selectedFalcon);
                        parent.getEquipmentScreenDwarf().setTelescopeAvailable(selectedTelescope);

                    }


                parent.changeScreen(Andor.MULTIGAME);
            }
        });


        return table;
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
