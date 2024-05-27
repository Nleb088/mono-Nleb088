package com.carbon_it.space.missioncontrol.validation;

import com.carbon_it.space.missioncontrol.LaunchRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.core.env.Environment;

public class MaxPayloadWeightValidator implements ConstraintValidator<MaxPayloadWeight, LaunchRequest> {

    private Environment environment;

    public MaxPayloadWeightValidator(Environment environment) {
        this.environment = environment;
    }

    public void initialize(MaxPayloadWeight constraint) {
    }

    public boolean isValid(LaunchRequest value, ConstraintValidatorContext context) {
        Integer maxLeo = environment.getProperty(value.getLauncher() + ".max.leo", Integer.class);
        if (maxLeo == null) {
            return false;
        }

        return value.getPayloadWeight() <= maxLeo;
    }
}
