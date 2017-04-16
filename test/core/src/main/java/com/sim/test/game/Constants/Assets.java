package com.sim.test.game.Constants;


import com.badlogic.gdx.Gdx;
import com.sim.test.game.Main;

public enum Assets {

    PLAYER("assets/Sprites/newPlayers/TexturePack/playerData.txt"),
    MAP("assets/Maps/Redtown/Map.tmx");


//  PLAYER("android/assets/Sprites/newPlayers/TexturePack/playerData.txt"),
//  MAP("android/assets/Maps/Redtown/Map.tmx");

    private String local;


    Assets(String local) {
        this.local = local;
    }

    public String localName() {
       // final String assetFolder = "android/assets/";
        if(Main.isAndroidApp) {
            return local;
        }
        else {
            return Gdx.files.internal(local).file().getAbsolutePath();
        }
    }

}


