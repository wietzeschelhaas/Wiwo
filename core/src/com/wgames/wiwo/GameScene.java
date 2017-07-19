package com.wgames.wiwo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by root on 2016-03-03.
 */
public class GameScene extends BaseScene  {


    public enum GameState {
        Play, Paused
    }

    private static final float PPM  = 100;


    SpriteBatch batch;
    Wiwo game;
    Word wordHUD;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera box2DCam;
    private Viewport viewport;
    private World world;


    Sprite backGround;
    Sprite gamePlayBrick;

    WordLanguage wordLanguage;

    GameState gameState = GameState.Play;

    ArrayList<GameBrick> bricks = new ArrayList<GameBrick>();

    CreateWorldObjects cwo;

    // the counter for spawning the bricks
    float brickSpawnCounter = 0;

    // how often the bricks should spawn, 1 means every sec.
    float brickSpawnRate = 2;

    Vector3 touchPos;

    Stage stage;


    ImageButton cancelButton;
    ImageButton checkButton;
    ImageButton resume;
    ImageButton replay;
    ImageButton exitToMenu;


    float score = 0;
    String word = "";

    SizeToAction sizeToResume;
    MoveToAction moveToResume;
    MoveToAction moveToExit;
    MoveToAction moveToReplay;


    boolean canInitBut = true;
    public GameScene(Wiwo wiwo) {
        super(wiwo);

        cwo = new CreateWorldObjects();

        // CHANGE THIS LATER SO THAT IT TAKES THE LANGUAGE FROM SHAREDPREFERENCES
        wordLanguage = new WordLanguage("en");
        wordHUD = new Word(wiwo,20);

        world = new World(new Vector2(0, -12f), true);
        game = wiwo;
        debugRenderer = new Box2DDebugRenderer();

        box2DCam = new OrthographicCamera(game.screenWidth / PPM,game.screenHeight / PPM);

        // might use this later
        viewport = new FillViewport(game.screenWidth / PPM ,game.screenHeight /PPM, box2DCam);

        touchPos = new Vector3();

        stage = new Stage();
        stage.setViewport(game.viewport);


        backGround = new Sprite(new Texture("bg.png"));
        backGround.setSize(backGround.getWidth() / PPM, backGround.getHeight() / PPM);
        backGround.setOrigin(backGround.getWidth() / 2, backGround.getHeight() / 2);
        backGround.setPosition(-backGround.getWidth() / 2, -backGround.getHeight() / 2);

        gamePlayBrick = new Sprite(new Texture("gamePlayBrick.png"));
        gamePlayBrick.setSize(gamePlayBrick.getWidth() / PPM, gamePlayBrick.getHeight() / PPM);
        gamePlayBrick.setOrigin(gamePlayBrick.getWidth() / 2, gamePlayBrick.getHeight() / 2);
        gamePlayBrick.setPosition(-gamePlayBrick.getWidth() / 2, -gamePlayBrick.getHeight() / 2);

        cwo.CreateEdgeShape(world, BodyDef.BodyType.StaticBody, (float) -8.28 / 2, (float) -14.72 / 2,
                (float) -8.28 / 2, (float) 14.72 / 2);

        cwo.CreateEdgeShape(world, BodyDef.BodyType.StaticBody, (float) 8.28 / 2, (float) -14.72 / 2,
                (float) 8.28 / 2, (float) 14.72 / 2);


        cwo.CreateEdgeShape(world, BodyDef.BodyType.StaticBody, (float) -8.28 / 2, (float) -14.72 / 2 + (float) 2.3,
                (float) 8.28 / 2, (float) -14.72 / 2 + (float) 2.3);



        ImageButton.ImageButtonStyle imsCheck = new ImageButton.ImageButtonStyle();
        imsCheck.pressedOffsetX=-5;
        imsCheck.pressedOffsetY=-5;
        Skin s = new Skin(game.menuItems);
        imsCheck.imageUp = s.getDrawable("checkmark");
        checkButton = new ImageButton(imsCheck);
        checkButton.setPosition((game.screenWidth / 1.1f) - checkButton.getWidth() / 2, (game.screenHeight * 0.05f) - checkButton.getHeight() / 2);


        ImageButton.ImageButtonStyle imsCancel = new ImageButton.ImageButtonStyle();
        imsCancel.pressedOffsetX=-5;
        imsCancel.pressedOffsetY=-5;
        imsCancel.imageUp = s.getDrawable("exitToMenu");
        cancelButton = new ImageButton(imsCancel);
        cancelButton.setSize(checkButton.getWidth(),checkButton.getHeight());
        cancelButton.setPosition((game.screenWidth * 0.1f) - cancelButton.getWidth() / 2, (game.screenHeight * 0.05f) - cancelButton.getHeight() / 2);

        cancelButton.setVisible(false);
        checkButton.setVisible(false);

        checkButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);


