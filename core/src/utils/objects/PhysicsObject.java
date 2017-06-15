package utils.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by profesores on 5/24/17.
 */

public abstract class PhysicsObject extends GameObject {
    protected World world;
    protected Body body;
    public Vector2 velocity;
    protected boolean setToDestroy;
    protected boolean destroyed;
    public PhysicsObject(World world){
        this.world = world;
        setVelocity(new Vector2());
        defineBody();
    }

    public PhysicsObject(World world, TextureAtlas textureAtlas,
                         Vector2 position){
        super(textureAtlas,position);
        this.world = world;
        this.setVelocity(new Vector2());
        defineBody();
    }

    @Override
    public void draw(Batch batch) {
        if(!destroyed){
            super.draw(batch);
        }
    }

    public void reverseVelocity(boolean x,boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

    public void destroy(){
        setToDestroy = true;
    }

    public abstract void defineBody();

    public float getVelocityX(){
        return body.getLinearVelocity().x;
    }

    public float getVelocityY(){
        return body.getLinearVelocity().y;
    }

    public Body getBody(){
        return body;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
}
