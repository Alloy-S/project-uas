package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Game extends ApplicationAdapter implements Screen {
    private Map map;
    private IsometricTiledMapRenderer mapRender;
    private OrthographicCamera camera;
    private Player player;
    private Assets assets;
    private Skin skin;
    int stage;

    public Game() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRender.setView(camera);
        mapRender.render();

        mapRender.getBatch().begin();
       /* player.draw(mapRender.getBatch());*/
        mapRender.getBatch().end();
    }

    public void objCollision(){
        MapObjects collisionObjects = (assets.mainTiledMap).getLayers().get("CollisionLayer").getObjects();
        int tileWidth = 16;
        int tileHeight = 16;

        for (int i = 0; i < collisionObjects.getCount(); i++) {
            RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);
            Rectangle rect = obj.getRectangle();

            rect.set(player.getX(), player.getY(), 16,16);

            Rectangle rectobject = obj.getRectangle();
            rectobject.x /= tileWidth;
            rectobject.y /= tileHeight;
            rectobject.width /= tileWidth;
            rectobject.height /= tileHeight;

            if(rect.overlaps(rectobject)) {
                player.setAbleToMove(false);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }

    @Override
    public void show() {
        map = new Map();
        camera = new OrthographicCamera();
        camera.zoom = 1 / 6f;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void create() {
        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();

        skin = new Skin();
        skin.addRegions(assets.manager.get("Movement.pack", TextureAtlas.class));

        stage = 5;

        player = new Player(skin.getRegion("up"), skin.getRegion("down"), skin.getRegion("right"), skin.getRegion("left"), assets.manager.get("stepSound.wav", Sound.class));
    }
}
