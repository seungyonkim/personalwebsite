package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen{

    private MyGdxGame parent;
    private Stage stage;
    private Skin skin;
    Texture playerImage;

    public MainMenuScreen(MyGdxGame parent){
        this.parent = parent;
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());

        parent.assMan.queueAddSkin();
        parent.assMan.manager.finishLoading();
        skin = parent.assMan.manager.get("skin/glassy-ui.json");
        playerImage = new Texture(Gdx.files.internal("player.png"));

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        stage.addActor(table);


        //create buttons
        final TextButton newGame = new TextButton("Start", skin);
        final TextButton preferences = new TextButton("Preferences", skin);
        final TextButton exit = new TextButton("Exit", skin);

        Label LabelPlayer1 = new Label("Player 1 :", skin);
        Label LabelPlayer2 = new Label("Player 2 :", skin);
        Label LabelPlayer3 = new Label("Player 3 :", skin);
        LabelPlayer1.setFontScale(4f);
        LabelPlayer2.setFontScale(4f);
        LabelPlayer3.setFontScale(4f);

        Label namePlayer1 = new Label(" Michael ", skin);
        Label namePlayer2 = new Label(" Samy ", skin);
        Label namePlayer3 = new Label(" Loading ...", skin);
        namePlayer1.setFontScale(2f);
        namePlayer2.setFontScale(2f);
        namePlayer3.setFontScale(2f);

        Label left = new Label(" <<< " , skin);
        left.setFontScale(6f);
        Label right = new Label(" >>> " , skin);
        right.setFontScale(6f);



        //add buttons to table
        table.add(newGame).fillX().uniformX().colspan(3);
        table.row().pad(40, 0, 40, 0);
        table.add(preferences).fillX().uniformX().colspan(3);
        table.row();
        table.add(exit).fillX().uniformX().colspan(3);
        table.row().pad(40, 0, 40, 0);

        table.add(LabelPlayer1).fillX().uniformX();
        table.add(LabelPlayer2).fillX().uniformX();
        table.add(LabelPlayer3).fillX().uniformX();

        table.row();

        table.add(namePlayer1).fillX().uniformX();
        table.add(namePlayer2).fillX().uniformX();
        table.add(namePlayer3).fillX().uniformX();

        table.row().pad(40, 0, 40, 0);

        table.add(left).fillX().uniformX();
        Image image = new Image(playerImage);
        table.add(image);
        table.add(right).fillX().uniformX();








        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Gdx.app.exit();

            }
        });


        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                parent.changeScreen(MyGdxGame.APPLICATION);

            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                parent.changeScreen(MyGdxGame.PREFERENCES);


            }
        });

    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        stage.getBatch().begin();
        stage.getBatch().draw(parent.BackgroundMenu, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }

}
