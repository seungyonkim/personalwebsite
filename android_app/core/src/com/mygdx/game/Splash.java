package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Splash implements Screen {

    final MyGdxGame game;

    OrthographicCamera camera;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;


    public Splash(final MyGdxGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        renderBackground(); //In first place!!!!
        game.batch.end();

        game.setScreen(new MainMenuScreen(game));
        dispose();

    }


    private void loadTextures() {
        backgroundTexture = new Texture("images/andor.jpg");
        backgroundSprite =new Sprite(backgroundTexture);
    }

    public void renderBackground() {
        backgroundSprite.draw(game.batch);
    }

    @Override
    public void show() {

    }
    @Override
    public void resize(int width, int height) {
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

    }

}