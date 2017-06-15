package utils.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by profesores on 5/24/17.
 */

public class GameObject extends Sprite implements Disposable{

    protected Vector2 position;
    protected boolean movingRight;
    protected TextureAtlas textureAtlas;
    protected float stateTime;
    public GameObject(){
        stateTime = 0f;
        movingRight = false;
    }

    public GameObject(TextureAtlas textureAtlas, Vector2 position){
        this.textureAtlas = textureAtlas;
        this.position = position;
        setPosition(this.position.x, this.position.y);
        movingRight = false;
        stateTime = 0f;
    }

    public void update(float dt){

    }

    public TextureRegion getCurrentRegion(float dt){
        return new TextureRegion();
    }

    @Override
    public void dispose() {

    }
}
