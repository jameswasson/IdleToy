package com.example.james.idletoy;

import org.junit.Test;

import java.util.Random;

import Game.Game;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void numberDisplay_isCorrect(){
        for (int i = 0; i < 30; i++)
            System.out.println(Game.convertDoubleToChosenFormat(Math.pow(10,i)));

        System.out.println(Game.convertDoubleToChosenFormat(Math.pow(10,9)));
    }

    @Test
    public void canReadAndWrite(){
        String startString = "Hello World";
        Game.getGame().writeToFile(startString, "test.txt");
        String endString = Game.getGame().readFromFile("test.txt");
        assertEquals(endString, startString);
    }



}