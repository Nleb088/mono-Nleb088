package com.carbon_it.space.launchers;

public class ArianeFive implements OrbitalLauncher {

    private final int maxLeoPayloadWeight;

    public ArianeFive(int maxLeoPayloadWeight) {
        this.maxLeoPayloadWeight = maxLeoPayloadWeight;
    }

    @Override
    public boolean canLaunch(int payloadWeight) {
        return payloadWeight <= this.maxLeoPayloadWeight;
    }
}
