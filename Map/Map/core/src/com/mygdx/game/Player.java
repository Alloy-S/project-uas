package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

@SuppressWarnings("unchecked")
public class Player extends Rectangle {
    Texture texture;
    public long lastBulletSpawn;
    public int hp;
    public int damage;
    private boolean dead = false;
    public int movementSpeed = 300;

    ApplicationAdapter applicationAdapter;
    SpriteBatch batch;
    private Skin skin;
    public static int WIDTH = 854;
    public static int HEIGHT = 480;
    private Assets assets;
    private PlayerMovement player;
    float angleRad;
    float angle;
    boolean press;
    public boolean keyPressed;
    int stage;
    int bulSpeed;
    float Xprev;
    float Yprev;

    public Player(float x, float y) {
        batch = new SpriteBatch();
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);

        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();

        skin = new Skin();
        skin.addRegions(assets.manager.get("Movement.pack", TextureAtlas.class));

        stage = 5;

        player = new PlayerMovement(this, skin.getRegion("up"), skin.getRegion("down"), skin.getRegion("right"), skin.getRegion("left"));
        /*  bullets = new Array<>();*/
        this.x = x;
        this.y = y;
        this.width = 48;
        this.height = 48;
        /*hp = 100;
        damage = 50;
        this.bulSpeed = 350;*/
    }



    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) this.x -= movementSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.D)) this.x += movementSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.S)) this.y -= movementSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.W)) this.y += movementSpeed * Gdx.graphics.getDeltaTime();

        if (this.x > 800) this.x = 720 - 72;
        if (this.x < -64) this.x = -64;
        if (this.y > 600 - 64) this.y = 600 - 72;
        if (this.y < -64) this.y = -64;
//        System.out.println("x : " + this.x);
//        System.out.println("y : " + this.y);
    }
    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, x, y);
        batch.end();
    }

    public void render(SpriteBatch batch) {

        player.update(batch);

        angleRad = (float) (Math.atan2(Gdx.input.getX() - this.x,Gdx.graphics.getHeight()- Gdx.input.getY() - this.y));
        angle = (float) Math.toDegrees(angleRad - Math.PI / 2);
        angle = Math.round(angle) <= 0 ? angle += 360 : angle;
        if (Math.round(angle) == 360)
            angle = 0;


        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            this.x -= movementSpeed * Gdx.graphics.getDeltaTime();
            Xprev = x;
            if (Gdx.input.isKeyJustPressed(Input.Keys.A)) player.setCurrentAnimation(2);
            press = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.x += movementSpeed * Gdx.graphics.getDeltaTime();
            Xprev = x;
            if (Gdx.input.isKeyJustPressed(Input.Keys.D)) player.setCurrentAnimation(0);
            press = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.y -= movementSpeed * Gdx.graphics.getDeltaTime();
            Yprev = y;
            if (Gdx.input.isKeyJustPressed(Input.Keys.S)) player.setCurrentAnimation(1);
            press = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.y += movementSpeed * Gdx.graphics.getDeltaTime();
            Yprev = y;
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) player.setCurrentAnimation(3);
            press = true;
        }

        if (this.x > 600) this.x = 600;
        if (this.x < 0) this.x = 0;
        if (this.y > 800) this.y = 720;
        if (this.y < 0) this.y = 0;

//        if ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D))) {
//            press = true;
//            if ((Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.D))) {
//                player.setCurrentAnimation(stage);
//            }
//        } else {
//            press = false;
//        }

        if (!press || Gdx.input.isTouched()) {
            if (angle >= 45 && angle < 135) {
                System.out.println("Down");
                player.setCurrentAnimation(PlayerMovement.stillDOWN);
                stage = 1;
            } else if (angle >= 135 && angle < 225) {
                System.out.println("LEFT");
                player.setCurrentAnimation(PlayerMovement.stillLEFT);
                stage = 2;
            } else if (angle >= 225 && angle < 315) {
                System.out.println("up");
                player.setCurrentAnimation(PlayerMovement.stillUP);
                stage = 3;
            } else if (angle >= 315 || angle < 45) {
                System.out.println("RIGHT");
                player.setCurrentAnimation(PlayerMovement.stillRIGHT);
                stage = 0;
            }
        }
        press = false;
    }
}