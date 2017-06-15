package com.upc.desarrollo.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.helpers.Utils;

import java.util.Random;

/**
 * Created by aqws3 on 6/14/17.
 */

public class ItemGoomba extends Item {


    public ItemGoomba(World world, TextureAtlas textureAtlas, Vector2 position) {
        super(world, textureAtlas, position);
        setRegion(textureAtlas.findRegion("goomba"),0,0,16,16);
        setBounds(getX(), getY(), Utils.convertPixelsToMeters(16f), Utils.convertPixelsToMeters(16f));
        Random r = new Random();
        float velX = r.nextBoolean() ? 0.5f : -0.5f;
        velocity = new Vector2(velX,0);
    }

    @Override
    public void update(float dt) {
        if(!destroyed){
            setPosition(body.getPosition().x - getWidth()/2 ,
                    body.getPosition().y - getHeight()/2
            );
            velocity.y = body.getLinearVelocity().y;
            body.setLinearVelocity(velocity);
        }
        super.update(dt);
    }

    @Override
    public void collect(Mario mario) {
        mario.addScore(-10);
        destroy();

    }

    @Override
    public void defineBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(),getY());
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Utils.convertPixelsToMeters(5f));
        fixtureDef.filter.categoryBits = Config.ITEM_BIT;
        fixtureDef.filter.maskBits = Config.MARIO_BIT | Config.COIN_BIT
                | Config.BRICK_BIT | Config.GROUND_BIT
                | Config.OBJECT_BIT;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

    }
}