package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Rectangle {
    private int hp = 100;
    private Boolean dead = false;
    Texture texture;

    public Enemy() {
        if (texture == null) {
            texture = new Texture("karen.png");
        }

        width = 100;
        height = 100;
    }

    public int getHp() {
        return hp;
    }

    public void render (SpriteBatch bacth) {
        //bacth.draw();
        bacth.draw(texture, x + 50, y + 50);
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public Boolean isDead() {
        if (this.hp <= 0) {
            dead = true;
        }
        return dead;
    }
}
