package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SmallScreen extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Table maintable;
    TextureAtlas atlas;
    protected Skin skin;
    private int state = 0;
    private AfterDark game;
    public SmallScreen(AfterDark game){
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(.1f,.1f,.15f,1);

        stage.act();
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void show(){
        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"),atlas);
        viewport = new ExtendViewport(800,600);
        stage = new Stage(viewport);
        maintable = new Table();
        maintable.setFillParent(true);
        stage.addActor(maintable);
        addButton("Resume").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                state = 1;
            }
        });
        addButton("Exit").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Menu(game));
            }
        });
    }
    private TextButton addButton(String name){
        TextButton button = new TextButton(name,skin);
        maintable.add(button).width(100).height(50).padBottom(10);
        maintable.row();
        return button;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
