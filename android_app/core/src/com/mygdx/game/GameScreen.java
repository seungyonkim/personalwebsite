package com.mygdx.game;



import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    final MyGdxGame game;

    Texture playerImage;
    OrthographicCamera camera;
    Rectangle player ;

    public GameScreen(final MyGdxGame game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        playerImage = new Texture(Gdx.files.internal("player.png"));



        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 9000, 6000);

        // create a Rectangle to logically represent the bucket
        player = new Rectangle();
        player.x = 8000 / 2 - 500 / 2; // center the bucket horizontally
        player.y = 2000; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        player.width = 500;
        player.height = 500;

    }


    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.batch.draw(game.BackgroundMain,0,0);
        game.batch.draw(playerImage, player.x, player.y, player.width, player.height);
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touchPos);
            player.x = touchPos.x - 500 / 2;
            player.y=touchPos.y - 500/2;
        }


        // make sure the bucket stays within the screen bounds
        if (player.x < 0)
            player.x = 0;
        if (player.x > 9000 - 500)
            player.x = 9000 - 500;
        if (player.y < 0)
            player.y = 0;
        if (player.y >  6000- 500)
            player.y = 6000 - 500;


    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        playerImage.dispose();
    }



}
