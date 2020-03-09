package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;

public class SinglePlayerSetupScreen implements Screen {

    private Andor parent;
    private Stage stage;

    private Label numOfPlayersLabel;
    private Label difficultyLabel;
//    private Skin skin;


    public SinglePlayerSetupScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());
    }


    @Override
    public void show()
    {
        stage.clear();

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
        stage.addActor(table);

//        skin  = new Skin(Gdx.files.internal("skin/Shadeui/uiskin.json"));


//        TextButton singlePlayer = new TextButton("       Singleplayer       ", parent.skin);
//        TextButton multiPlayer = new TextButton( "Multiplayer", parent.skin);


        numOfPlayersLabel = new Label( "Players", parent.skin);
        difficultyLabel = new Label("Difficulty", parent.skin);

        final SelectBox<String> numOfPlayers = new SelectBox<String>(parent.skin);
        final SelectBox<String> difficulty = new SelectBox<String>(parent.skin);
        numOfPlayers.setItems("2 Players", "3 Players", "4 Players");
        difficulty.setItems("Easy", "Hard");
        TextButton backButton = new TextButton("Back", parent.skin);
        TextButton nextButton = new TextButton("Next", parent.skin);

        table.row().pad(150, 0, 0, 0);
        table.add(numOfPlayersLabel).left();
        table.add(numOfPlayers);
        table.row().pad(10, 0, 0, 0);
        table.add(difficultyLabel).left();
        table.add(difficulty);
        table.row().pad(10, 0, 0, 0);
        table.add(backButton);
        table.add(nextButton);


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.NEWGAME);
            }
        });

        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                parent.changeScreen(Andor.CHOOSEHERO);
                int p, d;
                if (numOfPlayers.getSelected().equals("2 Players")) {
                    p = 2;
                } else if (numOfPlayers.getSelected().equals("3 Players")) {
                    p = 3;
                } else {
                    p = 4;
                }

                if (difficulty.getSelected().equals("Easy")) {
                    d = -1;
                } else {
                    d = 1;
                }
                parent.setUpSinglePlayer(p, d);
                parent.changeScreen(Andor.CHOOSEHERO);
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
        stage.getBatch().draw(parent.menuScreenBG, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
