package com.carbon_it.space.missioncontrol;

import com.carbon_it.space.exception.ImpossibleLaunchException;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

public class ValidatingMissionControl implements MissionControl {

    private final Validator validator;

    public ValidatingMissionControl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void launch(LaunchRequest launchRequest) {
        BindException exception = new BindException(launchRequest, "launchRequest");
        validator.validate(launchRequest, exception);

        if (exception.hasErrors()) {
            throw new ImpossibleLaunchException(exception);
        }
    }
}
