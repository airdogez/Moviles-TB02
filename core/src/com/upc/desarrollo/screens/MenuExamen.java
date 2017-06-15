package com.upc.desarrollo.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.Main;

import utils.states.State;

/**
 * Created by operador on 5/17/17.
 */

public class MenuExamen extends State {
    private Stage stage;
    public MenuExamen(SpriteBatch spriteBatch) {
        super(spriteBatch);
        viewport = new FitViewport(Config.GAME_WIDTH, Config.GAME_HEIGHT,camera);

        stage = new Stage(viewport,spriteBatch);
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Label examenLabel = new Label("PC02",new Label.LabelStyle(
                new BitmapFont(), Color.WHITE
        ));
        Label alumnoLabel= new Label("Andres Revolledo Galvez",new Label.LabelStyle(
                new BitmapFont(), Color.WHITE
        ));
        examenLabel.setAlignment(Align.center);
        alumnoLabel.setAlignment(Align.center);
        table.add(examenLabel);
        table.row();
        table.add(alumnoLabel);
        stage.addActor(table);
    }

    @Override
    public void handleInput(float dt) {
        super.handleInput(dt);
        if(Gdx.input.justTouched()){
            Main.instance.setScreen(new Game(spriteBatch));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
    }
}
