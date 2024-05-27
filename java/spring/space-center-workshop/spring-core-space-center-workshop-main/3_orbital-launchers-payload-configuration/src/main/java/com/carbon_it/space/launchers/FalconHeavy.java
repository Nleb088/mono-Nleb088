package com.carbon_it.space.launchers;

import org.springframework.beans.factory.annotation.Value;

public class FalconHeavy implements OrbitalLauncher {

    int maxLeoPayloadWeight;

    public FalconHeavy(int maxLeoPayloadWeight) {
        this.maxLeoPayloadWeight = maxLeoPayloadWeight;
    }
    // TODO 4 : inject the Falcon Heavy's max payload weight for a leo path, using the property fh.max.leo. The field name must be maxLeoPayloadWeight.
    @Override
    public boolean canLaunch(int payloadWeight) {
        return false;
    }
}
