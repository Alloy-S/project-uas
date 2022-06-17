package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Map extends ApplicationAdapter implements InputProcessor {
    TiledMap map;
    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;
    Player player;
    private Assets assets;
    private Skin skin;
    SpriteBatch batch;
    private Game game;

    int stage;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        map = new TmxMapLoader().load("map_3.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(this);

        //load player
        batch = new SpriteBatch();
        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();

        skin = new Skin();
        skin.addRegions(assets.manager.get("Movement.pack", TextureAtlas.class));

        stage = 5;

        player = new Player(skin.getRegion("up"), skin.getRegion("down"), skin.getRegion("right"), skin.getRegion("left"), assets.manager.get("stepSound.wav", Sound.class));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        player.setX(600 / 2);
        player.setY(30);
        player.update(batch);

        MapObjects collisionObjects = (map).getLayers().get("obj layer").getObjects();
        int tileWidth = 16;
        int tileHeight = 16;


        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            //ATAS
            player.setCurrentAnimation(Player.UP);
            stage = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            //KIRI
            player.setCurrentAnimation(Player.LEFT);
            stage = 3;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            //BAWAH
            player.setCurrentAnimation(Player.DOWN);
            stage = 2;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            //BAWAH
            player.setCurrentAnimation(Player.RIGHT);
            stage = 1;

//
//
            if (!(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) ||
                    Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D))) {
                switch (stage) {
                    case 0:
                        player.setCurrentAnimation(Player.stillUP);
                        break;
                    case 1:
                        player.setCurrentAnimation(Player.stillRIGHT);
                        break;
                    case 2:
                        player.setCurrentAnimation(Player.stillDOWN);
                        break;
                    case 3:
                        player.setCurrentAnimation(Player.stillLEFT);
                        break;
                }
            }
        }
        for (int i = 0; i < collisionObjects.getCount(); i++) {
            RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);
            Rectangle rect = obj.getRectangle();

            rect.set(player.getX(), player.getY(), 16, 16);

            Rectangle rectobject = obj.getRectangle();
            rectobject.x /= tileWidth;
            rectobject.y /= tileHeight;
            rectobject.width /= tileWidth;
            rectobject.height /= tileHeight;

            if (rect.overlaps(rectobject)) {
                player.setAbleToMove(false);
            }
        }


    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
