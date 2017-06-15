package com.upc.desarrollo.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.helpers.Utils;

import utils.objects.PhysicsObject;

/**
 * Created by profesores on 5/24/17.
 */

public abstract class InteractiveTiledObject extends PhysicsObject {

protected TiledMap tiledMap;
    protected Rectangle bounds;
    protected Fixture fixture;
    public InteractiveTiledObject(World world, TextureAtlas textureAtlas, TiledMap map,
                                    Rectangle bounds){
        super(world, textureAtlas, new Vector2());
        tiledMap = map;
        this.bounds = bounds;
        define();
    }

    private void define(){
        BodyDef bodyDef = new BodyDef();
        float width, height, positionX, positionY;
        positionX = Utils.convertPixelsToMeters(
                bounds.getX() + bounds.getWidth()/2);
        positionY = Utils.convertPixelsToMeters(
                bounds.getY()  + bounds.getHeight()/2);
        width = Utils.convertPixelsToMeters(bounds.getWidth()/2f);
        height = Utils.convertPixelsToMeters(bounds.getHeight() / 2f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(positionX, positionY);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
    }

    @Override
    public void defineBody() {}

    public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer =
                    (TiledMapTileLayer)tiledMap.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * Config.PPM/16),
                             (int)(body.getPosition().y * Config.PPM/16));
    }

}
