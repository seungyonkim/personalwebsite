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
import com.mygdx.game.views.MultiGameScreen;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.NewGameScreen;
import com.mygdx.game.views.PreferenceScreen;
import com.mygdx.game.views.LoadingScreen;
import com.mygdx.game.views.SinglePlayerSetupScreen;
import com.mygdx.game.views.MultiPlayerSetupScreen;
import com.mygdx.game.views.ChatScreen;


import java.util.ArrayList;
import java.util.HashMap;

import io.socket.client.Socket;

public class Andor extends Game {
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private PreferenceScreen preferenceScreen;
	private NewGameScreen newGameScreen;
	private SinglePlayerSetupScreen newSingleScreen;
	private MultiPlayerSetupScreen newMultiScreen;

	private ChooseHeroScreen chooseHeroScreen;
	private GameScreen gameScreen;
	private MultiGameScreen multiGameScreen;

	private ChatScreen chatScreen;

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
	public final static int MULTISETUP = 6;
	public final static int MULTIGAME = 7;
	public final static int CHAT = 8;

	public int decider= 0;

	private Socket socket;
	private Board gameBoard;

	private Hero currentTurn; // hero whose turn it is

	private int numOfPlayers;
	private int difficulty; // -1 for easy, 1 for hard

	private int readyPlayers; // number of ready players at singleplayer game setup stage
	private ArrayList<Hero> playerHeroes; // in order (in real life clockwise direction)
	private Hero rooster; // player who finished the day first; gets to start the next day first
	private boolean gameOver = false;
//	private int playersFinishedDay; // number of players that have finished the day
	private ArrayList<Hero> finishedHeroes;
	private HashMap<String, Boolean> availableHeroes; // to keep track of "taken" heroes at singleplayer game setup stage

	private HashMap<String,String> friendlyPlayers; // hash map of the players and their id for the server
	private Hero myHero;

	public final float UPDATE_TIME =1/60f;
	public float timer;


//    public void printClaimedHeroes() {
//    	System.out.println("Total "+playerHeroes.size()+" heroes created.");
//        for (int i = 0; i < this.playerHeroes.size(); i++) {
//			int rank = playerHeroes.get(i).getRank();
//        	if (rank == 14) System.out.println("Warrior");
//        	else if (rank == 25) System.out.println("Archer");
//			else if (rank == 34) System.out.println("Wizard");
//			else if (rank == 7) System.out.println("Dwarf");
//		}
//    }

	public void setUpSinglePlayer(int numOfPlayers, int difficulty) {
		// set up the game for singleplayer mode
		this.numOfPlayers = numOfPlayers;
		this.readyPlayers = 0;
		this.difficulty = difficulty;
	}
	public void setUpMultiPlayer(int numOfPlayers, int difficulty) {
		this.numOfPlayers = numOfPlayers;
		this.readyPlayers = 0;
		this.difficulty = difficulty;
	}
	public Socket getSocket(){return socket;}
	public int getNumOfPlayers() {
		return this.numOfPlayers;
	}

	public int getReadyPlayers() {
		return this.readyPlayers;
	}


	public int getDifficulty() {
//		if (this.difficulty == -1) {
//			return "Easy";
//		} else {
//			return "Hard";
//		}
		return this.difficulty; // -1 for easy, 1 for hard
	}

	public HashMap<String, Boolean> getAvailableHeroes() {
        return availableHeroes;
    }

    public void disableHero(String hero) {
		// someone has picked this hero, so put it in the "taken" list so that no one else can pick it

        availableHeroes.put(hero, false);
    }

    public void enableHero(String hero) {
		// enable the hero for pick (the back button has been pressed)
        availableHeroes.put(hero, true);
    }

    public void selectHero(Hero hero) {
		// player has picked a hero
		playerHeroes.add(hero);
	    readyPlayers++;
    }

    public Hero removeLastSelectedHero() {
		// back button has been pressed in the process of picking heroes
        Hero lastHero = playerHeroes.get(playerHeroes.size()-1);
		playerHeroes.remove(playerHeroes.size()-1);
	    readyPlayers--;
	    return lastHero;
    }

