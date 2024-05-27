package com.carbon_it.space.missioncontrol;

// TODO 3 : create a custom property constraint which will use the launcher name, find the max payload weight of this launcher (using the properties from step 3) and compare it to the supplied payload weight. If the supplied payload weight is greater, mark the property as invalid.
public class LaunchRequest {

    // TODO 2 : mark this property as required. It should not be null nor blank.
    private String launcher;

    // TODO 2 : mark this property as required, have a minimum value of 1
    private int payloadWeight;

    public LaunchRequest(String launcher, int payloadWeight) {
        this.launcher = launcher;
        this.payloadWeight = payloadWeight;
    }
}
