package com.mygdx.game.views;

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
//    private TextureRegion myTextureRegion;
//    private TextureRegionDrawable myTexRegionDrawable;
//    private ImageButton button;


    public MultiPlayerSetupScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());
        numOfPlayers = parent.getNumOfPlayers();
    }


    @Override
    public void show()
    {
        stage.clear();

        connectSocket();
        configSocketEvents();


        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
//        stage.addActor(table);


//        titleLabel = new Label( "Choose Heroes upto "+parent.getNumOfPlayers(), parent.skin);
        titleLabel = new Label( "Choose Hero for Player "+(1+parent.getReadyPlayers()), parent.skin);
        TextButton backButton = new TextButton("Back", parent.skin);


        table.add(titleLabel).colspan(4);
        table.row().pad(50, 0, 0, 0);

        HashMap<String, Boolean> availableHeroes = parent.getAvailableHeroes();


            if (availableHeroes.get("Warrior") == true) {
                warriorTexture = new Texture(Gdx.files.internal("characters/warrior_male_portrait.png"));
                Image warriorImage = new Image(warriorTexture);
                warriorImage.setSize(120, 160);
                warriorImage.setPosition(Gdx.graphics.getWidth() / 5 - warriorImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
                warriorImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Warrior clicked");
//                parent.changeScreen(Andor.SINGLESETUP);


                        new Dialog("Waiting other players ...", parent.skin) {
                            {
                                text("Start?");
                                button("Yes", true);
                                button("No", false);
                            }

                            @Override
                            protected void result(Object object) {
                                if (object.equals(true)) {

//                                    System.out.println("Player "+(1+parent.getReadyPlayers())+" selected Warrior.");

                                    Warrior selectedHero = new Warrior(String.valueOf(1 + parent.getReadyPlayers()));
                                    parent.selectHero(selectedHero);
                                    parent.disableHero("Warrior");
                                    updateServer(selectedHero);

//                                    System.out.println((parent.getNumOfPlayers()-parent.getReadyPlayers()) + " players to go.");

                                    parent.createNewBoard();
                                    parent.changeScreen(Andor.MULTIGAME);
                                }
                            }
                        }.show(stage);

                    }


                });
                table.add(warriorImage).width(120).height(160).padRight(10);
            }

            if (availableHeroes.get("Archer") == true) {
                archerTexture = new Texture(Gdx.files.internal("characters/archer_male_portrait.png"));
                Image archerImage = new Image(archerTexture);
                archerImage.setSize(120, 160);
                archerImage.setPosition(Gdx.graphics.getWidth() * 2 / 5 - archerImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
                archerImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //                System.out.println("Archer clicked");
                        //                parent.changeScreen(Andor.SINGLESETUP);



                        new Dialog("Waiting other players ...", parent.skin) {
                            {
                                text("Start?");
                                button("Yes", true);
                                button("No", false);
                            }

                            @Override
                            protected void result(Object object) {
                                if (object.equals(true)) {

//                                    System.out.println("Player " + (1 + parent.getReadyPlayers()) + " selected Archer.");
                                    Archer selectedHero = new Archer(String.valueOf(1 + parent.getReadyPlayers()));
                                    parent.selectHero(selectedHero);
                                    parent.disableHero("Archer");
                                    updateServer(selectedHero);



//                                    System.out.println((parent.getNumOfPlayers() - parent.getReadyPlayers()) + " players to go.");

                                    parent.createNewBoard();
                                    parent.changeScreen(Andor.MULTIGAME);
                                }
                            }
                        }.show(stage);
                    }


                });
                table.add(archerImage).width(120).height(160).padRight(10);
            }

            if (availableHeroes.get("Wizard") == true) {
                wizardTexture = new Texture(Gdx.files.internal("characters/wizard_male_portrait.png"));
                Image wizardImage = new Image(wizardTexture);
                wizardImage.setSize(120, 160);
                wizardImage.setPosition(Gdx.graphics.getWidth() * 3 / 5 - wizardImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
                wizardImage.addListener(new ClickListener() {

                    @Override

                    public void clicked(InputEvent event, float x, float y) {
                        //                System.out.println("Wizard clicked");
                        //                parent.changeScreen(Andor.SINGLESETUP);

                        new Dialog("Waiting other players ...", parent.skin) {
                            {
                                text("Start?");
                                button("Yes", true);
                                button("No", false);
                            }

                            @Override
                            protected void result(Object object) {
                                if (object.equals(true)) {

//                                    System.out.println("Player " + (1 + parent.getReadyPlayers()) + " selected Wizard.");

                                    Wizard selectedHero = new Wizard(String.valueOf(1 + parent.getReadyPlayers()));
                                    parent.selectHero(selectedHero);
                                    parent.disableHero("Wizard");
                                    updateServer(selectedHero);


//                                    System.out.println((parent.getNumOfPlayers() - parent.getReadyPlayers()) + " players to go.");

                                    parent.createNewBoard();
                                    parent.changeScreen(Andor.MULTIGAME);
                                }
                            }
                        }.show(stage);
                    }


                });
                table.add(wizardImage).width(120).height(160).padRight(10);
            }

            if (availableHeroes.get("Dwarf") == true) {
                dwarfTexture = new Texture(Gdx.files.internal("characters/dwarf_male_portrait.png"));
                Image dwarfImage = new Image(dwarfTexture);
                dwarfImage.setSize(120, 160);
                dwarfImage.setPosition(Gdx.graphics.getWidth() * 4 / 5 - dwarfImage.getWidth() / 2, Gdx.graphics.getHeight() / 4);
                dwarfImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //                System.out.println("Dwarf clicked");
                        //                parent.changeScreen(Andor.SINGLESETUP);


                        new Dialog("Waiting other players ...", parent.skin) {
                            {

                                text("Start?");
                                button("Yes", true);
                                button("No", false);
                            }

                            @Override
                            protected void result(Object object) {
                                if (object.equals(true)) {

                                    Dwarf selectedHero = new Dwarf(String.valueOf(1 + parent.getReadyPlayers()));
                                    parent.selectHero(selectedHero);
                                    parent.disableHero("Dwarf");
//                                    System.out.println("Player " + (1 + parent.getReadyPlayers()) + " selected Dwarf.");
                                    updateServer(selectedHero);



//                                    System.out.println((parent.getNumOfPlayers() - parent.getReadyPlayers()) + " players to go.");

                                    parent.createNewBoard();
                                    parent.changeScreen(Andor.MULTIGAME);
                                }
                            }
                        }.show(stage);
                    }


                });
                table.add(dwarfImage).width(120).height(160).padRight(10);

        }
        table.row().pad(30, 0, 0, 0);
        table.add(backButton).colspan(4);

        stage.addActor(table);


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
                    parent.changeScreen(Andor.CHOOSEHERO);
                } else {
                    parent.changeScreen(Andor.MULTISETUP);
                }
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



    public void updateServer(Hero selectedHero){

            JSONObject data =new JSONObject();
            ArrayList<Hero> playerHeroes = parent.getPlayerHeroes();
            try{
                data.put("name", selectedHero.getTypeOfHeroString());

                socket.emit("playerChose", data);
                Gdx.app.log("SOCKET.IO", "SUCCESS sending data");
            }catch(Exception e){
                Gdx.app.log("SOCKET.IO", "Error sending data");
            }

    }

    public void connectSocket(){

        try{
            socket =IO.socket("http://localhost:8080");
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
        }).on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "My Id : " + id);
                }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error getting the id");
                }

            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "New player connect: "+id);


                }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error getting the new player id");
                }
            }
        }).on("playerDisconnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
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
                JSONObject data = (JSONObject)args[0];
                try {
                    String id = data.getString("id");
                    String name = data.getString("name");
                    if(name.equals("Archer")) {
                        Archer selectedHero = new Archer(String.valueOf(1 + parent.getReadyPlayers()));
                        parent.selectHero(selectedHero);
                        parent.disableHero("Archer");
                    }else if(name.equals("Warrior")) {
                        Warrior selectedHero = new Warrior(String.valueOf(1 + parent.getReadyPlayers()));
                        parent.selectHero(selectedHero);
                        parent.disableHero("Warrior");
                    }else if(name.equals("Wizard")) {
                        Wizard selectedHero = new Wizard(String.valueOf(1 + parent.getReadyPlayers()));
                        parent.selectHero(selectedHero);
                        parent.disableHero("Wizard");
                    }else if(name.equals("Dwarf")) {
                        Dwarf selectedHero = new Dwarf(String.valueOf(1 + parent.getReadyPlayers()));
                        parent.selectHero(selectedHero);
                        parent.disableHero("Dwarf");
                    }

                }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error handling the player choosing");
                }
            }
        }).on("getPlayers", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray) args[0];
                try {
                    for(int i = 0; i < objects.length(); i++){

                        String name = ((String) objects.getJSONObject(i).getString("name"));
                        if(name.equals("Archer")) {
                            Archer selectedHero = new Archer(String.valueOf(1 + parent.getReadyPlayers()));
                            parent.selectHero(selectedHero);
                            parent.disableHero("Archer");
                        }else if(name.equals("Warrior")) {
                            Warrior selectedHero = new Warrior(String.valueOf(1 + parent.getReadyPlayers()));
                            parent.selectHero(selectedHero);
                            parent.disableHero("Warrior");
                        }else if(name.equals("Wizard")) {
                            Wizard selectedHero = new Wizard(String.valueOf(1 + parent.getReadyPlayers()));
                            parent.selectHero(selectedHero);
                            parent.disableHero("Wizard");
                        }else if(name.equals("Dwarf")) {
                            Dwarf selectedHero = new Dwarf(String.valueOf(1 + parent.getReadyPlayers()));
                            parent.selectHero(selectedHero);
                            parent.disableHero("Dwarf");
                        }else{
                            Gdx.app.log("SocketIO", "No passed players found");

                        }

                    }
                } catch(Exception e){
                    Gdx.app.log("SocketIO", "Error getting the players");
                }
            }
        });
    }


}
