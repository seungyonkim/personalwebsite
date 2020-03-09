package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;
import com.mygdx.game.board.Board;
import com.mygdx.game.character.Hero;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private Andor parent;
    private Stage stage;

    private OrthographicCamera camera;

    private Texture warriorTexture;
    private Texture archerTexture;
    private Texture wizardTexture;
    private Texture dwarfTexture;

    private Rectangle player;

    private Rectangle warrior;
    private Rectangle archer;
    private Rectangle wizard;
    private Rectangle dwarf;

    private Board gameBoard;

    public GameScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());

        gameBoard = parent.getGameBoard();

        warriorTexture = new Texture(Gdx.files.internal("characters/andor_warrior_M.png"));
        archerTexture = new Texture(Gdx.files.internal("characters/andor_archer_M.png"));
        wizardTexture = new Texture(Gdx.files.internal("characters/andor_wizard_M.png"));
        dwarfTexture = new Texture(Gdx.files.internal("characters/andor_dwarf_M.png"));

        player = new Rectangle();
        player.x = parent.andorBoard.getWidth()/2 - 300/2;
        player.y = parent.andorBoard.getHeight()/2 - 500/2;
        player.width = 300;
        player.height = 500;

        warrior = new Rectangle();
        warrior.width = 300;
        warrior.height = 500;
        warrior.x = parent.andorBoard.getWidth()*2519/9861 - warrior.width/2;
        warrior.y = parent.andorBoard.getHeight()*3729/6476 - warrior.height/2;

        archer = new Rectangle();
        archer.width = 300;
        archer.height = 500;
        archer.x = parent.andorBoard.getWidth()*287/9861 - archer.width/2;
        archer.y = parent.andorBoard.getHeight()*1600/6476 - archer.height/2;

        wizard = new Rectangle();
        wizard.width = 300;
        wizard.height = 500;
        wizard.x = parent.andorBoard.getWidth()*1899/9861 - wizard.width/2;
        wizard.y = parent.andorBoard.getHeight()*1942/6476 - wizard.height/2;

        dwarf = new Rectangle();
        dwarf.width = 300;
        dwarf.height = 500;
        dwarf.x = parent.andorBoard.getWidth()*1250/9861 - dwarf.width/2;
        dwarf.y = parent.andorBoard.getHeight()*5771/6476 - dwarf.height/2;


        // create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, parent.andorBoard.getWidth(), parent.andorBoard.getHeight());

    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Tell camera to update its matrices
        camera.update();

        // Tell SpriteBatch to render in the coordinate system specified by the camera
        parent.batch.setProjectionMatrix(camera.combined);

        // Begin a new batch and draw
        parent.batch.begin();
        parent.batch.draw(parent.andorBoard, 0, 0);
//        parent.batch.draw(playerTexture, player.x, player.y, player.width, player.height);

        ArrayList<Hero> playerHeroes = parent.getPlayerHeroes();
        for (int i = 0; i < playerHeroes.size(); i++) {
            if (playerHeroes.get(i).getTypeOfHero() == 1) {
                parent.batch.draw(archerTexture, archer.x, archer.y, archer.width, archer.height);
            }
            if (playerHeroes.get(i).getTypeOfHero() == 2) {
                parent.batch.draw(dwarfTexture, dwarf.x, dwarf.y, dwarf.width, dwarf.height);
            }
            if (playerHeroes.get(i).getTypeOfHero() == 3) {
                parent.batch.draw(warriorTexture, warrior.x, warrior.y, warrior.width, warrior.height);
            }
            if (playerHeroes.get(i).getTypeOfHero() == 4) {
                parent.batch.draw(wizardTexture, wizard.x, wizard.y, wizard.width, wizard.height);
            }
        }

        parent.batch.end();
        stage.draw();

        // Process user input
        if (Gdx.input.isTouched()) {
            System.out.println("X: "+Gdx.input.getX()+"   Y: "+Gdx.input.getY());
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            warrior.x = touchPos.x - warrior.width / 2;
            warrior.y = touchPos.y - warrior.height / 8;
        }

        // make sure the bucket stays within the screen bounds
        if (warrior.x < 0) {
            warrior.x = 0;
        }
        if (warrior.x > parent.andorBoard.getWidth() - warrior.width) {
            warrior.x = parent.andorBoard.getWidth() - warrior.width;
        }
        if (warrior.y < 0) {
            warrior.y = 0;
        }
        if (warrior.y > parent.andorBoard.getHeight() - warrior.height) {
            warrior.y = parent.andorBoard.getHeight() - warrior.height;
        }

//        // Process user input
//        if (Gdx.input.isTouched()) {
//            System.out.println("X: "+Gdx.input.getX()+"   Y: "+Gdx.input.getY());
//            Vector3 touchPos = new Vector3();
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(touchPos);
//            player.x = touchPos.x - player.width / 2;
//            player.y = touchPos.y - player.height / 8;
//        }
//
//        // make sure the bucket stays within the screen bounds
//        if (player.x < 0) {
//            player.x = 0;
//        }
//        if (player.x > parent.andorBoard.getWidth() - player.width) {
//            player.x = parent.andorBoard.getWidth() - player.width;
//        }
//        if (player.y < 0) {
//            player.y = 0;
//        }
//        if (player.y > parent.andorBoard.getHeight() - player.height) {
//            player.y = parent.andorBoard.getHeight() - player.height;
//        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
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
//        playerTexture.dispose();
        warriorTexture.dispose();
        archerTexture.dispose();
        wizardTexture.dispose();
        dwarfTexture.dispose();
    }
}
