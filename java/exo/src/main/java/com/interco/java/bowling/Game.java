package com.interco.java.bowling;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final List<Frame> frames = new ArrayList<>();
    private int score = 0;
    private int turn = 1;
    private Frame currentFrame = null;

    public void roll(Integer pins) {
        currentFrame.roll(pins);

        if (currentFrame.isFrameOver()) {
            Frame lastFrame = this.frames.get(turn - 1);
            turn++;
            score += currentFrame.calculateFrameScore(lastFrame);
            frames.add(currentFrame);
            currentFrame = new Frame();
        }
    }

    public Integer score() {
        return score;
    }


}
