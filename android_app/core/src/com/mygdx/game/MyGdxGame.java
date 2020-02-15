package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGdxGame extends Game {


	private LoadScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MainMenuScreen menuScreen;
	private GameScreen mainScreen;
	private AppPreferences preferences;
	private Chat ChatScreen;
	public GameAssetManager assMan = new GameAssetManager();
	private Music playingSong;

	public Skin skin;
	Label currentChatLabel;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int CHAT =3;

	public SpriteBatch batch;
	public BitmapFont font;
	public Texture BackgroundMenu;
	public Texture BackgroundMain;
	public Texture LoadPicture;

	public void create() {

		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		BackgroundMenu = new Texture(Gdx.files.internal("andormenu.jpg")); //File from assets folder
		BackgroundMain = new Texture(Gdx.files.internal("andorboard.jpg")); //File from assets folder
		LoadPicture = new Texture(Gdx.files.internal("andor.jpg")); //File from assets folder

		assMan.queueAddSkin();
		assMan.manager.finishLoading();
		skin = assMan.manager.get("skin/glassy-ui.json");



		loadingScreen = new LoadScreen(this);
		preferences = new AppPreferences();



		this.setScreen(loadingScreen);



		// tells our asset manger that we want to load the images set in loadImages method
		assMan.queueAddMusic();
		// tells the asset manager to load the images and wait until finished loading.
		assMan.manager.finishLoading();
		// loads the 2 sounds we use
		playingSong = assMan.manager.get("music/Rolemusic_-_pl4y1ng.mp3");

		playingSong.play();


	}

	public void render() {
		super.render(); //important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MainMenuScreen(this); // added (this)
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this); // added (this)
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new GameScreen(this); //added (this)
				this.setScreen(mainScreen);
				break;
			case CHAT:
				if(ChatScreen == null) ChatScreen = new Chat(this); //added (this)
				this.setScreen(ChatScreen);
				break;




		}
	}
	public AppPreferences getPreferences(){
		return this.preferences;
	}
	public void setCurrentLabel(Label l){
		currentChatLabel = l;
	}

	public synchronized void log(Object str){
		Gdx.app.log("chat3t", str.toString());
	}
	public void acceptMessage(String str){
		currentChatLabel.setText("YOU : " + str+"\n"+ currentChatLabel.getText());
	}

	public String fromArrayToString(String[] arr){
		String tmp = "";
		for(String s : arr){
			tmp+=s;
		}
		return tmp;
	}



	public void sendMessage(String mess){
		Person person = new Person();
		person.setType("Chat");
		person.setMessage(mess);
		acceptMessage(mess);

	}
	public boolean isNamePasswordTrue(String name, String pass){
		return false;
	}



}