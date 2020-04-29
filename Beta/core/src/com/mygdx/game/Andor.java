package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.board.Board;
import com.mygdx.game.character.Archer;
import com.mygdx.game.character.Dwarf;
import com.mygdx.game.character.Warrior;
import com.mygdx.game.character.Wizard;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.character.Hero;
import com.mygdx.game.preference.AppPreferences;
import com.mygdx.game.views.ChooseHeroScreen;
import com.mygdx.game.views.EquipmentScreen.EquipmentScreen;
import com.mygdx.game.views.EquipmentScreen.EquipmentScreenArcher;
import com.mygdx.game.views.EquipmentScreen.EquipmentScreenDwarf;
import com.mygdx.game.views.EquipmentScreen.EquipmentScreenWarrior;
import com.mygdx.game.views.EquipmentScreen.EquipmentScreenWizard;
import com.mygdx.game.views.EquipmentScreen.UseFalconScreen;
import com.mygdx.game.views.GameScreen;
import com.mygdx.game.views.LoadGameScreen;
import com.mygdx.game.views.LoadLosingGameScreen;
import com.mygdx.game.views.MerchantScreen;
import com.mygdx.game.views.MultiGameScreen;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.NewGameScreen;
import com.mygdx.game.views.PreferenceScreen;
import com.mygdx.game.views.LoadingScreen;
import com.mygdx.game.views.SinglePlayerSetupScreen;
import com.mygdx.game.views.MultiPlayerSetupScreen;
import com.mygdx.game.views.ChatScreen;
import com.mygdx.game.views.BattleScreen;



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

	private LoadGameScreen loadGameScreen;
	private LoadLosingGameScreen loadLosingGameScreen;

	private ChooseHeroScreen chooseHeroScreen;
	private GameScreen gameScreen;
	private MultiGameScreen multiGameScreen;

	private BattleScreen battleScreen;

	private ChatScreen chatScreen;

	private AppPreferences preferences;
	private MerchantScreen merchantScreen;

	private EquipmentScreenWarrior equipmentScreenWarrior;
	private EquipmentScreenArcher equipmentScreenArcher;
	private EquipmentScreenWizard equipmentScreenWizard;
	private EquipmentScreenDwarf equipmentScreenDwarf;
	private UseFalconScreen useFalconScreen;

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

	public final static int MERCHANT = 9;
	public final static int EQUIPMENT_WARRIOR = 10;
	public final static int EQUIPMENT_ARCHER = 11;
	public final static int EQUIPMENT_WIZARD = 12;
	public final static int EQUIPMENT_DWARF = 13;
	public final static int USE_FALCON =15;
	public final static int BATTLE =14;


	public final static int LOADGAME = 16;
	public final static int LOADLOSEGAME = 17;

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

	private ArrayList<Hero> heroBattling;
	private Monster monsterBattling;
	public boolean wonLastBattle;


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

	public void setUpMultiPlayer(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
		this.readyPlayers = 0;
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

    public void addHeroBattling(Hero hero){
		if(heroBattling == null)
			heroBattling = new ArrayList<Hero>();
		heroBattling.add(hero);
	}

	public ArrayList<Hero> getHeroBattling() {
		return heroBattling;
	}

	public void addMonsterBattling(Monster monster){

		monsterBattling = (monster);
	}

	public Monster getMonsterBattling() {
		return monsterBattling;
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

    public void loadBoard(char option) {
		gameBoard = new Board(playerHeroes, option);
		currentTurn = playerHeroes.get(0);
		finishedHeroes = new ArrayList<Hero>();
//		if (option == 'c') { // Losing game
//			for (Hero player : playerHeroes) {
//				if (player instanceof Warrior) {
//					System.out.println("Moving warrior from " + player.getPosition() + " to 46.");
//					player.moveTo(gameBoard.getRegion(player.getPosition()), gameBoard.getRegion(46));
//				} else if (player instanceof Archer) {
//					System.out.println("Moving archer from " + player.getPosition() + " to 39.");
//					player.moveTo(gameBoard.getRegion(player.getPosition()), gameBoard.getRegion(39));
//				} else if (player instanceof Dwarf) {
//					System.out.println("Moving dwarf from " + player.getPosition() + " to 48.");
//					player.moveTo(gameBoard.getRegion(player.getPosition()), gameBoard.getRegion(48));
//				} else if (player instanceof Wizard) {
//					System.out.println("Moving wizard from " + player.getPosition() + " to 41.");
//					player.moveTo(gameBoard.getRegion(player.getPosition()), gameBoard.getRegion(41));
//				}
//			}
//		}
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
		System.out.println("A hero finished day.");
//		if (this.finishedHeroes.size() == this.playerHeroes.size()) {
//			System.out.println("Ending Day");
//			endDay();
//		}
    }

	public void endDay() { // all players finished day
		// update board for a new day
	    gameBoard.newDay();
	    for (Hero player : playerHeroes) {
	    	System.out.println("Resetting player.");
	    	player.enablePlay();
	    	player.resetHours();
//	    	System.out.println("Player now has hours: "+player.getHours());
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
	    EquipmentScreen.setUsedFalconToday(false);
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

			case LOADGAME:
				if (loadGameScreen == null) loadGameScreen = new LoadGameScreen(this);
				this.setScreen(loadGameScreen);
				break;

			case LOADLOSEGAME:
				if (loadLosingGameScreen == null) loadLosingGameScreen = new LoadLosingGameScreen(this);
				this.setScreen(loadLosingGameScreen);
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
			case BATTLE:
				if(battleScreen == null) battleScreen = new BattleScreen((this));
				this.setScreen(battleScreen);
				break;


			case MERCHANT:
				if (merchantScreen == null) merchantScreen = new MerchantScreen(this);
				this.setScreen(merchantScreen);
				break;

			case EQUIPMENT_WARRIOR:
				if (equipmentScreenWarrior == null) equipmentScreenWarrior = new EquipmentScreenWarrior(this);
				this.setScreen(equipmentScreenWarrior);
				break;

			case EQUIPMENT_ARCHER:
				if (equipmentScreenArcher == null) equipmentScreenArcher = new EquipmentScreenArcher(this);
				this.setScreen(equipmentScreenArcher);
				break;
			case EQUIPMENT_WIZARD:
				if (equipmentScreenWizard == null) equipmentScreenWizard= new EquipmentScreenWizard(this);
				this.setScreen(equipmentScreenWizard);
				break;
			case EQUIPMENT_DWARF:
				if (equipmentScreenDwarf == null) equipmentScreenDwarf= new EquipmentScreenDwarf(this);
				this.setScreen(equipmentScreenDwarf);
				break;
			case USE_FALCON:
				if (useFalconScreen == null) useFalconScreen= new UseFalconScreen(this);
				this.setScreen(useFalconScreen);
				break;

		}
	}

	public EquipmentScreenWarrior getEquipmentScreenWarrior(){
		if(equipmentScreenWarrior==null) {
			equipmentScreenWarrior = new EquipmentScreenWarrior(this);
		}
		return  equipmentScreenWarrior;
	}
	public EquipmentScreenArcher getEquipmentScreenArcher(){
		if(equipmentScreenArcher==null){
			equipmentScreenArcher = new EquipmentScreenArcher(this);
		}
		return equipmentScreenArcher;
	}
	public EquipmentScreenWizard getEquipmentScreenWizard(){
		if(equipmentScreenWizard == null){
			equipmentScreenWizard = new EquipmentScreenWizard(this);
		}
		return equipmentScreenWizard;
	}
	public EquipmentScreenDwarf getEquipmentScreenDwarf(){
		if(equipmentScreenDwarf==null){
			equipmentScreenDwarf = new EquipmentScreenDwarf(this);
		}
		return equipmentScreenDwarf;
	}
	public MerchantScreen getMerchantScreen(){
		return merchantScreen;
	}

	public UseFalconScreen getUseFalconScreen(){
		if(useFalconScreen==null){
			useFalconScreen = new UseFalconScreen(this);
		}
		return useFalconScreen;
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
