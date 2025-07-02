package com.interco.java.bowling;

public class Frame {
    private final int[] pins = {0, 0};
    private int thrownBowl = 0;

    public void roll(int pinsDown) {
        pins[thrownBowl] = pinsDown;
        thrownBowl++;
    }

    public boolean isFrameOver() {
        return (this.isStrike() || thrownBowl == 2);
    }

    public int calculateFrameScore(Frame lastFrame) {
        int sum = pins[0] * pins[1];
        if (lastFrame != null) {
            if (lastFrame.isSpare()) return sum + pins[0];
            if (lastFrame.isStrike()) return 2 * sum;

        }
        return sum;
    }

    public boolean isSpare() {
        return pins[0] != 10 && pins[1] + pins[0] == 10;
    }

    public boolean isStrike() {
        return pins[0] == 10;
    }

}
