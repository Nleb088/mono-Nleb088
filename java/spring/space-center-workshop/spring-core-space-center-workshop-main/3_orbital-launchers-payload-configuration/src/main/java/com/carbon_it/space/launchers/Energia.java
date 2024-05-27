package com.carbon_it.space.launchers;

import org.springframework.beans.factory.annotation.Value;

public class Energia implements OrbitalLauncher {

    // TODO 3 : inject the Energia's max payload weight for a leo path, using the property energia.max.leo. The field name must be maxLeoPayloadWeight.
    int maxLeoPayloadWeight;

    public Energia(int maxLeoPayloadWeight) {
        this.maxLeoPayloadWeight = maxLeoPayloadWeight;
    }

    @Override
    public boolean canLaunch(int payloadWeight) {
        return false;
    }
}
