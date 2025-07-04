package com.interco.java.bowling;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrameTest {

    private Frame frame;
    private Frame lastFrame;

    @BeforeEach
    void setUp() {
        frame = new Frame();
        lastFrame = new Frame();
    }

    @Test
    @DisplayName("Should update frame score when a single roll is made")
    public void shouldUpdateFrameScoreWhenRolling() {
        frame.roll(5);

        assertEquals(5, frame.calculateFrameScore(lastFrame, false), "Score should be 5 after a single roll.");
    }

    @Test
    @DisplayName("Frame score should equal pins sum when next frame is still empty")
    public void frameScoreShouldEqualPinsSumWhileLastFrameIsStillEmpty() {
        frame.roll(5);
        frame.roll(3);

        assertEquals(8, frame.calculateFrameScore(lastFrame, false), "Score should be sum of rolls when no bonus.");
    }

    @Test
    @DisplayName("Frame score should include bonus from next frame after a spare")
    public void frameScoreShouldEqualPinsSumPlusFirstPinWhenLastFrameIsASpare() {
        lastFrame.roll(5);
        lastFrame.roll(5);
        frame.roll(8);
        frame.roll(1);

        assertEquals(17, frame.calculateFrameScore(lastFrame, false), "Spare score should include next frame's first roll.");
    }

    @Test
    @DisplayName("Frame score should include bonus from next frame after a strike")
    public void frameScoreShouldEqualDoublePinsWhenLastFrameIsAStrike() {
        lastFrame.roll(10);
        frame.roll(8);
        frame.roll(1);

        assertEquals(18, frame.calculateFrameScore(lastFrame, false), "Strike score should include next frame's two rolls.");
    }

    @Test
    @DisplayName("Frame should not accept rolls after it's completed (e.g., after a strike)")
    public void frameScoreShouldNotChangeIfRollingAfterItIsOver() {
        frame.roll(10);
        frame.roll(8);

        assertEquals(10, frame.calculateFrameScore(lastFrame, false), "Frame score should not change if extra rolls are made after completion.");
    }

    @Test
    @DisplayName("Frame score should include bonus from next frame after a strike")
    public void frameScoreShouldEqualPinsSumWhenLastFrameIsAStrikeAndCurrentFrameIsBonus() {
        lastFrame.roll(10);
        frame.roll(8);
        frame.roll(1);

        assertEquals(9, frame.calculateFrameScore(lastFrame, true), "Strike score should include next frame's two rolls.");
    }

    @ParameterizedTest(name = "Rolls ({0},{1}) -> isSpare: {2}")
    @CsvSource({
            "5, 5, true",
            "0, 10, true",
            "1, 9, true",
            "10, 0, false",
            "3, 4, false",
            "7, 0, false"
    })
    @DisplayName("Check if frame is a spare with various roll combinations")
    void testIsSpare(int firstRoll, int secondRoll, boolean expectedIsSpare) {
        frame.roll(firstRoll);
        frame.roll(secondRoll);

        assertEquals(expectedIsSpare, frame.isSpare(),
                () -> String.format("Expected isSpare to be %b for rolls (%d, %d)", expectedIsSpare, firstRoll, secondRoll));
    }

    @ParameterizedTest(name = "Rolls ({0},{1}) -> isStrike: {2}")
    @CsvSource({
            "10, 0, true",
            "0, 0, false",
            "5, 5, false",
            "3, 4, false"
    })
    @DisplayName("Check if frame is a strike with various roll combinations")
    void testIsStrike(int firstRoll, int secondRoll, boolean expectedIsStrike) {
        frame.roll(firstRoll);
        frame.roll(secondRoll);

        assertEquals(expectedIsStrike, frame.isStrike(),
                () -> String.format("Expected isStrike to be %b for rolls (%d, %d)", expectedIsStrike, firstRoll, secondRoll));
    }

    @ParameterizedTest(name = "Rolls ({0},{1}) -> isFrameOver: {2}")
    @CsvSource({
            "10, -1, true",
            "0, 0, true",
            "5, 5, true",
            "5, 0, true",
            "-1, -1, false",
            "5, -1, false",
    })
    @DisplayName("Check if frame is a over with various roll combinations")
    void testIsFrameOver(int firstRoll, int secondRoll, boolean expectedIsFrameOver) {
        if (firstRoll != -1) {
            frame.roll(firstRoll);
        }
        if (secondRoll != -1) {
            frame.roll(secondRoll);
        }

        assertEquals(expectedIsFrameOver, frame.isOver(),
                () -> String.format("Expected isFrameOver to be %b for rolls (%d, %d)", expectedIsFrameOver, firstRoll, secondRoll));
    }
}