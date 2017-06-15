package com.upc.desarrollo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.Main;
import com.upc.desarrollo.helpers.Utils;
import com.upc.desarrollo.screens.GameOver;

import utils.objects.PhysicsObject;

/**
 * Created by profesores on 5/24/17.
 */

public class Mario extends PhysicsObject {


    private boolean runGrowAnimation;

    public enum Status{FALLING,JUMPING, STANDING,RUNNING, DEAD,GROWING};
    public Status currentStatus;
    public Status prevStatus;
    private TextureRegion marioStand, bigMarioStand, marioDead;
    private Animation marioRunning, bigMarioRunning,marioGrowing;
    private TextureRegion marioJumping, bigMarioJumping;
    private Array<TextureRegion> frames;
    private boolean isDead, timeToRedifineMario, isBig, timeToDefineMario, isHit;
    private float hitTime = 3f;
    public static final String LITTLE_MARIO ="little_mario";
    public static final String BIG_MARIO ="big_mario";
    public int score = 0;

    public Mario(World world, TextureAtlas textureAtlas, Vector2 position) {
        super(world,textureAtlas, position);
        isDead = isBig = timeToDefineMario = timeToRedifineMario = false;
        currentStatus = Status.STANDING;
        prevStatus = currentStatus;
        marioStand = new TextureRegion(textureAtlas.findRegion(Mario.LITTLE_MARIO),0,0,16,16);
        setRegion(marioStand);
        setBounds(0,0,Utils.convertPixelsToMeters(16), Utils.convertPixelsToMeters(16));
        frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(textureAtlas.findRegion(Mario.LITTLE_MARIO), i * 16,0,16,16));
        }
        marioRunning = new Animation(0.1f,frames);
        marioJumping = new TextureRegion(textureAtlas.findRegion(Mario.LITTLE_MARIO),80,0,16,16);

        bigMarioStand = new TextureRegion(textureAtlas.findRegion(Mario.BIG_MARIO),0,0,16,32);
        bigMarioJumping = new TextureRegion(textureAtlas.findRegion(Mario.BIG_MARIO),80,0,16,32);

        frames.clear();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(textureAtlas.findRegion(Mario.BIG_MARIO), i * 16,0,16,32));
        }
        bigMarioRunning =new Animation(0.1f, frames);
        frames.clear();
        frames.add(new TextureRegion(textureAtlas.findRegion(Mario.BIG_MARIO), 240,0,16,32));
        frames.add(new TextureRegion(textureAtlas.findRegion(Mario.BIG_MARIO), 0,0,16,32));
        frames.add(new TextureRegion(textureAtlas.findRegion(Mario.BIG_MARIO), 240,0,16,32));
        frames.add(new TextureRegion(textureAtlas.findRegion(Mario.BIG_MARIO), 0,0,16,32));
        marioGrowing = new Animation(0.2f,frames);
        marioDead = new TextureRegion(textureAtlas.findRegion(LITTLE_MARIO),96,0,16,16);
    }


    public boolean isDead(){
        return isDead;
    }

    public void processInput(Vector2 padPosition) {
        if(currentStatus == Status.DEAD){
            return;
        }
        if( (Gdx.input.isKeyJustPressed(Input.Keys.UP) || padPosition.y > 0  ) &&
                getVelocityY() == 0f
                ){
            getBody().applyLinearImpulse(
                    new Vector2(0,4f),getBody().getWorldCenter(),true);
        }
        if( (Gdx.input.isKeyPressed(Input.Keys.LEFT) || padPosition.x < 0) &&
                getVelocityX() > -2f
                ){
            getBody().applyLinearImpulse(
                    new Vector2(-0.1f,0),getBody().getWorldCenter(),true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || padPosition.x >0 )
                && getVelocityX() < 2f
                ){
            getBody().applyLinearImpulse(
                    new Vector2(0.1f,0),getBody().getWorldCenter(),true);
        }
    }

    void addScore(int scoredelta){
        score += scoredelta;
    }

    void doubleScore(){
        score *= 2;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(isBig){
            setPosition(body.getPosition().x - getWidth() * 0.5f,
                    body.getPosition().y - getHeight() * 0.5f
                            + Utils.convertPixelsToMeters(6f));
        }else{
            setPosition(body.getPosition().x - getWidth() * 0.5f,
                    body.getPosition().y - getHeight() * 0.5f);
        }
        if(isHit){
            hitTime-=dt;
            if(hitTime<=0){
                hitTime = 0f;
                isHit = false;
            }
        }

        setRegion(getCurrentRegion(dt));
        if(getY() < -getHeight()){
            isDead = true;
        }
        if(timeToDefineMario){
            defineBigMario();
        }
        if(timeToRedifineMario){
            redifineMario();
        }
        if(score < 0){
            kill();
        }
    }

    public void hit(Enemy enemy) {
        if (enemy instanceof Turtle && ((Turtle) enemy).current
                == Turtle.Status.STANDING_SHELL) {
            Turtle turtle = (Turtle) enemy;
            turtle.kick(this.getX()< turtle.getX() ? Turtle.RIGHT_SPEED : Turtle.LEFT_SPEED);
        } else {
            if (!isHit) {
                /*isHit = true;
                if (isBig) {
                    isBig = false;
                    timeToRedifineMario = true;
                    setBounds(getX(), getY(), getWidth() * 0.5f, getHeight() * 0.5f);
                }
                else {
                    kill();
                }*/
            }
        }
    }

    private void defineBigMario(){
        Vector2 currentPosition = body.getPosition();
        world.destroyBody(body);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(currentPosition.add(0,Utils.convertPixelsToMeters(10)));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Utils.convertPixelsToMeters(6f));
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Config.MARIO_BIT;
        fixtureDef.filter.maskBits = Config.BRICK_BIT |
                Config.ENEMY_HEAD_BIT |
                Config.OBJECT_BIT |
                Config.COIN_BIT |
                Config.GROUND_BIT |
                Config.ITEM_BIT |
                Config.ENEMY_BIT;
        body.createFixture(fixtureDef).setUserData(this);
        shape.setPosition(new Vector2(0f,Utils.convertPixelsToMeters(-14f)));
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(Utils.convertPixelsToMeters(-2),
                        Utils.convertPixelsToMeters(14)) ,
                new Vector2(Utils.convertPixelsToMeters(2),
                        Utils.convertPixelsToMeters(14))
        );
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Config.MARIO_HEAD_BIT;
        body.createFixture(fixtureDef).setUserData(this);
        timeToDefineMario = false;
    }

    private void redifineMario(){
        Vector2 currentPosition = body.getPosition();
        world.destroyBody(body);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(currentPosition);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Utils.convertPixelsToMeters(5f));
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Config.MARIO_BIT;
        fixtureDef.filter.maskBits = Config.BRICK_BIT |
                Config.ENEMY_HEAD_BIT |
                Config.OBJECT_BIT |
                Config.COIN_BIT |
                Config.GROUND_BIT |
                Config.ITEM_BIT |
                Config.ENEMY_BIT;
        body.createFixture(fixtureDef).setUserData(this);
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(Utils.convertPixelsToMeters(-2),
                        Utils.convertPixelsToMeters(7)) ,
                new Vector2(Utils.convertPixelsToMeters(2),
                        Utils.convertPixelsToMeters(7))
        );
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Config.MARIO_HEAD_BIT;
        body.createFixture(fixtureDef).setUserData(this);
        timeToRedifineMario = false;
    }

    @Override
    public TextureRegion getCurrentRegion(float dt) {
        currentStatus = getStatus();
        TextureRegion region = new TextureRegion();
        switch (currentStatus){
            case GROWING:
                region = marioGrowing.getKeyFrame(stateTime);
                if(marioGrowing.isAnimationFinished(stateTime)){
                    runGrowAnimation = false;
                }
                break;
            case DEAD:
                region = marioDead;
                break;
            case RUNNING:
                region = isBig ? bigMarioRunning.getKeyFrame(stateTime,true) :
                                marioRunning.getKeyFrame(stateTime, true);
                break;
            case STANDING:
                region = isBig ? bigMarioStand : marioStand;
                break;
            case JUMPING:
                region = isBig ? bigMarioJumping :  marioJumping;
                break;
        }
        if((getVelocityX()<0 || !movingRight) && !region.isFlipX()){
            region.flip(true,false);
            movingRight = false;
        }
        if((getVelocityX()>0 || movingRight) && region.isFlipX()){
            region.flip(true,false);
            movingRight = true;
        }

        stateTime = currentStatus == prevStatus ? stateTime+dt : 0;
        prevStatus = currentStatus;
        return region;
    }

    public void kill(){
        isDead = true;
        Filter filter = new Filter();
        filter.maskBits = Config.NOTHING_BIT;
        for(Fixture fix:body.getFixtureList()){
            fix.setFilterData(filter);
        }
        body.applyLinearImpulse(new Vector2(0,4f),
                body.getWorldCenter(),true);

    }

    @Override
    public void defineBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(Utils.convertPixelsToMeters(75f),
                Utils.convertPixelsToMeters(100f));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Utils.convertPixelsToMeters(5f));
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Config.MARIO_BIT;
        fixtureDef.filter.maskBits = Config.BRICK_BIT |
                                        Config.ENEMY_HEAD_BIT |
                                     Config.OBJECT_BIT |
                                     Config.COIN_BIT |
                                    Config.ITEM_BIT |
                                     Config.GROUND_BIT |
                                    Config.ENEMY_BIT;
        body.createFixture(fixtureDef).setUserData(this);
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(Utils.convertPixelsToMeters(-2),
                Utils.convertPixelsToMeters(7)) ,
                new Vector2(Utils.convertPixelsToMeters(2),
                        Utils.convertPixelsToMeters(7))
        );

        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Config.MARIO_HEAD_BIT;
        body.createFixture(fixtureDef).setUserData(this);
    }

    public Status getStatus(){
        if(runGrowAnimation){
            return Status.GROWING;
        }
        else if(isDead){
            return Status.DEAD;
        }
        if(getVelocityY() > 0 ||
                (getVelocityY() < 0 && prevStatus == Status.JUMPING ) ){
            return Status.JUMPING;
        }else if(getVelocityX()!=0){
            return Status.RUNNING;
        }
        return Status.STANDING;
    }

    public void grow(){
        if(!isBig){
            runGrowAnimation = true;
            timeToDefineMario = true;
            isBig = true;
            setBounds(getX(),getY(), getWidth(), getHeight()*2f);
        }

    }
}
