package com.upc.desarrollo.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.helpers.Utils;
import com.upc.desarrollo.objects.Mario;

/**
 * Created by Luis on 23/05/2017.
 */

public class Hud implements Disposable {
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch sp;
    private Label countdownLabel;
    private Label marioLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label scoreLabel;
    public int worldTimer;
    public int score;
    private float timeCount;
    private Skin touchPadSkin;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Drawable touchBackground;
    private Drawable touchKnob;

    public static Hud instance;
    private Mario mMario;

    public Hud(SpriteBatch sp, Mario mario){
        mMario = mario;
        instance = this;

        this.sp = sp;
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(Config.GAME_WIDTH,Config.GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,sp);
        countdownLabel = new Label(String.format("%03d",worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d",score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        stage.addActor(table);
        createTouchpad();
    }

    private void createTouchpad() {
        touchPadSkin = new Skin();
        touchPadSkin.add("touchBackground",new Texture("touchBackground.png"));
        touchPadSkin.add("touchKnob",new Texture("touchKnob.png"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchPadSkin.getDrawable("touchBackground");
        touchKnob = touchPadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(5,touchpadStyle);
        touchpad.setBounds(70,70,
                100,100);
        //stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
    }

    public Vector2 getPadPosition(){
        return new Vector2(touchpad.getKnobPercentX()
                            ,touchpad.getKnobPercentY());

    }

    public void update(float dt){
        score = mMario.score;
        scoreLabel.setText(String.format("%06d",score));
    }


    public Camera getCamera(){
        return stage.getCamera();
    }

    public void draw(){
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
