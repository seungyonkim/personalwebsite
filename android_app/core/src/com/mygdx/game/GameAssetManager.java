package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {

    public final AssetManager manager = new AssetManager();

    // textures
    public final String playerImage = "images/player.png";
    public final String enemyImage = "images/enemy.png";

    // sounds
    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";

    // music
    public final String playingSong = "music/Rolemusic_-_pl4y1ng.mp3";

    // skin
    public final String skin = "skin/comic-ui.json";

    public void queueAddSkin() {
        SkinParameter params = new SkinParameter("skin/comic-ui.atlas");
        manager.load(skin, Skin.class, params);
    }

    public void queueAddMusic() {
        manager.load(playingSong, Music.class);
    }

    public void queueAddSounds() {
        manager.load(boingSound, Sound.class);
        manager.load(pingSound, Sound.class);
    }

    public void queueAddImages() {
        manager.load(playerImage, Texture.class);
        manager.load(enemyImage, Texture.class);
    }

    // a small set of images used by the loading screen
    public void queueAddLoadingImages() {

    }

}
