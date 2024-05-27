package com.carbon_it.space.launchers;

public class FalconHeavy implements OrbitalLauncher {

    private final int maxLeoPayloadWeight;

    public FalconHeavy(int maxLeoPayloadWeight) {
        this.maxLeoPayloadWeight = maxLeoPayloadWeight;
    }

    @Override
    public boolean canLaunch(int payloadWeight) {
        return payloadWeight <= this.maxLeoPayloadWeight;
    }
}
