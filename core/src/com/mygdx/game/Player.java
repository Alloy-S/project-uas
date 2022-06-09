package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Rectangle {

    Sprite sprite;
    Texture texture;
    Vector2 vec;
    Vector2 position;
    long time;

    public Player() {
       texture = new Texture(Gdx.files.internal("kaktus.png"));
       sprite = new Sprite(texture);
       vec = new Vector2();


       width = 64;
       height = 64;

    }

    public void update(float delta) {
        time += delta;
        float yInput = (Gdx.graphics.getHeight() - Gdx.input.getY());
        vec.set(Gdx.input.getX() - position.x, yInput - position.y).nor();
        //position is a Vector2 update sprite coordinates
        position.x += vec.x * 15f;
        position.y += vec.y * 15f;
    }

    public void draw(SpriteBatch batch){
        batch.begin();
        sprite.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2);

        float xInput = Gdx.input.getX();
        float yInput = (Gdx.graphics.getHeight() - Gdx.input.getY());

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - position.y, xInput - position.x);

        if(angle < 0){
            angle += 360;
        }
        sprite.rotate(angle);

        sprite.draw(batch);
        batch.end();
    }
}
