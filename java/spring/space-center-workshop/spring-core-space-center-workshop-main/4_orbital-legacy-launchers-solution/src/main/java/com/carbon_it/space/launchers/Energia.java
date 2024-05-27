package com.carbon_it.space.launchers;

public class Energia implements OrbitalLauncher {

    private final int maxLeoPayloadWeight;

    public Energia(int maxLeoPayloadWeight) {
        this.maxLeoPayloadWeight = maxLeoPayloadWeight;
    }

    @Override
    public boolean canLaunch(int payloadWeight) {
        return payloadWeight <= this.maxLeoPayloadWeight;
    }
}
