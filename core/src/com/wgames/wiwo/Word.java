package com.wgames.wiwo;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by wietze on 2016-07-10.
 */
public class Word {
    private static final int PPM = 100;
    Wiwo game;
    int maxWordLength;
    ArrayList<WordBrick> word = new ArrayList<WordBrick>();

    // the row of bricks position, xpos will be updated dynamicly, ypos will remain static
    float xPos;
    float yPos;



    float brickArea = 5.4f;


    float adder = 0.9f;
    float startBrickArea =  -((game.screenWidth * 0.85f) / PPM / 2) + adder;

    public Word(Wiwo g, int i) {
        maxWordLength = i;
        game = g;


        xPos = -((game.screenWidth * 0.85f) / PPM / 2) + adder;
        yPos = -((game.screenHeight * 0.96f) / PPM / 2);

    }

    public void addLetter(String l) {
        word.add(new WordBrick(l, xPos, yPos));

        xPos = -((game.screenWidth * 0.85f) / PPM / 2) + adder;

            Iterator<WordBrick> iter = word.iterator();
            while (iter.hasNext()) {
                WordBrick wordBrick = iter.next();
                if(word.size() > 6){
                    float tSize = brickArea / word.size();

                    wordBrick.brick.setSize(tSize,tSize);
                    wordBrick.letterSprite.setSize(tSize,tSize);

                    wordBrick.brick.setX(xPos);
                    wordBrick.letterSprite.setX(xPos);
                    xPos+= tSize;
                }else {
                    wordBrick.brick.setX(xPos);
                    wordBrick.letterSprite.setX(wordBrick.brick.getX() + (wordBrick.brick.getWidth() - wordBrick.letterSprite.getWidth()) / 2);

                    xPos += adder;
                }
            }

    }

    public void removeAllLetters() {
        word.clear();
        xPos = -((game.screenWidth * 0.85f) / PPM / 2) + adder;
    }

    public void draw(SpriteBatch batch) {
        if (word.size() == 0 || word.size() >= maxWordLength) {
            return;
        }

        Iterator<WordBrick> drawIter = word.iterator();
        while (drawIter.hasNext()) {
            WordBrick wordBrick = drawIter.next();

            wordBrick.brick.draw(batch);
            wordBrick.letterSprite.draw(batch);
        }
    }


    private class WordBrick {

        Sprite brick;
        Sprite letterSprite;

        String letter;
        float posX;
        float posY;

        public WordBrick(String l, float x, float y) {
            letter = l;
            posX = x;
            posY = y;

            brick = new Sprite(game.menuItems.findRegion("brick"));
            brick.setSize(brick.getWidth() / PPM + game.brickSizeHUD, brick.getHeight() / PPM + game.brickSizeHUD);
            brick.setOrigin(brick.getWidth() / 2, brick.getHeight() / 2);
            brick.setPosition(posX, posY);

            letterSprite = new Sprite(game.letters.findRegion(l));
            letterSprite.setSize(letterSprite.getWidth() / PPM + game.brickSizeHUD, letterSprite.getHeight() / PPM + game.brickSizeHUD);
            letterSprite.setOrigin(letterSprite.getWidth() / 2, letterSprite.getHeight() / 2);

            letterSprite.setPosition(brick.getX() + (brick.getWidth() - letterSprite.getWidth()) / 2
                    , brick.getY() + (brick.getHeight() - letterSprite.getHeight()) / 2);

        }

    }
}
