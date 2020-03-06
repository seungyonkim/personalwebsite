package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.preference.AppPreferences;
import com.mygdx.game.views.GameScreen;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.PreferenceScreen;
import com.mygdx.game.views.LoadingScreen;

public class Andor extends Game {
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private PreferenceScreen preferenceScreen;
	private GameScreen gameScreen;
	private AppPreferences preferences;

	public final static int MENU = 0;
	public final static int PREFERENCE = 1;
	public final static int GAME = 2;



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

			case GAME:
				if (gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;

		}
	}

	@Override
	public void create () {
		preferences = new AppPreferences();
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	public AppPreferences getPreferences() {
		return this.preferences;
	}

	@Override
	public void dispose () {
	}
}
