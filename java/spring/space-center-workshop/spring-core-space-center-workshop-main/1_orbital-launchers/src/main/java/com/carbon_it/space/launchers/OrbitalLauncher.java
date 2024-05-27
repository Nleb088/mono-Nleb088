package com.carbon_it.space.launchers;

// TODO 1 : create an implementation of OrbitalLauncher
//  named ArianeFive, detectable through the most basic Spring stereotype.
//  The bean must have the name "Ariane 5" inside the application context.
// TODO 2 : create an implementation of OrbitalLauncher named Energia
//  , detectable using the same way as before. Leave Spring autogenerate
//  the bean's name.
// TODO 3 : create an implementation of OrbitalLauncher named Soyuz,
//  detectable using the same way as before.
//  Leave Spring autogenerate the bean's name.
// TODO 4 : create an implementation of OrbitalLauncher named Falcon Heavy,
//  detectable using the same way as
//  before. The bean must have the name "BFR" inside the application context.
public interface OrbitalLauncher {

    default boolean canLaunch(int payloadWeight) {
        return true;
    }
}
