package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.css.Rect;

public class Map extends ApplicationAdapter implements InputProcessor {
    TiledMap map;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRenderer;
    Player player;
    private Assets assets;
    private Skin skin;
    SpriteBatch batch;
    private Game game;
    Array<Rectangle> objectCollide;
    int stage;
    float Xprev;
    float Yprev;
    boolean move = true;

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

        player = new Player(400 - 64, 20);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        /*player.setX(600 / 2);
        player.setY(30);*/


//        }

       /*for (int i = 0; i < layer.getCount(); i++) {
            RectangleMapObject obj = (RectangleMapObject) layer.get(i);
            Rectangle rect = obj.getRectangle();

            rect.set(player.getX(), player.getY(), 16, 16);

            Rectangle rectobject = obj.getRectangle();
            rectobject.x = tileWidth;
            rectobject.y = tileHeight;
            rectobject.width = tileWidth;
            rectobject.height = tileHeight;

            if (rect.overlaps(rectobject)) {
                player.setAbleToMove(false);
            }
        }
        System.out.println(player.isAbleToMove());*/

        MapLayer layer = map.getLayers().get("obj layer");
        objectCollide = new Array<Rectangle>();
        for (MapObject object : layer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if ("blocked".equals(rectangleObject.getName())) {
                    Rectangle rectangle = rectangleObject.getRectangle();
                    objectCollide.add(rectangle);
                }
            }
        }
        System.out.println(player.getX());
        System.out.println(player.getY());

        for (int i = 0; i < objectCollide.size; i++) {
            if (player.overlaps(objectCollide.get(i))) {
                // player.setPosition(objectCollide.get(i).getX(),  objectCollide.get(i).getY());
                System.out.println("nabrak");
                move = false;
              /*  if (player.getX() >= objectCollide.get(i).getX()){

                }
                if (player.getY() >= objectCollide.get(i).getY()){

                }*/


                player.setX(Xprev);
                player.setY(Yprev);


    /*            System.out.println(objectCollide.get(i).getX());
                System.out.println(objectCollide.get(i).getY());*/
            }
        }
        player.render(batch);
        if (move == true) {
            Xprev = player.getX();
            Yprev = player.getY();

        }
        move = true;

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
