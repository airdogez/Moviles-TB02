package com.upc.desarrollo.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.helpers.Utils;

import utils.objects.PhysicsObject;

/**
 * Created by profesores on 5/24/17.
 */

public abstract class Enemy extends PhysicsObject {
    public Enemy(World world, TextureAtlas textureAtlas, Vector2 position) {
        super(world, textureAtlas, position);
    }

    @Override
    public void defineBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Utils.convertPixelsToMeters(5f));
        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = Config.ENEMY_BIT;
        fixtureDef.filter.maskBits = Config.MARIO_BIT |
                                Config.GROUND_BIT | Config.OBJECT_BIT|
                                Config.ENEMY_BIT;
        body.createFixture(fixtureDef).setUserData(this);
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(Utils.convertPixelsToMeters(-2),
                        Utils.convertPixelsToMeters(7)) ,
                new Vector2(Utils.convertPixelsToMeters(2),
                        Utils.convertPixelsToMeters(7))
        );

        fixtureDef.shape = head;
        fixtureDef.restitution = 0.5f;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Config.ENEMY_HEAD_BIT;
        fixtureDef.filter.maskBits = Config.MARIO_BIT;
        body.createFixture(fixtureDef).setUserData(this);
    }
    public abstract void onHeadHit();

    public abstract void onEnemyHit(Enemy enemy);
}
