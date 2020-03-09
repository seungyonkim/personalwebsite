package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.board.Board;
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
import java.util.HashMap;

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
	public Texture andorBoard;

	public final static int MENU = 0;
	public final static int PREFERENCE = 1;
	public final static int NEWGAME = 2;
	public final static int SINGLESETUP = 3;
	public final static int CHOOSEHERO = 4;
	public final static int GAME = 5;


	private Board gameBoard;

	private Hero currentTurn;

	private int numOfPlayers;
	private int difficulty;

	private int readyPlayers;
	private ArrayList<Hero> playerHeroes;
	private HashMap<String, Boolean> availableHeroes;


    public void printClaimedHeroes() {
    	System.out.println("Total "+playerHeroes.size()+" heroes created.");
        for (int i = 0; i < this.playerHeroes.size(); i++) {
			int rank = playerHeroes.get(i).getRank();
        	if (rank == 14) System.out.println("Warrior");
        	else if (rank == 25) System.out.println("Archer");
			else if (rank == 34) System.out.println("Wizard");
			else if (rank == 7) System.out.println("Dwarf");
		}
    }

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

    public HashMap<String, Boolean> getAvailableHeroes() {
        return availableHeroes;
    }

    public void disableHero(String hero) {
        availableHeroes.put(hero, false);
    }

    public void enableHero(String hero) {
        availableHeroes.put(hero, true);
    }

    public void selectHero(Hero hero) {
		playerHeroes.add(hero);
	    readyPlayers++;
    }

    public Hero removeLastSelectedHero() {
        Hero lastHero = playerHeroes.get(playerHeroes.size()-1);
		playerHeroes.remove(playerHeroes.size()-1);
	    readyPlayers--;
	    return lastHero;
    }

    public void createNewBoard() {
		gameBoard = new Board(playerHeroes);
//		System.out.println("NEW BOARD CREATED");
		currentTurn = playerHeroes.get(0);
	}

	public Hero whosTurn() {
        return currentTurn;
    }

	public Board getGameBoard() {
    	return gameBoard;
	}

	public ArrayList<Hero> getPlayerHeroes() {
    	return playerHeroes;
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
		andorBoard = new Texture(Gdx.files.internal("background/Andor_Board.jpg"));

		skin = new Skin(Gdx.files.internal("skin/Shadeui/uiskin.json"));

		playerHeroes = new ArrayList<Hero>();
		availableHeroes = new HashMap<String,Boolean>();
		availableHeroes.put("Warrior", true);
        availableHeroes.put("Archer", true);
        availableHeroes.put("Wizard", true);
        availableHeroes.put("Dwarf", true);


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
