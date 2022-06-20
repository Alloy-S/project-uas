package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.Map;

public class Assets {
    public AssetManager manager;
    public Map mainTiledMap;

    public void load(){
        if(manager == null){
            manager = new AssetManager();
        }
        manager.load("Movement.pack",  TextureAtlas.class);
        manager.load("stepSound.wav",  Sound.class);
    }
}
