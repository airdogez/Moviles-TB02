package utils.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Luis on 22/10/2016.
 */

public abstract class State implements Screen {

    protected Viewport viewport;
    protected OrthographicCamera camera;
    protected SpriteBatch spriteBatch;
    public State(SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;
        camera = new OrthographicCamera();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void handleInput(float dt){

    }

    public void update(float dt){
        handleInput(dt);
    }
}
