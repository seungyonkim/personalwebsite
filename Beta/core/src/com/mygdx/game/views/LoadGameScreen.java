package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;

public class LoadGameScreen implements Screen {

    private Andor parent;
    private Stage stage;
//    private Skin skin;


    public LoadGameScreen(Andor andor)
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


        TextButton collaborativeFight = new TextButton("Collaborative Fight", parent.skin);
        TextButton merchant = new TextButton( "Buying from Merchant", parent.skin);
        TextButton loseGame = new TextButton("Losing Game", parent.skin);
        TextButton winGame = new TextButton("Winning Game", parent.skin);
        TextButton backButton = new TextButton("Back", parent.skin);

        table.row().pad(150, 0, 0, 0);
        table.add(collaborativeFight).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(merchant).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(loseGame).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(winGame).fillX().uniformX();
        table.row().pad(30, 0, 0, 0);
        table.add(backButton).fillX().uniformX();



        collaborativeFight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.LOADBATTLEGAME);
            }
        });

        merchant.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.LOADMERCHANTGAME);
            }
        });

        loseGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Andor.LOADLOSEGAME);
            }
        });

        winGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

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