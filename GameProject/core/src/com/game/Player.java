package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Rectangle {

    Sprite sprite;
    Texture texture;
//    Vector2 vec;
//    Vector2 position;
    long lastBulletSpawn;
    int hp;
    int damage;

    public Array<Bullet> bullets;

    public Player(float x, float y) {
       texture = new Texture(Gdx.files.internal("kaktus.png"));
       sprite = new Sprite(texture);
       bullets = new Array<>();
//       vec = new Vector2();
//       position = new Vector2(800 / 2 - 64 / 2, 20);
        this.x = x;
        this.y = y;
       width = 64;
       height = 64;
        hp = 100;
        damage = 50;
    }

    public void update(float delta) {
//        time += delta;
//        float yInput = (Gdx.graphics.getHeight() - Gdx.input.getY());
//        vec.set(Gdx.input.getX() - position.x, yInput - position.y).nor();
//        //position is a Vector2 update sprite coordinates
//        position.x += vec.x * 15f;
//        position.y += vec.y * 15f;

        if(Gdx.input.isKeyPressed(Input.Keys.A)) this.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.D)) this.x += 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.S)) this.y -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.W)) this.y += 200 * Gdx.graphics.getDeltaTime();
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, x, y);
        batch.end();
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public void addBullet(float targetx, float targety){
        bullets.add(new Bullet(x, y, targetx, targety));
        lastBulletSpawn = TimeUtils.nanoTime();
    }

    public void removeBullet(Bullet bullet){
        bullets.removeValue(bullet, true);
    }
}
