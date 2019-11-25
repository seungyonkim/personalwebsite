package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {

	private LoadScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MainMenuScreen menuScreen;
	private GameScreen mainScreen;
	private AppPreferences preferences;
	public GameAssetManager assMan = new GameAssetManager();
	private Music playingSong;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;

	public SpriteBatch batch;
	public BitmapFont font;
	public Texture BackgroundMenu;
	public Texture BackgroundMain;
	public Texture LoadPicture;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		BackgroundMenu = new Texture(Gdx.files.internal("andormenu.jpg"));	// file from assets folder
		BackgroundMain = new Texture(Gdx.files.internal("andorboard.jpg"));	// file from assets folder
		LoadPicture = new Texture(Gdx.files.internal("andor.jpg"));	// file from assets folder

		loadingScreen = new LoadScreen(this);
		preferences = new AppPreferences();
		this.setScreen(loadingScreen);

		// tells our asset manger that we want to load the images set in loadImages method
		assMan.queueAddMusic();
		// tells the asset manager to load the images and wait until finished loading
		assMan.manager.finishLoading();
		// loads the 2 sounds we use
		playingSong = assMan.manager.get("music/Rolemusic_-_pl4y1ng.mp3");

		playingSong.play();
	}

	public void render() {
		super.render();		//important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public void changeScreen(int screen) {
		switch(screen) {
			case MENU:
				if(menuScreen == null) menuScreen = new MainMenuScreen(this);	// added (this)
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);		// added (this)
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new GameScreen(this);	//added (this)
				this.setScreen(mainScreen);
				break;
		}
	}

	public AppPreferences getPreferences(){
		return this.preferences;
	}

}
