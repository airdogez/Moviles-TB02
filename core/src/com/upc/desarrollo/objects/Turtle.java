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

public class Turtle extends Enemy {
    private Animation walkAnimation;
    public final static int LEFT_SPEED = -2;
    public final static int RIGHT_SPEED = 2;
    public enum Status{
        WALKING,
        STANDING_SHELL,
        MOVING_SHELL
    }
    public Status current;
    private Status prev;
    private TextureRegion shell;
    private Array<TextureRegion> frames;
    public Turtle(World world, TextureAtlas textureAtlas, Vector2 position) {
        super(world, textureAtlas, position);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(textureAtlas.findRegion("turtle"),i*16,0,16,24));
        }
        shell = new TextureRegion(textureAtlas.findRegion("turtle"),64,0,16,24);
        current =  Status.WALKING;
        prev = current;
        walkAnimation = new Animation(0.5f, frames);
        setBounds(getX(),getY(), Utils.convertPixelsToMeters(16f),Utils.convertPixelsToMeters(16f));
        velocity.x = -0.7f;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        stateTime+=dt;
        setPosition(body.getPosition().x - getWidth() * 0.5f,
                body.getPosition().y - getHeight() * 0.5f);
        setRegion(getCurrentRegion(dt));
        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }

    public void kick(int direction){
        velocity.x = direction;
        current = Status.MOVING_SHELL;
    }

    @Override
    public TextureRegion getCurrentRegion(float dt) {
        TextureRegion region;
        switch (current){
            case MOVING_SHELL:
            case STANDING_SHELL:
                region = shell;
                break;
            default:
            case WALKING:
                region = walkAnimation.getKeyFrame(stateTime,true);
                break;
        }

        if(getVelocityX()<0 && region.isFlipX()){
            region.flip(true,false);
        }
        if(getVelocityX()>0  && !region.isFlipX()){
            region.flip(true,false);
        }

        stateTime = current == prev ? stateTime+dt : 0;
        prev = current;
        return region;
    }

    @Override
    public void onHeadHit() {
        if(current!=Status.STANDING_SHELL){
            current = Status.STANDING_SHELL;
            velocity.x = 0;
        }
    }

    @Override
    public void onEnemyHit(Enemy enemy) {
        if(current != Status.MOVING_SHELL){
            reverseVelocity(true,false);
        }
    }
}
