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
import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MultiPlayerSetupScreen implements Screen {

    private Andor parent;
    private Stage stage;
    private Socket socket;

    private int numOfPlayers;
    //    private Skin skin;
    private Texture warriorTexture;
    private Texture archerTexture;
    private Texture wizardTexture;
    private Texture dwarfTexture;
    private Label titleLabel;
    private TextArea ListConnectedPlayers;
    private TextButton chat;


    private Label titleLabel2;
    private SelectBox<String> difficulty;

    private SelectBox<Integer> wineskinWarrior;
    private SelectBox<Integer> goldWarrior;

    private SelectBox<Integer> wineskinArcher;
    private SelectBox<Integer> goldArcher;


    private SelectBox<Integer> wineskinWizard;
    private SelectBox<Integer> goldWizard;


    private SelectBox<Integer> wineskinDwarf;
    private SelectBox<Integer> goldDwarf;

    TextButton startButton;
    TextButton backButton;





    //    private TextureRegion myTextureRegion;
//    private TextureRegionDrawable myTexRegionDrawable;
//    private ImageButton button;
    HashMap<String,String> friendlyPlayers;


    public MultiPlayerSetupScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());
        numOfPlayers = parent.getNumOfPlayers();
        friendlyPlayers = parent.getFriendlyPlayers();
        this.socket= parent.getSocket();

        archerTexture = new Texture(Gdx.files.internal("characters/archer_male_portrait.png"));
        wizardTexture = new Texture(Gdx.files.internal("characters/wizard_male_portrait.png"));
        dwarfTexture = new Texture(Gdx.files.internal("characters/dwarf_male_portrait.png"));
        warriorTexture = new Texture(Gdx.files.internal("characters/warrior_male_portrait.png"));
        titleLabel = new Label( "Choose your hero and difficulty ", parent.skin);


    }


    @Override
    public void show()
    {
        stage.clear();



        connectSocket();
        configSocketEvents();


        Gdx.input.setInputProcessor(stage);

        Table table = createTable();

        stage.addActor(table);




    }

    public Table createTable(){
        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
//        stage.addActor(table);



//        titleLabel = new Label( "Choose Heroes upto "+parent.getNumOfPlayers(), parent.skin);
         backButton = new TextButton("Back", parent.skin);
         startButton = new TextButton("Start",parent.skin);
        table.add(titleLabel).colspan(4);
        difficulty = new SelectBox<String>(parent.skin);
        difficulty.setItems("Easy", "Hard");
        table.add(difficulty).width(150).colspan(4);
        difficulty.setDisabled(true);



        table.row().pad(30, 0, 0, 0);

        final HashMap<String, Boolean> availableHeroes = parent.getAvailableHeroes();




        if (availableHeroes.get("Warrior") == true) {
            final Image warriorImage = new Image(warriorTexture);
            warriorImage.setSize(120, 160);
            warriorImage.setPosition(Gdx.graphics.getWidth() / 5 - warriorImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);

            warriorImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(!parent.getFriendlyPlayers().containsValue("Warrior")) {

                        Warrior selectedHero = new Warrior(String.valueOf(1 + parent.getReadyPlayers()));
                        //parent.selectHero(selectedHero);
                        updateServer(selectedHero);


                    }else{

                        ListConnectedPlayers.appendText("Already used!");

                    }


                }


            });
            table.add(warriorImage).width(120).height(160).padRight(10).colspan(2);



        }

        if (availableHeroes.get("Archer") == true) {
            Image archerImage = new Image(archerTexture);
            archerImage.setSize(120, 160);
            archerImage.setPosition(Gdx.graphics.getWidth() * 2 / 5 - archerImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            archerImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!parent.getFriendlyPlayers().containsValue("Archer")) {
                        Archer selectedHero = new Archer(String.valueOf(1 + parent.getReadyPlayers()));
                        //parent.selectHero(selectedHero);

                        updateServer(selectedHero);
                    }else{
                        ListConnectedPlayers.appendText("Already used!");
                    }

                    //parent.changeScreen(Andor.MULTISETUP);


                }


            });
            table.add(archerImage).width(120).height(160).padRight(10).colspan(2);
        }

        if (availableHeroes.get("Wizard") == true) {
            Image wizardImage = new Image(wizardTexture);
            wizardImage.setSize(120, 160);
            wizardImage.setPosition(Gdx.graphics.getWidth() * 3 / 5 - wizardImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            wizardImage.addListener(new ClickListener() {

                @Override

                public void clicked(InputEvent event, float x, float y) {
                    if(!parent.getFriendlyPlayers().containsValue("Wizard")) {
                        Wizard selectedHero = new Wizard(String.valueOf(1 + parent.getReadyPlayers()));
                        //parent.selectHero(selectedHero);


                        updateServer(selectedHero);
                        //parent.changeScreen(Andor.MULTISETUP);
                    }else{
                        ListConnectedPlayers.appendText("Already used!");
                    }

                }


            });
            table.add(wizardImage).width(120).height(160).padRight(10).colspan(2);
        }

        if (availableHeroes.get("Dwarf") == true) {
            Image dwarfImage = new Image(dwarfTexture);
            dwarfImage.setSize(120, 160);
            dwarfImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - dwarfImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
            dwarfImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(!parent.getFriendlyPlayers().containsValue("Dwarf")) {
                        Dwarf selectedHero = new Dwarf(String.valueOf(1 + parent.getReadyPlayers()));
                        //parent.selectHero(selectedHero);


                        updateServer(selectedHero);
                    }else{

                        ListConnectedPlayers.appendText("Already used!");
                    }




                }


            });
            table.add(dwarfImage).width(120).height(160).padRight(10).colspan(2);



        }
        table.row().pad(10, 0, 0, 0);


        wineskinWarrior = new SelectBox<Integer>(parent.skin);
        wineskinWarrior.setItems(0,1, 2);
        table.add(wineskinWarrior).width(30).height(20).colspan(1);
        goldWarrior = new SelectBox<Integer>(parent.skin);
        goldWarrior.setItems(0,1, 2,3,4,5);
        table.add(goldWarrior).width(30).height(20).colspan(1);
        wineskinWarrior.setDisabled(true);
        goldWarrior.setDisabled(true);


        wineskinArcher = new SelectBox<Integer>(parent.skin);
        wineskinArcher.setItems(0,1, 2);
        table.add(wineskinArcher).width(30).height(20).colspan(1);
        goldArcher = new SelectBox<Integer>(parent.skin);
        goldArcher.setItems(0,1, 2,3,4,5);
        table.add(goldArcher).width(30).height(20).colspan(1);
        wineskinArcher.setDisabled(true);
        goldArcher.setDisabled(true);

        wineskinWizard = new SelectBox<Integer>(parent.skin);
        wineskinWizard.setItems(0,1, 2);
        table.add(wineskinWizard).width(30).height(20).colspan(1);
        goldWizard = new SelectBox<Integer>(parent.skin);
        goldWizard.setItems(0,1, 2,3,4,5);
        table.add(goldWizard).width(30).height(20).colspan(1);
        wineskinWizard.setDisabled(true);
        goldWizard.setDisabled(true);

        wineskinDwarf = new SelectBox<Integer>(parent.skin);
        wineskinDwarf.setItems(0,1, 2);
        table.add(wineskinDwarf).width(30).height(20).colspan(1);
        goldDwarf = new SelectBox<Integer>(parent.skin);
        goldDwarf.setItems(0,1, 2,3,4,5);
        table.add(goldDwarf).width(30).height(20).colspan(1);
        wineskinDwarf.setDisabled(true);
        goldDwarf.setDisabled(true);




        table.row().pad(10, 0, 0, 0);

        titleLabel2 =  new Label( "Lobby ", parent.skin);
        table.add(titleLabel2).colspan(8);

        table.row().pad(20, 0, 0, 0);


        ListConnectedPlayers = new TextArea("",parent.skin);
        ListConnectedPlayers.setDisabled(true);


        table.add(ListConnectedPlayers).prefSize(300).colspan(8);
        for(int i =0 ;i<parent.getPlayerHeroes().size();i++) {
            ListConnectedPlayers.appendText("- " + parent.getPlayerHeroes().get(i).getTypeOfHeroString() + "\n");
            if (parent.decider == 1) {
                String name = parent.getPlayerHeroes().get(i).getTypeOfHeroString();
                if (name.equals("Warrior")) {
                    goldWarrior.setDisabled(false);
                    wineskinWarrior.setDisabled(false);
                } else if (name.equals("Wizard")) {
                    goldWizard.setDisabled(false);
                    wineskinWizard.setDisabled(false);
                } else if (name.equals("Archer")) {
                    goldArcher.setDisabled(false);
                    wineskinArcher.setDisabled(false);
                } else if (name.equals("Dwarf")) {
                    goldDwarf.setDisabled(false);
                    wineskinDwarf.setDisabled(false);
                }
            }
        }



        table.row().pad(30, 0, 0, 0);



        table.add(backButton).colspan(3);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                socket.disconnect();

                if (parent.getReadyPlayers() > 0) {
                    Hero removedHero = parent.removeLastSelectedHero();
                    if (removedHero.getTypeOfHero() == 1) {
                        parent.enableHero("Archer");
                    } else if (removedHero.getTypeOfHero() == 2) {
                        parent.enableHero("Dwarf");
                    } else if (removedHero.getTypeOfHero() == 3) {
                        parent.enableHero("Warrior");
                    } else if (removedHero.getTypeOfHero() == 4) {
                        parent.enableHero("Wizard");
                    }
                    parent.changeScreen(Andor.NEWGAME);
                } else {
                    parent.changeScreen(Andor.NEWGAME);
                }
            }
        });

        chat = new TextButton("Chat",parent.skin);
        chat.setPosition(Gdx.graphics.getWidth() - chat.getWidth() - 10, 100);
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.CHAT);
            }
        });
        table.add(chat).colspan(2);
        chat.setDisabled(true);


        table.add(startButton).colspan(3);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {


                if(friendlyPlayers.size()>=2) {
                    int size = friendlyPlayers.size();
                    int d;
                    if (difficulty.getSelected().equals("Easy")) {
                        d = -1;
                    } else {
                        d = 1;
                    }
                    int totalGold = goldWarrior.getSelected() + goldWizard.getSelected()+ goldArcher.getSelected()+ goldDwarf.getSelected();
                    int totalWineskin = wineskinArcher.getSelected() + wineskinDwarf.getSelected()+ wineskinWarrior.getSelected()+ wineskinWizard.getSelected();

                    if(totalGold<=5 && totalWineskin<=2){
                        /*for(int i =0 ; i < parent.getPlayerHeroes().size();i++){
                            if(parent.getPlayerHeroes().get(i).getTypeOfHeroString().equals("Warrior")){
                                parent.getPlayerHeroes().get(i).addGold(goldWarrior.getSelected());
                            }else if (parent.getPlayerHeroes().get(i).getTypeOfHeroString().equals("Archer")){
                                parent.getPlayerHeroes().get(i).addGold(goldArcher.getSelected());
                            }else if (parent.getPlayerHeroes().get(i).getTypeOfHeroString().equals("Wizard")){
                                parent.getPlayerHeroes().get(i).addGold(goldWizard.getSelected());
                            }else if (parent.getPlayerHeroes().get(i).getTypeOfHeroString().equals("Dwarf")){
                                parent.getPlayerHeroes().get(i).addGold(goldDwarf.getSelected());

                            }
                        }*/

                        if(parent.getMyHero().getTypeOfHeroString().equals("Warrior")){
                            parent.getMyHero().addGold(goldWarrior.getSelected());
                        }else if (parent.getMyHero().getTypeOfHeroString().equals("Archer")){
                            parent.getMyHero().addGold(goldArcher.getSelected());
                        }else if (parent.getMyHero().getTypeOfHeroString().equals("Wizard")){
                            parent.getMyHero().addGold(goldWizard.getSelected());
                        }else if (parent.getMyHero().getTypeOfHeroString().equals("Dwarf")){
                            parent.getMyHero().addGold(goldDwarf.getSelected());

                        }
                        updateGoldWine();
                    }



                    parent.setUpMultiPlayer(size, d);
                    parent.createNewBoard();
                    parent.changeScreen(Andor.MULTIGAME);
                }

            }
        });

        if(parent.decider != 1 ){
            startButton.setDisabled(true);
        }




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
    public void updateGoldWine(){
        JSONObject data =new JSONObject();
        try{
            data.put("goldArcher", goldArcher.getSelected());
            data.put("goldWarrior", goldWarrior.getSelected());
            data.put("goldWizard", goldWizard.getSelected());
            data.put("goldDwarf", goldDwarf.getSelected());
            data.put("wineDwarf", wineskinDwarf.getSelected());
            data.put("wineArcher", wineskinArcher.getSelected());
            data.put("wineWizard", wineskinWizard.getSelected());
            data.put("wineWarrior", wineskinWarrior.getSelected());



            socket.emit("GoldWine", data);

        }catch(Exception e){
            Gdx.app.log("SOCKET.IO", "Error sending data");
        }

    }

    public void updateServer(Hero selectedHero){
        parent.setMyHero(selectedHero);
        JSONObject data =new JSONObject();

        try{
            data.put("name", selectedHero.getTypeOfHeroString());
            data.put("difficulty", difficulty.getSelected());

            socket.emit("playerChose", data);
            Gdx.app.log("SOCKET.IO", "Successfully sending data to the server : "+data.get("name"));
            Gdx.app.log("SOCKET.IO", "Successfully sending data to the server : "+data.get("difficulty"));

        }catch(Exception e){
            Gdx.app.log("SOCKET.IO", "Error sending data");
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

                    ListConnectedPlayers.setText("");
                    for(int i =0;i<objects.length();i++) {
                        if(objects.length() == 1){
                            parent.decider =1;
                        }

                        String name = ((String) objects.getJSONObject(i).getString("name"));
                        ListConnectedPlayers.appendText("- " + name + "\n");

                    }
                    if(parent.decider ==1 ) {
                        difficulty.setDisabled(false);
                        startButton.setDisabled(false);


                        new Dialog("You are the Leader", parent.skin) {
                            {
                                text("You must select the difficulty now. \nAnd you will be in charge of distributing Gold and Wineskin.\nNumber of players : "+objects.length());
                                button("Okay", true);
                            }

                            @Override
                            protected void result(Object object) {

                            }
                        }.show(stage);
                    }else{

                        String level = (String)objects.getJSONObject(0).getString("level");
                        difficulty.setSelected(level);
                        difficulty.setDisabled(true);

                    }

                }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error getting the new player id");
                }
            }
        }).on("playerDisconnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    if (parent.whoseTurn().getTypeOfHero() == 1) {
                        parent.enableHero("Archer");
                    } else if (parent.whoseTurn().getTypeOfHero() == 2) {
                        parent.enableHero("Dwarf");
                    } else if (parent.whoseTurn().getTypeOfHero() == 3) {
                        parent.enableHero("Warrior");
                    } else if (parent.whoseTurn().getTypeOfHero() == 4) {
                        parent.enableHero("Wizard");
                    }

                }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error disconnecting the player");
                }
            }
        }).on("playerChose", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray)args[0];
                try {

                    chat.setDisabled(false);
                    ListConnectedPlayers.setText("");
                    for(int i =0;i<objects.length();i++) {
                        String id = ((String) objects.getJSONObject(i).getString("id"));
                        String name = ((String) objects.getJSONObject(i).getString("name"));
                        ListConnectedPlayers.appendText("- " + name + "\n");
                        if(!friendlyPlayers.containsValue(name)) {
                            friendlyPlayers.put(id, name);
                            if (name.equals("Archer")) {

                                Archer selectedHero = new Archer(String.valueOf(1 + parent.getReadyPlayers()));
                                parent.selectHero(selectedHero);
                                if(parent.decider ==1){
                                    goldArcher.setDisabled(false);
                                    wineskinArcher.setDisabled(false);
                                }


                            }
                            if (name.equals("Warrior")) {
                                Warrior selectedHero = new Warrior(String.valueOf(1 + parent.getReadyPlayers()));
                                parent.selectHero(selectedHero);

                                if(parent.decider ==1){
                                    goldWarrior.setDisabled(false);
                                    wineskinWarrior.setDisabled(false);
                                }


                            }
                            if (name.equals("Wizard")) {
                                Wizard selectedHero = new Wizard(String.valueOf(1 + parent.getReadyPlayers()));
                                parent.selectHero(selectedHero);

                                if(parent.decider ==1){
                                    goldWizard.setDisabled(false);
                                    wineskinWizard.setDisabled(false);
                                }


                            }
                            if (name.equals("Dwarf")) {
                                Dwarf selectedHero = new Dwarf(String.valueOf(1 + parent.getReadyPlayers()));
                                parent.selectHero(selectedHero);

                                if(parent.decider ==1){
                                    goldDwarf.setDisabled(false);
                                    wineskinDwarf.setDisabled(false);
                                }

                            }
                        }

                    }
                        String level = (String)objects.getJSONObject(0).getString("level");
                        difficulty.setSelected(level);
                        difficulty.setDisabled(true);

                }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error handling the other player choosing");
                }
            }
        }).on("GoldWine", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray)args[0];

                try {
                    startButton.setDisabled(false);

                    for(int i =0;i<objects.length();i++) {
                        String name = ((String) objects.getJSONObject(i).getString("name"));
                        if (name.equals("Warrior")) {

                            int WarriorGold = objects.getJSONObject(i).getInt("gold");
                            int WarriorWine = objects.getJSONObject(i).getInt("wineskin");
                            goldWarrior.setSelected(WarriorGold);
                            wineskinWarrior.setSelected(WarriorWine);


                        }
                        if (name.equals("Wizard")) {

                            int WizardGold = objects.getJSONObject(i).getInt("gold");
                            int WizardWine = objects.getJSONObject(i).getInt("wineskin");
                            goldWizard.setSelected(WizardGold);
                            wineskinWizard.setSelected(WizardWine);
                        }
                        if (name.equals("Archer")) {

                            int ArcherGold = objects.getJSONObject(i).getInt("gold");
                            int ArcherWine = objects.getJSONObject(i).getInt("wineskin");
                            goldArcher.setSelected(ArcherGold);
                            wineskinArcher.setSelected(ArcherWine);
                        }

                        if (name.equals("Dwarf")) {

                            int DwarfGold = objects.getJSONObject(i).getInt("gold");
                            int DwarfWine = objects.getJSONObject(i).getInt("wineskin");
                            goldDwarf.setSelected(DwarfGold);
                            wineskinDwarf.setSelected(DwarfWine);



                        }

                    }





                    }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error distributing Gold and wineskin");
                }
            }
        });
    }


}