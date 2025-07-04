package com.interco.java.bowling;

public class Frame {
    private final int[] pins = {0, 0};
    private int thrownBowl = 0;

    public void roll(int pinsDown) {
        if (this.isOver())
            return;
        pins[thrownBowl] = pinsDown;
        thrownBowl++;
    }

    public boolean isOver() {
        return (this.isStrike() || thrownBowl == 2);
    }

    public int calculateFrameScore(Frame lastFrame, boolean isBonusRound) {
        if (isBonusRound) return this.bonusScoreForLastFrame(lastFrame);
        return pins[0] + pins[1] + this.bonusScoreForLastFrame(lastFrame);
    }

    private int bonusScoreForLastFrame(Frame lastFrame) {
        if (lastFrame.isSpare()) return pins[0];
        if (lastFrame.isStrike()) return pins[0] + pins[1];
        return 0;
    }

    public boolean isSpare() {
        return pins[0] != 10 && pins[1] + pins[0] == 10;
    }

    public boolean isStrike() {
        return pins[0] == 10;
    }
}
