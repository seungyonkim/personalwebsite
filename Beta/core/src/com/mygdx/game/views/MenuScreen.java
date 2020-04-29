package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;

import javax.xml.soap.Text;


public class MenuScreen implements Screen {

    private Andor parent;
    private Stage stage;
//    private Skin skin;


    public MenuScreen(Andor andor)
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


        TextButton newGame = new TextButton("New Game", parent.skin);
//        newGame.getLabel().setFontScale(Gdx.graphics.getWidth()/250, Gdx.graphics.getHeight()/250);
        TextButton loadGame = new TextButton( "Load Game", parent.skin);
        TextButton preferences = new TextButton("Preferences", parent.skin);
        TextButton exit = new TextButton("Exit", parent.skin);

//        newGame.getLabel().setFontScale(1.5f);

        table.row().pad(Gdx.graphics.getHeight()/2, 0, 0, 0);
        table.add(newGame).size(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/12).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(loadGame).size(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/12).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(preferences).size(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/12).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);;
        table.add(exit).size(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/12).fillX().uniformX();



        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.NEWGAME);
            }
        });

        loadGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.LOADGAME);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.PREFERENCE);
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Gdx.app.exit();
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