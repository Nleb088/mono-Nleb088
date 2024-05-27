package com.carbon_it.space.launchers;

public class ArianeFive implements OrbitalLauncher {

    int maxLeoPayloadWeight;

    public ArianeFive(int maxLeoPayloadWeight) {
        this.maxLeoPayloadWeight = maxLeoPayloadWeight;
    }

    // TODO 2 : inject the Ariane 5's max payload weight for a leo path, using the property ariane.max.leo. The field name must be maxLeoPayloadWeight.
    @Override
    public boolean canLaunch(int payloadWeight) {
        return false;
    }
}
