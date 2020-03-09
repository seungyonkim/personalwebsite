package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;
import com.mygdx.game.character.Archer;
import com.mygdx.game.character.Dwarf;
import com.mygdx.game.character.Hero;
import com.mygdx.game.character.Warrior;
import com.mygdx.game.character.Wizard;

public class ChooseHeroScreen implements Screen {

    private Andor parent;
    private Stage stage;

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


    public ChooseHeroScreen(Andor andor)
    {
        this.parent = andor;
        stage = new Stage(new ScreenViewport());
        numOfPlayers = parent.getNumOfPlayers();
    }


    @Override
    public void show()
    {
        stage.clear();

//        if (numOfPlayers == 4) {
//            parent.changeScreen(Andor.GAME);
//        }

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
//        stage.addActor(table);

        warriorTexture = new Texture(Gdx.files.internal("characters/warrior_male_portrait.png"));
        archerTexture = new Texture(Gdx.files.internal("characters/archer_male_portrait.png"));
        wizardTexture = new Texture(Gdx.files.internal("characters/wizard_male_portrait.png"));
        dwarfTexture = new Texture(Gdx.files.internal("characters/dwarf_male_portrait.png"));

        Image warriorImage = new Image(warriorTexture);
        Image archerImage = new Image(archerTexture);
        Image wizardImage = new Image(wizardTexture);
        Image dwarfImage = new Image(dwarfTexture);


//        titleLabel = new Label( "Choose Heroes upto "+parent.getNumOfPlayers(), parent.skin);
        titleLabel = new Label( "Choose Hero for Player "+(1+parent.getReadyPlayers()), parent.skin);
        TextButton backButton = new TextButton("Back", parent.skin);
        TextButton nextButton = new TextButton("Next", parent.skin);

        warriorImage.setSize(120, 160);
        archerImage.setSize(120, 160);
        wizardImage.setSize(120,160);
        dwarfImage.setSize(120, 160);

        warriorImage.setPosition(Gdx.graphics.getWidth()/5 - warriorImage.getWidth()/2, Gdx.graphics.getHeight()/4);
        archerImage.setPosition(Gdx.graphics.getWidth()*2/5 - archerImage.getWidth()/2, Gdx.graphics.getHeight()/4);
        wizardImage.setPosition(Gdx.graphics.getWidth()*3/5 - wizardImage.getWidth()/2, Gdx.graphics.getHeight()/4);
        dwarfImage.setPosition(Gdx.graphics.getWidth()*4/5 - dwarfImage.getWidth()/2, Gdx.graphics.getHeight()/4);

        Table container = new Table();
        container.addActor(warriorImage);

        table.add(titleLabel).colspan(4);
        table.row().pad(10, 0, 0, 0);
        table.add(warriorImage).width(120).height(160).padRight(10);
        table.add(archerImage).width(120).height(160).padRight(10);
        table.add(wizardImage).width(120).height(160).padRight(10);
        table.add(dwarfImage).width(120).height(160).padRight(10);
//        table.add(container);
//        table.add(container2);
        table.row().pad(10, 0, 0, 0);

        // Adding image button
//        myTextureRegion = new TextureRegion(warriorTexture);
//        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
//        button = new ImageButton(myTexRegionDrawable); //Set the button up
//        button.setSize(120, 160);
//        button.setPosition(Gdx.graphics.getWidth()/5 - warriorImage.getWidth()/2, Gdx.graphics.getHeight()/4);
//
//        stage.addActor(button); //Add the button to the stage to perform rendering and take input.

        table.add(backButton).colspan(2);
        table.add(nextButton).colspan(2);


//        stage.addActor(warriorImage);
//        stage.addActor(archerImage);
//        stage.addActor(wizardImage);
//        stage.addActor(dwarfImage);

        stage.addActor(table);


        warriorImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Warrior clicked");
//                parent.changeScreen(Andor.SINGLESETUP);
                System.out.println("Player "+(1+parent.getReadyPlayers())+" selected Warrior.");

                Warrior selectedHero = new Warrior(String.valueOf(1+parent.getReadyPlayers()));
                parent.selectHero(selectedHero);
                System.out.println((parent.getNumOfPlayers()-parent.getReadyPlayers()) + " players to go.");

                if (parent.getReadyPlayers() < parent.getNumOfPlayers()) {
                    parent.changeScreen(Andor.CHOOSEHERO);
                } else {
                    parent.createNewBoard();
                    parent.changeScreen(Andor.GAME);
                }


            }
        });

        archerImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Archer clicked");
