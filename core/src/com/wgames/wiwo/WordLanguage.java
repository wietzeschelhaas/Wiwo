package com.wgames.wiwo;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wietze on 2016-07-06.
 */
public class WordLanguage {
    String language;

    ArrayList<Character> characters = new ArrayList<Character>();

    Map<Character, Integer> charPointMap = new HashMap<Character, Integer>();

    public WordLanguage(String l){
        language = l;
        if(language == "en"){
            createLetters('e',12,1);
            createLetters('a',9,1);
            createLetters('i',9,1);
            createLetters('o',8,1);
            createLetters('n',6,1);
            createLetters('r',6,1);
            createLetters('t',6,1);
            createLetters('l',4,1);
            createLetters('s',4,1);
            createLetters('u',4,1);

            createLetters('d',4,2);
            createLetters('g',3,2);

            createLetters('b',2,3);
            createLetters('c',2,3);
            createLetters('m',2,3);
            createLetters('p',2,3);

            createLetters('f',2,4);
            createLetters('h',2,4);
            createLetters('v',2,4);
            createLetters('w',2,4);
            createLetters('y',2,4);

            createLetters('k',2,5);

            createLetters('j',1,8);

            createLetters('x',1,8);

            createLetters('q',1,10);

        }
        else if(language == "sv"){

        }
        else if(language == "nl"){

        }
        else if(language == "es"){

        }
    }

    private void createLetters(char letter, int amount,int points){
        int i = 0;
        while (i < amount){
            characters.add(letter);
            i = i +1;
        }

        charPointMap.put(letter,points);


    }

    public char getRandomChar(){

        float randomElement = characters.size() * MathUtils.random();
        return characters.get((int)randomElement);

    }

    public int getPoint(char letter){
        return charPointMap.get(letter);
    }

}
