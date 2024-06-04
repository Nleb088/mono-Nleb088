package io.vieira.space.launchpad;

import io.vieira.space.exceptions.UnavailableLaunchpadException;
import org.springframework.stereotype.Component;

public interface LaunchPad {

    void launch(LaunchRequest launchRequest) throws UnavailableLaunchpadException;

    @Component
    class DummyLaunchPad implements LaunchPad {

        @Override
        public void launch(LaunchRequest launchRequest) {

        }
    }
}