//                parent.changeScreen(Andor.SINGLESETUP);
                System.out.println("Player "+(1+parent.getReadyPlayers())+" selected Archer.");

                Archer selectedHero = new Archer(String.valueOf(1+parent.getReadyPlayers()));
                parent.selectHero(selectedHero);
                System.out.println((parent.getNumOfPlayers()-parent.getReadyPlayers()) + " players to go.");

                if (parent.getReadyPlayers() < parent.getNumOfPlayers()) {
                    parent.changeScreen(Andor.CHOOSEHERO);
                } else {
                    parent.createNewBoard();
                    parent.changeScreen(Andor.GAME);
                }

            }
        });

        wizardImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Wizard clicked");
//                parent.changeScreen(Andor.SINGLESETUP);
                System.out.println("Player "+(1+parent.getReadyPlayers())+" selected Wizard.");

                Wizard selectedHero = new Wizard(String.valueOf(1+parent.getReadyPlayers()));
                parent.selectHero(selectedHero);
                System.out.println((parent.getNumOfPlayers()-parent.getReadyPlayers()) + " players to go.");

                if (parent.getReadyPlayers() < parent.getNumOfPlayers()) {
                    parent.changeScreen(Andor.CHOOSEHERO);
                } else {
                    parent.createNewBoard();
                    parent.changeScreen(Andor.GAME);
                }

            }
        });

        dwarfImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Dwarf clicked");
//                parent.changeScreen(Andor.SINGLESETUP);
                System.out.println("Player "+(1+parent.getReadyPlayers())+" selected Dwarf.");

                Dwarf selectedHero = new Dwarf(String.valueOf(1+parent.getReadyPlayers()));
                parent.selectHero(selectedHero);
                System.out.println((parent.getNumOfPlayers()-parent.getReadyPlayers()) + " players to go.");


                if (parent.getReadyPlayers() < parent.getNumOfPlayers()) {
                    parent.changeScreen(Andor.CHOOSEHERO);
                } else {
                    parent.createNewBoard();
                    parent.changeScreen(Andor.GAME);
                }

            }
        });

        // Adding listener to the image button
//      button.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                parent.changeScreen(Andor.SINGLESETUP);
//            }
//        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (parent.getReadyPlayers() > 0) {
                    parent.removeLastSelectedHero();
                    parent.changeScreen(Andor.CHOOSEHERO);
                } else {
                    parent.changeScreen(Andor.SINGLESETUP);
                }
            }
        });

        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (parent.getReadyPlayers() < parent.getNumOfPlayers()) {
                    parent.changeScreen(Andor.CHOOSEHERO);
                } else {
//                    parent.createNewBoard();
                    parent.changeScreen(Andor.GAME);
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
        stage.getBatch().draw(parent.andorMenu, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        stage.getBatch().draw(warriorTexture, Gdx.graphics.getWidth()/5-60, Gdx.graphics.getHeight()/4, 120, 160);
//        stage.getBatch().draw(archerTexture, Gdx.graphics.getWidth()*2/5-60, Gdx.graphics.getHeight()/4, 120, 160);
//        stage.getBatch().draw(wizardTexture, Gdx.graphics.getWidth()*3/5-60, Gdx.graphics.getHeight()/4, 120, 160);
//        stage.getBatch().draw(dwarfTexture, Gdx.graphics.getWidth()*4/5-60, Gdx.graphics.getHeight()/4, 120, 160);
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
