package com.carbon_it.space.missioncontrol;

import com.carbon_it.space.missioncontrol.validation.MaxPayloadWeight;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@MaxPayloadWeight
public class LaunchRequest {

    @NotBlank
    private String launcher;

    @Min(1)
    private int payloadWeight;

    public LaunchRequest(String launcher, int payloadWeight) {
        this.launcher = launcher;
        this.payloadWeight = payloadWeight;
    }

    public String getLauncher() {
        return launcher;
    }

    public int getPayloadWeight() {
        return payloadWeight;
    }
}
