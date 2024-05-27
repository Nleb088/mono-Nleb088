package com.carbon_it.space.launchers;

import org.springframework.beans.factory.annotation.Value;

public class Soyuz implements OrbitalLauncher {

    // TODO 5 : inject the Soyuz's max payload weight for a leo path, using the property soyuz.max.leo. The field name must be maxLeoPayloadWeight.
    int maxLeoPayloadWeight;

    public Soyuz(int maxLeoPayloadWeight) {
        this.maxLeoPayloadWeight = maxLeoPayloadWeight;
    }

    @Override
    public boolean canLaunch(int payloadWeight) {
        return false;
    }
}
