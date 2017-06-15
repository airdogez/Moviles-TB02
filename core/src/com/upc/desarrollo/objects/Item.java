package com.upc.desarrollo.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.upc.desarrollo.helpers.Utils;

import utils.objects.PhysicsObject;

/**
 * Created by profesores on 6/7/17.
 */

public abstract class Item extends PhysicsObject {

    public Item(World world, TextureAtlas textureAtlas, Vector2 position){
        super(world,textureAtlas,position);
        setBounds(getX(),getY(), Utils.convertPixelsToMeters(16f),
                                Utils.convertPixelsToMeters(16f));
    }

    public abstract void collect(Mario mario);

    @Override
    public void update(float dt) {
        if(!destroyed && setToDestroy){
            world.destroyBody(body);
            destroyed = true;
        }
        super.update(dt);
    }
}
