package com.upc.desarrollo.screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.upc.desarrollo.Config;
import com.upc.desarrollo.CustomAssetManager;
import com.upc.desarrollo.helpers.ElementCreator;
import com.upc.desarrollo.helpers.Utils;
import com.upc.desarrollo.helpers.WorldContactListener;
import com.upc.desarrollo.objects.Enemy;
import com.upc.desarrollo.objects.Item;
import com.upc.desarrollo.objects.ItemDef;
import com.upc.desarrollo.objects.Mario;
import com.upc.desarrollo.objects.Mushroom;
import com.upc.desarrollo.ui.Hud;

import java.util.concurrent.LinkedBlockingDeque;

import utils.states.PhysicsState;

/**
 * Created by Luis on 23/05/2017.
 */

public class GameBackup extends PhysicsState {
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private Hud hud;
    private Mario mario;
    private TextureAtlas textureAtlas;
    private ElementCreator elementCreator;
    private boolean playDeadSound;
    private Music bg;
    public static GameBackup instance;
    private Array<Item> items;
    private LinkedBlockingDeque<ItemDef> itemsToSpawn;


    public GameBackup(SpriteBatch spriteBatch) {
        super(spriteBatch);
        instance = this;

        textureAtlas = new TextureAtlas("mario.pack");
        viewport = new FitViewport(Utils.convertPixelsToMeters(Config.GAME_WIDTH),
                                    Utils.convertPixelsToMeters(Config.GAME_HEIGHT),
                                    camera);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        elementCreator = new ElementCreator(world,map,textureAtlas);
        mario = new Mario(world,textureAtlas, new Vector2(0,0));
        hud = new Hud(spriteBatch, mario);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, Utils.convertPixelsToMeters(1));
        camera.position.set(viewport.getWorldWidth()/2f,viewport.getWorldHeight()/2f,0);

        bg = CustomAssetManager.manager.get(CustomAssetManager.MARIO_MUSIC);
        //bg.play();
        //bg.setVolume(0.5f);
        //bg.setLooping(true);
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingDeque<ItemDef>();
        world.setContactListener(new WorldContactListener());
    }

    public void spawnItem(ItemDef itemDef){
        itemsToSpawn.add(itemDef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef def = itemsToSpawn.poll();
            if(def.type == Mushroom.class){
                items.add(new Mushroom(world,textureAtlas,def.position));
            }
        }
    }



    public void playSound(String _sound){
        Sound sound = CustomAssetManager.manager.get(_sound);
        sound.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //tiledMapRenderer.render();
        box2DDebugRenderer.render(world, camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        //mario.draw(spriteBatch);
        for(Enemy enemy: elementCreator.enemies){
            enemy.draw(spriteBatch);
        }
        for(Item item :items){
            item.draw(spriteBatch);
        }
        spriteBatch.end();

        spriteBatch.setProjectionMatrix(hud.getCamera().combined);
        hud.draw();
    }

    @Override
    public void update(float delta){
        super.update(delta);
        camera.update();
        mario.update(delta);
        handleSpawningItems();
        tiledMapRenderer.setView(camera);
        for(Item item :items){
            item.update(delta);
        }
        if(mario.getStatus() != Mario.Status.DEAD){
            mario.update(delta);
            camera.position.x = mario.getPosition().x;
        }
        if(mario.getStatus() == Mario.Status.DEAD && !playDeadSound){
            playDeadSound = true;
            playSound(CustomAssetManager.DEAD);
            bg.stop();
        }
        for(Enemy enemy: elementCreator.enemies){
            enemy.update(delta);
        }

    }

    @Override
    public void handleInput(float dt) {
        super.handleInput(dt);
        mario.processInput(hud.getPadPosition());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }


    @Override
    public void dispose() {
        tiledMapRenderer.dispose();
        hud.dispose();
        map.dispose();
    }
}
