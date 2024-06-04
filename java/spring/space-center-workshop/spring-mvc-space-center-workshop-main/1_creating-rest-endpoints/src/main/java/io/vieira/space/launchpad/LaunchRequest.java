package io.vieira.space.launchpad;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vieira.space.launchpad.validation.MaxPayloadWeight;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

// TODO 8 : remember the custom validation we created in the Spring Core workshop ? We're gonna be using it to validate the LaunchRequest.
@MaxPayloadWeight
public class LaunchRequest {


    @NotEmpty
    private final String launcher;

    @Min(1)
    private final int payloadWeight;

    @JsonCreator
    public LaunchRequest(@JsonProperty("launcher") String launcher, @JsonProperty("payloadWeight") int payloadWeight) {
        this.launcher = launcher;
        this.payloadWeight = payloadWeight;
    }

    public String getLauncher() {
        return launcher;
    }

    public int getPayloadWeight() {
        return payloadWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LaunchRequest)) return false;
        LaunchRequest that = (LaunchRequest) o;
        return payloadWeight == that.payloadWeight && Objects.equals(launcher, that.launcher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(launcher, payloadWeight);
    }
}
