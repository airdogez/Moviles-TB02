package com.upc.desarrollo.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.upc.desarrollo.helpers.Utils;

/**
 * Created by profesores on 5/24/17.
 */

public class Goomba extends Enemy {

    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private TextureRegion hitRegion;
    public Goomba(World world, TextureAtlas textureAtlas, Vector2 position) {
        super(world, textureAtlas, position);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(textureAtlas.findRegion("goomba"),i*16,0,16,16));
        }
        hitRegion = new TextureRegion(textureAtlas.findRegion("goomba"),32,0,16,16);
        walkAnimation = new Animation(0.5f, frames);
        setBounds(getX(),getY(), Utils.convertPixelsToMeters(16f),Utils.convertPixelsToMeters(16f));
        velocity.x = -0.7f;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        stateTime+=dt;
        if(setToDestroy  && !destroyed){
            destroyed = true;
            world.destroyBody(body);
            setRegion(hitRegion);
            stateTime = 0;
        }else if(!destroyed){
            setPosition(body.getPosition().x - getWidth() * 0.5f,
                    body.getPosition().y - getHeight() * 0.5f);
            setRegion(walkAnimation.getKeyFrame(stateTime,true));
            velocity.y = body.getLinearVelocity().y;
            body.setLinearVelocity(velocity);
        }
    }

    @Override
    public void onEnemyHit(Enemy enemy) {
        if(enemy instanceof Turtle && ((Turtle)enemy).current == Turtle.Status.MOVING_SHELL){
            destroy();
        }else{
            reverseVelocity(true,false);
        }
    }

    @Override
    public void onHeadHit() {
        destroy();
    }
}
