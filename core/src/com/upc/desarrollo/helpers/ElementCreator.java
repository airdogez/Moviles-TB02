package com.upc.desarrollo.helpers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.objects.Brick;
import com.upc.desarrollo.objects.Coin;
import com.upc.desarrollo.objects.Enemy;
import com.upc.desarrollo.objects.Goomba;
import com.upc.desarrollo.objects.Turtle;

/**
 * Created by profesores on 5/24/17.
 */

public class ElementCreator {
    public Array<Enemy> enemies;
    public ElementCreator(World world, TiledMap map, TextureAtlas textureAtlas){
        BodyDef bodyDef = new BodyDef();
        TextureAtlas atlas = textureAtlas;
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        enemies = new Array<Enemy>();
        Body body;
        float positionX , positionY , width, height;

        for (MapObject mapObject :
                map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            positionX =Utils.convertPixelsToMeters(
                    rectangle.getX() +rectangle.getWidth()/2f);
            positionY = Utils.convertPixelsToMeters(
                    rectangle.getY() + rectangle.getHeight()/2f);
            bodyDef.position.set(positionX,positionY);
            width = Utils.convertPixelsToMeters(rectangle.getWidth()/2f);
            height = Utils.convertPixelsToMeters(rectangle.getHeight()/2f);
            shape.setAsBox(width, height);
            body = world.createBody(bodyDef);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Config.GROUND_BIT;
            body.createFixture(fixtureDef);
        }

        for (MapObject mapObject :
                map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            positionX =Utils.convertPixelsToMeters(
                    rectangle.getX() +rectangle.getWidth()/2f);
            positionY = Utils.convertPixelsToMeters(
                    rectangle.getY() + rectangle.getHeight()/2f);
            bodyDef.position.set(positionX,positionY);
            width = Utils.convertPixelsToMeters(rectangle.getWidth()/2f);
            height = Utils.convertPixelsToMeters(rectangle.getHeight()/2f);
            shape.setAsBox(width, height);
            body = world.createBody(bodyDef);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Config.OBJECT_BIT;
            body.createFixture(fixtureDef);
        }

        for (MapObject mapObject :
                map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
            new Coin(world,atlas,map,rectangle);
        }

        for (MapObject mapObject :
                map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
            new Brick(world,atlas,map,rectangle);
        }

        for (MapObject mapObject :
                map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
            positionX = Utils.convertPixelsToMeters
                    (rectangle.getX() + rectangle.getWidth()/2f);
            positionY = Utils.convertPixelsToMeters (
                    rectangle.getY() + rectangle.getHeight()/2f);
            Vector2 position = new Vector2(
                    positionX,positionY
            );
            enemies.add(new Goomba(world,textureAtlas,position));

        }

        for (MapObject mapObject :
                map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
            positionX = Utils.convertPixelsToMeters
                    (rectangle.getX() + rectangle.getWidth()/2f);
            positionY = Utils.convertPixelsToMeters (
                    rectangle.getY() + rectangle.getHeight()/2f);
            Vector2 position = new Vector2(
                    positionX,positionY
            );
            enemies.add(new Turtle(world,textureAtlas,position));

        }

    }

}
