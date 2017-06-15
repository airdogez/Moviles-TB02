package com.upc.desarrollo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Luis on 23/05/2017.
 */

public class CustomAssetManager {

    public static AssetManager manager;
    public static final String MARIO_MUSIC = "audio/music/mario_music.ogg";
    public static final String COIN_SOUND = "audio/sounds/coin.wav";
    public static final String BUMP_SOUND = "audio/sounds/bump.wav";
    public static final String BREAK_BLOCK_BUMP = "audio/sounds/breakblock.wav";
    public static final String POWER_UP = "audio/sounds/powerup.wav";
    public static final String POWER_DOWN = "audio/sounds/powerdown.wav";
    public static final String DEAD = "audio/sounds/mariodie.wav";

    public static void init(){
        manager = new AssetManager();
        manager.load(MARIO_MUSIC, Music.class);
        manager.load(COIN_SOUND, Sound.class);
        manager.load(BUMP_SOUND, Sound.class);
        manager.load(BREAK_BLOCK_BUMP, Sound.class);
        manager.load(POWER_UP, Sound.class);
        manager.load(DEAD, Sound.class);
        manager.load(POWER_DOWN, Sound.class);
        manager.finishLoading();
    }

    public static void dispose(){
        manager.dispose();
    }
}
