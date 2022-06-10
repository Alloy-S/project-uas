package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.xml.stream.events.StartDocument;
import java.nio.file.attribute.UserPrincipal;

public class Bullet extends Rectangle {
    public static Float attackSpeed = 1f;
    private static Texture texture;
    Vector2 vec;
    Vector2 position;
    Sprite sprite;
    private final float SET_MOUSE_LIMIT = 150;

    public boolean remove = false;
    public Bullet (float x, float y, float targetx, float targety) {
//        this.x = x;
//        this.y = y;
        if (texture == null) {
            texture = new Texture("bullet.png");
            sprite = new Sprite(texture);
        }

        width = 20;
        height = 30;
        position = new Vector2(x, y);
        vec = new Vector2(0, 0);
        if (targetx > position.x + SET_MOUSE_LIMIT) {
            targetx = position.x + SET_MOUSE_LIMIT;
        } else if (targetx < position.x - SET_MOUSE_LIMIT) {
            targetx = position.x - SET_MOUSE_LIMIT;
        }

        if (targety > position.y + SET_MOUSE_LIMIT) {
            targety = position.y + SET_MOUSE_LIMIT;
        } else if (targety < position.y - SET_MOUSE_LIMIT) {
            targety = position.y - SET_MOUSE_LIMIT;
        }

        vec.set(targetx - position.x, (targety - position.y));
    }

    public void update(float deltaTime) {
//        y += speed * deltaTime;
        x = position.x;
        y = position.y;
        if (position.x > 800) {
            remove = true;
        }
        if (position.y > 600) {
            remove = true;
        }
        position.add(vec.x * deltaTime * attackSpeed, vec.y * deltaTime * attackSpeed);
    }

    public void render (SpriteBatch bacth) {

        bacth.draw(texture, position.x, position.y);
    }
}
