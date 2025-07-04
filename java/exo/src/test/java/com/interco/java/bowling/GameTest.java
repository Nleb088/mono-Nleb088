package com.interco.java.bowling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    @DisplayName("Should return the right score at any moment of the game")
    public void shouldReturnTheCurrentScore() {
        game.roll(5);
        game.roll(4);
        assertEquals(9, game.score());
        //Strike
        game.roll(10);
        assertEquals(19, game.score());
        game.roll(5);
        game.roll(4);
        assertEquals(37, game.score());
        //Spare
        game.roll(5);
        game.roll(5);
        assertEquals(47, game.score());
        game.roll(3);
        assertEquals(53, game.score());
    }

    @Test
    @DisplayName("Should return the right score at any moment of the game with a strike on last turn")
    public void shouldReturnTheCurrentScoreWithAStrikeOnLastTurn() {
        game.roll(5);
        game.roll(4);
        assertEquals(9, game.score());
        //Strike
        game.roll(10);
        assertEquals(19, game.score());
        game.roll(5);
        game.roll(4);
        assertEquals(37, game.score());
        //Spare
        game.roll(5);
        game.roll(5);
        assertEquals(47, game.score());
        game.roll(3);
        game.roll(3);
        game.roll(0);
        game.roll(0);

        game.roll(0);
        game.roll(0);

        game.roll(0);
        game.roll(0);

        game.roll(0);
        game.roll(0);

        game.roll(10);
        game.roll(0);
        assertEquals(66, game.score());
        //BonusRound
        game.roll(5);
        game.roll(3);
        assertEquals(74, game.score());
    }

    @Test
    @DisplayName("Should return the right score at any moment of the game with a spare on last turn")
    public void shouldReturnTheCurrentScoreWithASpareOnLastTurn() {
        game.roll(5);
        game.roll(4);
        assertEquals(9, game.score());
        //Strike
        game.roll(10);
        assertEquals(19, game.score());
        game.roll(5);
        game.roll(4);
        assertEquals(37, game.score());
        //Spare
        game.roll(5);
        game.roll(5);
        assertEquals(47, game.score());
        game.roll(3);
        game.roll(3);
        game.roll(0);
        game.roll(0);

        game.roll(0);
        game.roll(0);

        game.roll(0);
        game.roll(0);

        game.roll(0);
        game.roll(0);

        game.roll(5);
        game.roll(5);
        assertEquals(66, game.score());
        //BonusRound
        game.roll(5);
        // isNotAccounted
        game.roll(3);
        assertEquals(71, game.score());
    }
}
