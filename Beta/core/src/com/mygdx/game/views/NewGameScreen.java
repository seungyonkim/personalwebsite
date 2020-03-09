package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;

public class NewGameScreen implements Screen {

    private Andor parent;
    private Stage stage;
//    private Skin skin;


    public NewGameScreen(Andor andor)
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


        TextButton singlePlayer = new TextButton("       Singleplayer       ", parent.skin);
        TextButton multiPlayer = new TextButton( "Multiplayer", parent.skin);
        TextButton backButton = new TextButton("Back", parent.skin);

        table.row().pad(150, 0, 0, 0);
        table.add(singlePlayer).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(multiPlayer).fillX().uniformX();
        table.row().pad(50, 0, 0, 0);
        table.add(backButton).fillX().uniformX();



        singlePlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.SINGLESETUP);
            }
        });

        multiPlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                parent.changeScreen(Andor.MULTISETUP);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.MENU);
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