                Iterator<GameBrick> removeIter = bricks.iterator();

                while (removeIter.hasNext()) {
                    GameBrick gb = removeIter.next();
                    if (gb.isGreen) {
                        world.destroyBody(gb.body);
                        removeIter.remove();
                    }
                }

                word = "";
                wordHUD.removeAllLetters();

                cancelButton.setVisible(false);
                checkButton.setVisible(false);
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Iterator<GameBrick> iter = bricks.iterator();
                while (iter.hasNext()) {
                    GameBrick gb = iter.next();
                    gb.isGreen = false;
                    gb.isRed = false;
                }
                word = "";
                wordHUD.removeAllLetters();
                cancelButton.setVisible(false);
                checkButton.setVisible(false);
            }
        });

        stage.addActor(checkButton);
        stage.addActor(cancelButton);



        // throws some bricks in the game to start with
        startBricks();
    }

    @Override
    protected void handleBackPress() {
        super.handleBackPress();
        gameState = GameState.Paused;
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        super.hide();
        batch.dispose();
    }

    @Override
    public void dispose() {
        super.dispose();

        debugRenderer.dispose();
        world.dispose();
        batch.dispose();
        stage.dispose();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // checks if the bricks are touched.
        checkBrickTouch();
        stage.act(delta);
        batch.setProjectionMatrix(box2DCam.combined);


        batch.begin();
        backGround.draw(batch);
        drawBricks(batch);
        gamePlayBrick.draw(batch);
        wordHUD.draw(batch);
        batch.end();

        stage.draw();

       // debugRenderer.render(world, box2DCam.combined);

        if (gameState == GameState.Play){
            world.step(1 / 60f, 6, 2);

            brickSpawnCounter += delta;
            if (brickSpawnCounter >= brickSpawnRate){
                spawnBrick();
                brickSpawnCounter = 0;
                brickSpawnRate = brickSpawnRate * 0.992f;

                if(brickSpawnRate <= 1){
                    brickSpawnRate = brickSpawnRate * 0.997f;
                }
            }
            canInitBut = true;
        }
        if(gameState == GameState.Paused){


            if (canInitBut){
                initPauseButtons();
                canInitBut = false;
            }

            resume.setVisible(true);
            replay.setVisible(true);
            exitToMenu.setVisible(true);




        }



    }
    private void spawnBrick() {

        char letter = wordLanguage.getRandomChar();
        float width = (game.screenWidth / PPM) - 0.5f - (79/PPM/2) ;

        float posX = width * MathUtils.random() / 2;


        if (MathUtils.random() > 0.5f){
            posX = -posX;

        }

        bricks.add(new GameBrick(game, Character.toString(letter),
                cwo.CreateBox(world, BodyDef.BodyType.DynamicBody, posX, 8, ((79 / PPM / 2) + game.brickSize / 2) - 0.02f, ((79 / PPM / 2) + game.brickSize / 2) -0.02f)));

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    private void drawBricks(SpriteBatch batch){
        if(gameState == GameState.Play){
            Iterator<GameBrick> iter = bricks.iterator();

            while (iter.hasNext()) {
                GameBrick gb = iter.next();
                gb.draw(batch);
            }
        }

    }


    private void checkBrickTouch() {
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            box2DCam.unproject(touchPos);

            Iterator<GameBrick> iter = bricks.iterator();

            while (iter.hasNext()) {
                GameBrick gb = iter.next();
                if (touchPos.x > gb.brick.getX() &&
                        touchPos.x < gb.brick.getX() + gb.brick.getWidth() &&
                        touchPos.y > gb.brick.getY() &&
                        touchPos.y < gb.brick.getY() + gb.brick.getHeight()) {
                    if (!gb.isRed && !gb.isGreen) {
                        gb.isRed = true;
                        word = word + gb.letter;
                        wordHUD.addLetter(gb.letter);
                        cancelButton.setVisible(true);

                    }


                }
            }

            Iterator<GameBrick> iterr = bricks.iterator();
            if (word.length() > 1) {
                if (Wiwo.dataBase.checkWord(word)) {
                    checkButton.setVisible(true);
                    while (iterr.hasNext()) {
                        GameBrick gb = iterr.next();
                        if (gb.isRed) {
                            gb.isGreen = true;
                        }

                    }
                }else {
                    Iterator<GameBrick> iterrr = bricks.iterator();
                    while (iterrr.hasNext()) {
                        GameBrick gb = iterrr.next();
                        if (gb.isGreen) {
                            gb.isGreen = false;
                            gb.isRed = true;
                            checkButton.setVisible(false);
                        }
                    }

                }
            }
        }
    }

    private void initPauseButtons(){

        Skin s = new Skin(game.menuItems);


        ImageButton.ImageButtonStyle imsResume = new ImageButton.ImageButtonStyle();
        imsResume.pressedOffsetX=-5;
        imsResume.pressedOffsetY=-5;
        imsResume.imageUp = s.getDrawable("resumePlay");
        resume = new ImageButton(imsResume);
        resume.setSize(1, 1);
        resume.setPosition((game.screenWidth / 2) - resume.getWidth() / 2,
                (game.screenHeight / 2) - resume.getHeight() / 2);
        resume.setVisible(false);

        float w = 210;
        float h = 220;

        sizeToResume = new SizeToAction();
        sizeToResume.setSize(w, h);
        sizeToResume.setDuration(0.7f);
        sizeToResume.setInterpolation(Interpolation.pow5);

        moveToResume = new MoveToAction();
        moveToResume.setPosition((game.screenWidth / 2 - (w / 2)),
                (game.screenHeight / 2) - (h / 2));
        moveToResume.setDuration(0.7f);
        moveToResume.setInterpolation(Interpolation.pow5);

        resume.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                gameState = GameState.Play;

                resume.setVisible(false);
                replay.setVisible(false);
                exitToMenu.setVisible(false);
            }
        });


        ImageButton.ImageButtonStyle imsReplay = new ImageButton.ImageButtonStyle();
        imsReplay.pressedOffsetX=-5;
        imsReplay.pressedOffsetY=-5;
        imsReplay.imageUp = s.getDrawable("again");
        replay = new ImageButton(imsReplay);
        replay.setSize(replay.getWidth() * 0.8f, replay.getHeight() * 0.8f);
        replay.setPosition(game.screenWidth,
                (game.screenHeight / 2) - replay.getHeight() / 2);
        replay.setVisible(false);


        moveToReplay = new MoveToAction();
        moveToReplay.setPosition((game.screenWidth / 2 * 1.5f) - replay.getWidth() / 2,
                (game.screenHeight / 2) - replay.getHeight() / 2);
        moveToReplay.setDuration(0.7f);
        moveToReplay.setInterpolation(Interpolation.exp5);

        replay.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Iterator<GameBrick> removeIter = bricks.iterator();

                while (removeIter.hasNext()) {
                    GameBrick gb = removeIter.next();
                    world.destroyBody(gb.body);
                }

                bricks.removeAll(bricks);
                score = 0;
                gameState = GameState.Play;

                resume.setVisible(false);
                replay.setVisible(false);
                exitToMenu.setVisible(false);

                startBricks();
                brickSpawnRate = 2;
                brickSpawnCounter = 0;
            }
        });


        ImageButton.ImageButtonStyle imsexitToMenu = new ImageButton.ImageButtonStyle();
        imsexitToMenu.pressedOffsetX=-5;
        imsexitToMenu.pressedOffsetY=-5;
        imsexitToMenu.imageUp = s.getDrawable("exitToMenu");
        exitToMenu = new ImageButton(imsexitToMenu);

        exitToMenu.setSize(exitToMenu.getWidth() * 0.8f, exitToMenu.getHeight() * 0.8f);
        exitToMenu.setPosition(-exitToMenu.getWidth(), (game.screenHeight / 2) - replay.getHeight() / 2);
        exitToMenu.setVisible(false);

        moveToExit = new MoveToAction();
        moveToExit.setPosition((game.screenWidth / 2 * 0.5f) - exitToMenu.getWidth() / 2,
                (game.screenHeight / 2) - replay.getHeight() / 2);
        moveToExit.setDuration(0.7f);
        moveToExit.setInterpolation(Interpolation.exp5);

        exitToMenu.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

                resume.setVisible(false);
                replay.setVisible(false);
                exitToMenu.setVisible(false);

                game.setScreen(new MenuScene(game));
            }
        });


        resume.addAction(sizeToResume);
        resume.addAction(moveToResume);
        replay.addAction(moveToReplay);
        exitToMenu.addAction(moveToExit);

        stage.addActor(resume);
        stage.addActor(replay);
        stage.addActor(exitToMenu);


    }


    private void startBricks(){
        float posY = 7;
        for(int i = 0; i < 20; i++){

            if (i % 5 == 0){
                posY+=1;
            }
            char letter = wordLanguage.getRandomChar();
            float width = (game.screenWidth / PPM) - 0.5f - (79/PPM/2) ;

            float posX = width * MathUtils.random() / 2;


            if (MathUtils.random() > 0.5f){
                posX = -posX;

            }

            bricks.add(new GameBrick(game, Character.toString(letter),
                    cwo.CreateBox(world, BodyDef.BodyType.DynamicBody, posX, posY, ((79 / PPM / 2) + game.brickSize / 2) - 0.02f, ((79 / PPM / 2) + game.brickSize / 2) -0.02f)));

        }
    }



}
