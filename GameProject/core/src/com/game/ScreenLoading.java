package com.game;


import com.badlogic.gdx.ScreenAdapter;

public class ScreenLoading extends ScreenAdapter {

    private GameScreen game;
    private AfterDark AssetGame;
    public ScreenLoading(AfterDark AssetGame){
        this.AssetGame = AssetGame;
    }
    @Override
    public void render(float delta) {
        game.render(delta);
    }

    @Override
    public void show() {
        game = new GameScreen(AssetGame);

    }
}