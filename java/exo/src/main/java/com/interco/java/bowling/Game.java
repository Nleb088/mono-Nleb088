package com.interco.java.bowling;

public class Game {

    private int lastScore = 0;
    private int score = 0;
    private int turn = 1;
    private Frame currentFrame = new Frame();
    private Frame lastFrame = new Frame();

    public void roll(Integer pins) {
        boolean isBonusRound = turn == 11;
        currentFrame.roll(pins);
        score = lastScore + currentFrame.calculateFrameScore(lastFrame, isBonusRound);
        if (currentFrame.isOver()) {
            lastFrame = currentFrame;
            turn++;
            currentFrame = new Frame();
            lastScore = score;
        }

    }

    public Integer score() {
        return score;
    }

    private boolean allowRoll() {
        return turn < 10 || (turn < 11 && (currentFrame.isSpare() || currentFrame.isStrike()));
    }

}
