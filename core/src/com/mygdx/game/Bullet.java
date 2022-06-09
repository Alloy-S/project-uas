package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.xml.stream.events.StartDocument;

public class Bullet extends Rectangle {
    public static int speed = 500;
    private static Texture texture;
    Vector2 vec;
    Vector2 position;

    public boolean remove = false;
    public Bullet (float x, float y, float targetx, float targety) {
//        this.x = x;
//        this.y = y;
        if (texture == null) {
            texture = new Texture("bullet.png");
        }
        position = new Vector2(x, y);
        vec = new Vector2(0,0);
        vec.set(targetx - position.x, (targety - position.y) * -1);
        System.out.println(getVelX(targetx, targety));
        System.out.println(getVelY(targetx, targety));
    }

    private float getVelX(float targetX, float targetY) {
        return (float)((targetX - position.x)/Math.sqrt(((targetX - position.x) * (targetX - position.x)) + ((targetY - position.y) *(targetY - position.y ))));
    }

    private float getVelY(float targetX, float targetY) {
        return (float)((targetY - position.y)/Math.sqrt(((targetY - position.y) * (targetY - position.y)) + ((targetX - position.x) * (targetX - position.x))));
    }

    public void update(float deltaTime) {
        y += speed * deltaTime;
        if (y > 600) {
            remove = true;
            System.out.println("kena lo y");
        }
        if (x > 800) {
            System.out.println("kena  lo x");
            remove = true;
        }
        position.add(vec.x * deltaTime, vec.y * deltaTime);
    }

    public void print() {
        System.out.println("x = " + position.x);
        System.out.println("y = " + position.y);
    }

    public void render (SpriteBatch bacth) {
        bacth.draw(texture, position.x, position.y);
    }
}
