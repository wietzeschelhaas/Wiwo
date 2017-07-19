package com.wgames.wiwo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.midi.Sequence;

/**
 * Created by root on 2016-02-27.
 */
public class MenuScene extends BaseScene{

    private static final float PPM  = 100;

    SpriteBatch batch;
    Wiwo game;
    Stage stage;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera box2DCam;
    private Viewport viewport;
    private World world;

    Sprite backGround;
    Sprite menuBrick;
    Sprite wiwo;


    CreateWorldObjects crwo;


    ArrayList<menuBrick> menuBricks = new ArrayList<menuBrick>();
    String letters = "qwertyuiopasdfghjklzxcvbnm";

    float brickSpawnCounter = 0;

    public MenuScene(Wiwo g){
        super(g);


        world = new World(new Vector2(0, -1.8f), true);
        game = g;
        debugRenderer = new Box2DDebugRenderer();

        box2DCam = new OrthographicCamera(game.screenWidth / PPM,game.screenHeight / PPM);
        viewport = new FillViewport(game.screenWidth / PPM ,game.screenHeight /PPM, box2DCam);

        stage = new Stage();
        stage.setViewport(game.viewport);


        Gdx.input.setInputProcessor(stage);

        crwo = new CreateWorldObjects();



        backGround = new Sprite(new Texture("bg.png"));
        backGround.setSize(backGround.getWidth() / PPM, backGround.getHeight() / PPM);
        backGround.setOrigin(backGround.getWidth() / 2, backGround.getHeight() / 2);
        backGround.setPosition(-backGround.getWidth() / 2, -backGround.getHeight() / 2);

        menuBrick = new Sprite(game.manager.get("menuBrick.png",Texture.class));
        menuBrick.setSize(menuBrick.getWidth() / PPM, menuBrick.getHeight() / PPM);
        menuBrick.setOrigin(menuBrick.getWidth() / 2, menuBrick.getHeight() / 2);
        menuBrick.setPosition(-menuBrick.getWidth() / 2, -backGround.getHeight() / 2);

        wiwo = new Sprite(game.manager.get("wiwo.png",Texture.class));
        wiwo.setSize(wiwo.getWidth() / PPM, wiwo.getHeight() / PPM);
        wiwo.setOrigin(wiwo.getWidth() / 2, wiwo.getHeight() / 2);
        wiwo.setPosition(-wiwo.getWidth() / 2, -wiwo.getHeight() / 2);




        Skin s = new Skin(game.menuItems);

        ImageButton.ImageButtonStyle ims = new ImageButton.ImageButtonStyle();
        ims.imageUp = s.getDrawable("playButton");
        ims.pressedOffsetY=-5;
        ims.pressedOffsetX=-5;


        ImageButton im = new ImageButton(ims);
        im.setSize(1, 1);
        im.setPosition((game.screenWidth / 2) - im.getWidth() / 2
                , (game.screenHeight / 2) - im.getHeight() / 2);


        SizeToAction sizeTo = new SizeToAction();
        sizeTo.setSize(232, 232);
        sizeTo.setDuration(1.7f);
        sizeTo.setInterpolation(Interpolation.pow5);

        MoveToAction moveTo = new MoveToAction();
        moveTo.setPosition((game.screenWidth / 2) - 232 / 2f,
                (game.screenHeight / 2) - 232 / 2);
        moveTo.setDuration(1.5f);
        moveTo.setInterpolation(Interpolation.pow5);


        im.addAction(sizeTo);
        im.addAction(moveTo);
        im.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.setScreen(new GameScene(game));
            }
        });

        crwo.CreateCircle(world, BodyDef.BodyType.StaticBody, 232 / 2 / PPM, 0, 0);


        ImageButton.ImageButtonStyle imsSetting = new ImageButton.ImageButtonStyle();
        imsSetting.imageUp = s.newDrawable("settingGear");
        imsSetting.pressedOffsetX=-5;
        imsSetting.pressedOffsetY=-5;

        ImageButton settings = new ImageButton(imsSetting);
        settings.setSize(settings.getWidth() * 0.5f, settings.getHeight() * 0.5f);
        settings.setPosition(0,
                (game.screenHeight * 0.1f) - settings.getHeight() / 2);

        settings.addAction(Actions.moveTo((game.screenWidth / 1.2f) - settings.getWidth() / 2,
                (game.screenHeight * 0.1f) - settings.getHeight() / 2
                , 1.5f, Interpolation.exp10));

        settings.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.preferences.openPref();
            }
        });

        ImageButton.ImageButtonStyle imsScoreBoard = new ImageButton.ImageButtonStyle();
        imsScoreBoard.imageUp = s.getDrawable("score");
        imsScoreBoard.pressedOffsetX=-5;
        imsScoreBoard.pressedOffsetY=-5;


        ImageButton score = new ImageButton(imsScoreBoard);
        score.setSize(score.getWidth() * 0.5f, score.getHeight() * 0.5f);
        score.setPosition(game.screenWidth,
                (game.screenHeight * 0.9f) - score.getHeight() / 2);
        score.setSize(score.getWidth(), score.getHeight());

        score.addAction(Actions.moveTo((game.screenWidth * 0.2f) - score.getWidth() / 2,
                (game.screenHeight * 0.9f) - score.getHeight() / 2
                , 1.5f, Interpolation.exp10));

        score.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("open scoreboard here");
            }
        });

        stage.addActor(im);
        stage.addActor(settings);
        stage.addActor(score);


    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act(delta);
        batch.setProjectionMatrix(box2DCam.combined);



        batch.begin();
        backGround.draw(batch);
        wiwo.draw(batch);
        drawBricks(batch);
        menuBrick.draw(batch);
        batch.end();

        stage.draw();

        //debugRenderer.render(world, box2DCam.combined);

        world.step(1 / 60f, 6, 2);



        brickSpawnCounter += delta;
        if (brickSpawnCounter >= 1){
            spawnBrick();
            brickSpawnCounter = 0;
        }



    }

    private void spawnBrick() {
        float randomElement = letters.length() * MathUtils.random();
        char letter = letters.charAt((int) randomElement);

        float width = game.screenWidth / PPM;

        float posX = width * MathUtils.random() / 2;

        if (MathUtils.random() > 0.5f){
            posX = -posX;

        }

        menuBricks.add(new menuBrick(game,Character.toString(letter),
                crwo.CreateBox(world, BodyDef.BodyType.DynamicBody,posX,8,79 / PPM / 2,79 / PPM / 2)));

    }

    private void drawBricks(SpriteBatch batch){

        Iterator<menuBrick> iter = menuBricks.iterator();

        while (iter.hasNext()) {
            menuBrick mb = iter.next();
            if (mb.body.getPosition().y < -8){
                world.destroyBody(mb.body);
                iter.remove();
            }
            mb.draw(batch);

        }
    }

    @Override
    public void dispose() {
        debugRenderer.dispose();
        world.dispose();
        stage.dispose();
        batch.dispose();
    }

    @Override
    public void hide() {
        super.hide();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    protected void handleBackPress() {
        super.handleBackPress();

    }
}
