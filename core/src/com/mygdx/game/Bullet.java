package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Rectangle {
    public static int speed = 500;
    private static Texture texture;
    Vector2 vec;
    Vector2 position;
    Sprite sprite;

    public boolean remove = false;
    public Bullet (float x, float y, float targetx, float targety) {
//        this.x = x;
//        this.y = y;
//        if (texture == null) {
//            texture = new Texture("bullet.png");
//            sprite = new Sprite(texture);
//        }
        texture = new Texture("bullet.png");
        sprite = new Sprite(texture);

        width = 20;
        height = 30;
        position = new Vector2(x, y);
        vec = new Vector2(0, 0);
        vec.set(targetx - position.x, targety - position.y);
    }

    public void update(float deltaTime) {
        y += speed * deltaTime;
        x = position.x;
        y = position.y;
        if (x > 800) {
            remove = true;
            System.out.println("kena lo y");
        }
        if (y > 600) {
            System.out.println("kena  lo x");
            remove = true;
        }
        position.add(vec.x * deltaTime, vec.y * deltaTime);
    }

    public void render (SpriteBatch bacth) {
        float angel = 90;
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(angel);
        sprite.draw(bacth);
        //bacth.draw(texture, position.x, position.y);
    }
}
