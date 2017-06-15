package com.upc.desarrollo;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.upc.desarrollo.screens.Menu;
import com.upc.desarrollo.screens.MenuExamen;

public class Main extends com.badlogic.gdx.Game {

	private SpriteBatch spriteBatch;
	public static Main instance;
	@Override
	public void create () {
        CustomAssetManager.init();
        instance = this;
		spriteBatch = new SpriteBatch();
		setScreen(new MenuExamen(spriteBatch));
		//Gdx.app.debug("hola",CustomAssetManager.manager.getProgress()+"");
	}


	@Override
	public void render () {
		super.render();
		//if(CustomAssetManager.manager.getProgress() == ){

		//}
	}
	
	@Override
	public void dispose () {
		super.dispose();
		spriteBatch.dispose();
        CustomAssetManager.dispose();
	}
}
