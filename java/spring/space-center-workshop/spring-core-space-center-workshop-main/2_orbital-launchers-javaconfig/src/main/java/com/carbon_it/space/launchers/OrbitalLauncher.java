package com.carbon_it.space.launchers;

public interface OrbitalLauncher {

    default boolean canLaunch(int payloadWeight) {
        return true;
    }
}