    public void createNewBoard() {
		// create new board for a new game
		gameBoard = new Board(playerHeroes, this.difficulty);
//		System.out.println("NEW BOARD CREATED");
		currentTurn = playerHeroes.get(0);
//		playersFinishedDay = 0;
		finishedHeroes = new ArrayList<Hero>();
	}

    public void createNewBoard(int difficulty) {
        // create new board for a new game
		this.difficulty = difficulty;
        gameBoard = new Board(playerHeroes, this.difficulty);
//		System.out.println("NEW BOARD CREATED");
        currentTurn = playerHeroes.get(0);
//		playersFinishedDay = 0;
        finishedHeroes = new ArrayList<Hero>();
    }

	public Hero whoseTurn() {
		// returns whose turn it is right now
        return currentTurn;
    }
    public Hero getMyHero(){
		if (myHero == null) {
			System.out.println("NULL MY HERO");
			return myHero;
		}
    	else{
				return myHero;

			}

	}
    public void setMyHero(Hero hero){myHero =hero;}

    public void nextTurn() {
		for (int i = 0; i < playerHeroes.size(); i++) {
			if (currentTurn.getTypeOfHero() == playerHeroes.get(i).getTypeOfHero()) {
				if (i+1 == playerHeroes.size()) {
					currentTurn = playerHeroes.get(0);
				} else {
					currentTurn = playerHeroes.get(i+1);
					return;
				}
			}
		}
		while (!currentTurn.getCanPlay() && !finishedHeroes.contains(currentTurn)) {
			finishedHeroes.add(currentTurn);
			// get the next turn hero
			for (int i = 0; i < playerHeroes.size(); i++) {
				if (currentTurn.getTypeOfHero() == playerHeroes.get(i).getTypeOfHero()) {
					if (i+1 == playerHeroes.size()) {
						currentTurn = playerHeroes.get(0);
					} else {
						currentTurn = playerHeroes.get(i+1);
						return;
					}
				}
			}
		}

	}

//	public int getPlayersFinishedDay() {
//		return this.playersFinishedDay;
//	}

	public ArrayList<Hero> getFinishedHeroes() {
		return this.finishedHeroes;
	}

	public void finishDay() { // a player finished day
        if (rooster == null) {
            // if no one else finished the day yet, current player starts the next day first
            rooster = currentTurn;
        }
        // this hero can no longer make a move until the next day
        currentTurn.disablePlay();
        // increment number of players that finished the day
//		playersFinishedDay++;
		if (!finishedHeroes.contains(currentTurn)) {
			finishedHeroes.add(currentTurn);
		}
        // continue to next turn
        this.nextTurn();
    }

	public void endDay() { // all players finished day
		// update board for a new day
	    gameBoard.newDay();
	    for (Hero player : playerHeroes) {
	    	player.enablePlay();
	    	player.resetHours();
		}
	    // find the player to go first for the new day
	    for (Hero player : playerHeroes) {
	        if (rooster == player) {
	            currentTurn = player;
            }
        }
	    // reset the rooster to nobody
	    rooster = null;
	    finishedHeroes.clear();
    }

	public Board getGameBoard() {
    	return gameBoard;
	}

	public ArrayList<Hero> getPlayerHeroes() {
    	return playerHeroes;
	}
	public HashMap<String,String> getFriendlyPlayers() {
		return friendlyPlayers;
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
			case MULTISETUP:
				if (newMultiScreen == null) newMultiScreen = new MultiPlayerSetupScreen(this);
				this.setScreen(newMultiScreen);
				break;

			case CHOOSEHERO:
				if (chooseHeroScreen == null) chooseHeroScreen = new ChooseHeroScreen(this);
				this.setScreen(chooseHeroScreen);
				break;

			case GAME:
				if (gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;
			case MULTIGAME:
				if (multiGameScreen == null) multiGameScreen = new MultiGameScreen(this);
				this.setScreen(multiGameScreen);
				break;

			case CHAT:
				if (chatScreen == null) chatScreen = new ChatScreen(this);
				this.setScreen(chatScreen);
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
		friendlyPlayers = new HashMap<String, String>();
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
