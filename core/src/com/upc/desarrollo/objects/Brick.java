package com.upc.desarrollo.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.CustomAssetManager;
import com.upc.desarrollo.screens.Game;

/**
 * Created by profesores on 5/24/17.
 */

public class Brick extends InteractiveTiledObject {
    public Brick(World world, TextureAtlas textureAtlas, TiledMap map, Rectangle bounds) {
        super(world, textureAtlas, map, bounds);
        this.fixture.setUserData(this);
        setCategoryFilter(Config.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(Config.NOTHING_BIT);
        Game.instance.playSound(CustomAssetManager.BREAK_BLOCK_BUMP);
        getCell().setTile(null);
    }
}
