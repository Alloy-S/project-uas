package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
    final AfterDark game;
    private State state = State.RUN;
    Texture coneImage;
    Texture dropImage;
    Texture bucketImage;
    OrthographicCamera camera;
    Rectangle bucket;
    ArrayList<Bullet> bullets;
    Array<Rectangle> coneDrops;
    Array<Enemy> enemies;
    long lastDropTime;
    int dropsGathered;
    float shootTime;
    //attack speed
    final float shootWaitTime = 0.1f;
    Player player;
    Table maintable;
    ImageButton setting;
    TextureAtlas atlas;
    protected Skin skin;
    private Stage stage;
    Viewport viewport;
    Texture settingImage;
    private SmallScreen smallScreen;




    public GameScreen(final AfterDark game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("kaktus.png"));
        coneImage = new Texture(Gdx.files.internal("droplet.png"));
        player = new Player();


        // load the drop sound effect and the rain background "music"

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        bullets = new ArrayList<Bullet>();
        //coneDrops = new Array<Rectangle>();
        enemies = new Array<Enemy>();
        spawnEnemy();

        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"),atlas);
        settingImage = new Texture(Gdx.files.internal("setting.png"));
        viewport = new ExtendViewport(800,600);
        stage = new Stage(viewport);
        maintable = new Table();
        maintable.setFillParent(true);
        setting = new ImageButton(skin);
        setting.getStyle().imageUp  = new TextureRegionDrawable(new TextureRegion(settingImage));
        setting.getStyle().imageDown  = new TextureRegionDrawable(new TextureRegion(settingImage));
        maintable.add(setting).width(16).height(16).padBottom(20);
        stage.addActor(maintable);
        maintable.setPosition(392,284);
        smallScreen = new SmallScreen(game);
        smallScreen.show();
    }



    private void spawnEnemy() {
        Enemy enemy = new Enemy();
        enemy.x = MathUtils.random(100, 800 - 100);
        enemy.y = MathUtils.random(100, 480 - 100);
        enemies.add(enemy);
        lastDropTime = TimeUtils.millis();
    }

    private void spawnObject() {
        Rectangle coneImage = new Rectangle();
        coneImage.x = MathUtils.random(0, 800 - 64);
        coneImage.y = MathUtils.random(0, 480 - 64);
        coneImage.width = 64;
        coneImage.height = 64;
        coneDrops.add(coneImage);
        lastDropTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        //player.update(delta);
        player.draw(game.batch);


        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        stage.act();
        stage.draw();
        Gdx.input.setInputProcessor(stage);
        switch(state){
            case PAUSE:
                smallScreen.render(60);

                break;
            case RUN:
                setting.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        pause();
                    }
                });

                break;
        }
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 300, 480);
        //game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);

//        for (Rectangle coneDrop : coneDrops) {
//            game.batch.draw(coneImage, coneDrop.x, coneDrop.y);
//        }
        for (Enemy enemy : enemies) {
            enemy.render(game.batch);
        }
        for (Bullet bullet: bullets) {
            bullet.render(game.batch);
        }
        game.batch.end();

        if(smallScreen.getState() == 1){
            smallScreen.setState(0);
            resume();
        }
        //shoting code
        shootTime += delta;
        if ((Gdx.input.isTouched()) && shootTime >= shootWaitTime) {
            bullets.add(new Bullet(player.position.x - 64, player.position.y + 20, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()));
            shootTime = 0;
        }

        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        //update bullet
        for (Bullet bullet: bullets) {
            bullet.update(delta);
            if (bullet.remove) {
                bulletsToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletsToRemove);

        //cek enemy
        for (Enemy enemy : enemies) {
            enemy.isDead();
        }



//         process user input
//        if (Gdx.input.isTouched()) {
//
//        }

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            player.position.x -= 500 * Gdx.graphics.getDeltaTime();
            //bucket.x -= 500 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            player.position.x += 500 * Gdx.graphics.getDeltaTime();
            //bucket.x += 500 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            player.position.y += 500 * Gdx.graphics.getDeltaTime();
            //bucket.y += 500 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            player.position.y -= 500 * Gdx.graphics.getDeltaTime();
//            bucket.y -= 500 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800)
            bucket.x = 800;

        // check if we need to create a new raindrop
        if (TimeUtils.millis() - lastDropTime > 5000) {
//            spawnObject();
            spawnEnemy();
        }


        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
//        Iterator<Rectangle> iter = coneDrops.iterator();
        Iterator<Enemy> iterEn = enemies.iterator();
//        while (iter.hasNext()) {
//            Rectangle coneDrop = iter.next();
//            //raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
//            //System.out.println(TimeUtils.millis() - lastDropTime);
//            if (TimeUtils.millis() - lastDropTime >= 1000) {
//                iter.remove();
//            }
//
//            if (coneDrop.overlaps(bucket)) {
//                dropsGathered++;
//                iter.remove();
//            }
//        }

		while (iterEn.hasNext()) {
			Rectangle enemy = iterEn.next();
			//raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			//System.out.println(TimeUtils.millis() - lastDropTime);
            game.batch.begin();
            game.font.draw(game.batch, "HP = " + ((Enemy)enemy).getHp(), ((Enemy)enemy).x + 115, ((Enemy)enemy).y + 130);
            game.batch.end();
			if (TimeUtils.millis() - lastDropTime >= 4900) {
				iterEn.remove();
			}

            if (((Enemy)enemy).isDead()) {
                iterEn.remove();
            }
            Iterator<Bullet> iterBull = bullets.iterator();
            while (iterBull.hasNext()) {
                Bullet bullet = iterBull.next();

                //enemy terkena bullet maka take damage
                if (bullet.overlaps(enemy)) {
                    ((Enemy)enemy).takeDamage(50);
                    iterBull.remove();
                }

                //cek apakah enemy sudah mati
                if (((Enemy)enemy).isDead()) {
                    dropsGathered++;
                }

            }

//			if (enemeis.overlaps(bu)) {
//				dropsGathered++;
//				iter.remove();
//			}
		}
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown

    }

    @Override
    public void hide() {
    }


    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
    }public void pause(){
        this.state = State.PAUSE;
    }
    public void resume(){
        this.state = State.RUN;
    }
    public enum State{
        PAUSE,RUN
    }
}
