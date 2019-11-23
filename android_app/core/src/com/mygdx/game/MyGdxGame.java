package com.mygdx.game;


import java.awt.SplashScreen;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Texture BackgroundMenu;
	public Texture BackgroundMain;
	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		BackgroundMenu = new Texture(Gdx.files.internal("andormenu.jpg")); //File from assets folder
		BackgroundMain = new Texture(Gdx.files.internal("andorboard.jpg")); //File from assets folder


		this.setScreen(new MainMenuScreen(this));

	}

	public void render() {
		super.render(); //important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}