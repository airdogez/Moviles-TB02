package utils.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by profesores on 5/24/17.
 */

public class PhysicsState extends State {

    protected World world;
    protected Vector2 gravity;
    protected Box2DDebugRenderer box2DDebugRenderer;
    public PhysicsState(SpriteBatch spriteBatch) {
        super(spriteBatch);
        gravity = new Vector2(0,-10f);
        world = new World(gravity,true);
        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //box2DDebugRenderer.render(world,camera.combined);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        world.step(1/60f,1,2);
    }
}
