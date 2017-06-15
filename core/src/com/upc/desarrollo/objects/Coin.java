package com.upc.desarrollo.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.CustomAssetManager;
import com.upc.desarrollo.helpers.Utils;
import com.upc.desarrollo.screens.Game;

/**
 * Created by profesores on 5/24/17.
 */

public class Coin extends InteractiveTiledObject {

    public static TiledMapTileSet set;
    private final int BLANK_COIN = 28;
    public Coin(World world, TextureAtlas textureAtlas, TiledMap map, Rectangle bounds) {
        super(world, textureAtlas, map, bounds);
        set = map.getTileSets().getTileSet("tileset_gutter");
        setCategoryFilter(Config.COIN_BIT);
        this.fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        if(getCell().getTile().getId() == BLANK_COIN){
            Game.instance.playSound(CustomAssetManager.BUMP_SOUND);
        }else{
            getCell().setTile(set.getTile(BLANK_COIN));
            Game.instance.playSound(CustomAssetManager.COIN_SOUND);
            Game.instance.spawnItem(new ItemDef(new Vector2(body.getPosition().x,
            body.getPosition().y + Utils.convertPixelsToMeters(16)),Mushroom.class));

        }
    }
}
