package com.carbon_it.space.launchers;

public class Soyuz implements OrbitalLauncher {

    private final int maxLeoPayloadWeight;

    public Soyuz(int maxLeoPayloadWeight) {
        this.maxLeoPayloadWeight = maxLeoPayloadWeight;
    }

    @Override
    public boolean canLaunch(int payloadWeight) {
        return payloadWeight <= this.maxLeoPayloadWeight;
    }
}
