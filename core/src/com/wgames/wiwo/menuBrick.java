package com.wgames.wiwo;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.physics.box2d.Body;


/**
 * Created by root on 2016-02-28.
 */
public class menuBrick {

    private static final float PPM  = 100;




    Wiwo game;
    Sprite brick;
    Body body;
    Sprite letterSprite;




    public menuBrick(Wiwo g,String l,Body b) {
        game = g;
        body = b;


        brick = new Sprite(game.menuItems.findRegion("brick"));
        brick.setSize(brick.getWidth() / PPM, brick.getHeight() / PPM);
        brick.setOrigin(brick.getWidth() / 2, brick.getHeight() / 2);


        float randomV = MathUtils.random() * 4;
        if(MathUtils.random() > 0.5){
            randomV = -randomV;
        }
        body.setAngularVelocity(randomV);

        letterSprite = new Sprite(game.letters.findRegion(l));
        letterSprite.setSize(letterSprite.getWidth() / PPM, letterSprite.getHeight() / PPM);
        letterSprite.setOrigin(letterSprite.getWidth() / 2, letterSprite.getHeight() /2);




    }
    public void updateBrick() {
        brick.setPosition(body.getPosition().x - brick.getWidth() / 2,
              body.getPosition().y - brick.getHeight() / 2);

        letterSprite.setPosition(brick.getX(),brick.getY());

        brick.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        letterSprite.setRotation(brick.getRotation());

    }

    public void draw(SpriteBatch batch) {

        updateBrick();
        brick.draw(batch);
        letterSprite.draw(batch);


    }
}
