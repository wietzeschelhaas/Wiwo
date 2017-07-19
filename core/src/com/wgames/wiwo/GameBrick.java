package com.wgames.wiwo;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by root on 2016-03-25.
 */
public class GameBrick {

    public static final float PPM = 100;

    Wiwo game;
    Sprite brick;
    Sprite greenBrick;
    Sprite redBrick;
    Body body;
    Sprite letterSprite;
    boolean isGreen = false;
    boolean isRed = false;
    String letter;


    public GameBrick(Wiwo g,String l,Body b) {
        game = g;
        body = b;

        letter = l;

        brick = new Sprite(game.menuItems.findRegion("brick"));
        brick.setSize(brick.getWidth() / PPM + game.brickSize, brick.getHeight() / PPM + game.brickSize);
        brick.setOrigin(brick.getWidth() / 2, brick.getHeight() / 2);

        greenBrick = new Sprite(game.menuItems.findRegion("brickGreen"));
        greenBrick.setSize(greenBrick.getWidth() / PPM + game.brickSize, greenBrick.getHeight() / PPM + game.brickSize);
        greenBrick.setOrigin(greenBrick.getWidth() / 2, greenBrick.getHeight() / 2);

        redBrick = new Sprite(game.menuItems.findRegion("brickRed"));
        redBrick.setSize(redBrick.getWidth() / PPM + game.brickSize, redBrick.getHeight() / PPM + game.brickSize);
        redBrick.setOrigin(redBrick.getWidth() / 2, redBrick.getHeight() / 2);

        letterSprite = new Sprite(game.letters.findRegion(l));
        letterSprite.setSize(letterSprite.getWidth() / PPM + game.letterSize, letterSprite.getHeight() / PPM + game.letterSize);
        letterSprite.setOrigin(letterSprite.getWidth() / 2, letterSprite.getHeight() / 2);


    }

    public void updateBrick() {

        if (isGreen){
            greenBrick.setPosition(body.getPosition().x - greenBrick.getWidth() / 2,
                    body.getPosition().y - greenBrick.getHeight() / 2);

            letterSprite.setPosition(greenBrick.getX() + (greenBrick.getWidth() - letterSprite.getWidth()) / 2
                    ,greenBrick.getY() + (greenBrick.getHeight() - letterSprite.getHeight())/2);

            greenBrick.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
            letterSprite.setRotation(greenBrick.getRotation());
        }
        else if (isRed){
            redBrick.setPosition(body.getPosition().x - redBrick.getWidth() / 2,
                    body.getPosition().y - redBrick.getHeight() / 2);

            letterSprite.setPosition(redBrick.getX() + (redBrick.getWidth() - letterSprite.getWidth()) / 2
                    ,redBrick.getY() + (redBrick.getHeight() - letterSprite.getHeight())/2);

            redBrick.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
            letterSprite.setRotation(redBrick.getRotation());
        }
        else{
            brick.setPosition(body.getPosition().x - brick.getWidth() / 2,
                    body.getPosition().y - brick.getHeight() / 2);

            letterSprite.setPosition(brick.getX() + (brick.getWidth() - letterSprite.getWidth()) / 2
                    ,brick.getY() + (brick.getHeight() - letterSprite.getHeight())/2);

            brick.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
            letterSprite.setRotation(brick.getRotation());
        }


    }

    public void draw(SpriteBatch batch) {

        updateBrick();
        if (isGreen){
            greenBrick.draw(batch);
        } else if (isRed) {
            redBrick.draw(batch);
        }
        else{
            brick.draw(batch);
        }

        letterSprite.draw(batch);


    }
}
