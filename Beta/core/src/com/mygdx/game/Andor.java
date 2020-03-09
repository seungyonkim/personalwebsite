package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.character.Hero;
import com.mygdx.game.preference.AppPreferences;
import com.mygdx.game.views.ChooseHeroScreen;
import com.mygdx.game.views.GameScreen;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.NewGameScreen;
import com.mygdx.game.views.PreferenceScreen;
import com.mygdx.game.views.LoadingScreen;
import com.mygdx.game.views.SinglePlayerSetupScreen;

import java.util.ArrayList;

public class Andor extends Game {
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private PreferenceScreen preferenceScreen;
	private NewGameScreen newGameScreen;
	private SinglePlayerSetupScreen newSingleScreen;
	private ChooseHeroScreen chooseHeroScreen;
	private GameScreen gameScreen;

	private AppPreferences preferences;

	public SpriteBatch batch;
	public Skin skin;
	public Texture menuScreenBG;
	public Texture andorMenu;

	public final static int MENU = 0;
	public final static int PREFERENCE = 1;
	public final static int NEWGAME = 2;
	public final static int SINGLESETUP = 3;
	public final static int CHOOSEHERO = 4;
	public final static int GAME = 5;


	private int numOfPlayers;
	private int difficulty;

	private int readyPlayers;
	private ArrayList<Hero> claimedHeroes;


	public void setUpSinglePlayer(int numOfPlayers, int difficulty) {
		this.numOfPlayers = numOfPlayers;
		this.readyPlayers = 0;
		this.difficulty = difficulty;
	}

	public int getNumOfPlayers() {
		return this.numOfPlayers;
	}

	public int getReadyPlayers() {
		return this.readyPlayers;
	}

	public String getDifficulty() {
		if (this.difficulty == -1) {
			return "Easy";
		} else {
			return "Hard";
		}
	}

	public void changeScreen(int screen)
	{
		switch(screen)
		{
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;

			case PREFERENCE:
				if (preferenceScreen == null) preferenceScreen = new PreferenceScreen(this);
				this.setScreen(preferenceScreen);
				break;

			case NEWGAME:
				if (newGameScreen == null) newGameScreen = new NewGameScreen(this);
				this.setScreen(newGameScreen);
				break;

            case SINGLESETUP:
                if (newSingleScreen == null) newSingleScreen = new SinglePlayerSetupScreen(this);
                this.setScreen(newSingleScreen);
                break;

			case CHOOSEHERO:
				if (chooseHeroScreen == null) chooseHeroScreen = new ChooseHeroScreen(this);
				this.setScreen(chooseHeroScreen);
				break;

			case GAME:
				if (gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;

		}
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		menuScreenBG = new Texture(Gdx.files.internal("background/andor_main_bg.jpg"));
		andorMenu = new Texture(Gdx.files.internal("background/andormenu.jpg"));
		skin = new Skin(Gdx.files.internal("skin/Shadeui/uiskin.json"));

		preferences = new AppPreferences();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public AppPreferences getPreferences() {
		return this.preferences;
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
