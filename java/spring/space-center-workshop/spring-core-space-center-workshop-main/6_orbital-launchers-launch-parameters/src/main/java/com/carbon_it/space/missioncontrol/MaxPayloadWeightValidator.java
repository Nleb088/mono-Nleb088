package com.carbon_it.space.missioncontrol;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.core.env.Environment;

public class MaxPayloadWeightValidator implements ConstraintValidator<MaxPayloadWeightConstraint, LaunchRequest> {

    private final Environment environment;

    public MaxPayloadWeightValidator(Environment environment) {
        this.environment = environment;
    }
    @Override
    public void initialize(MaxPayloadWeightConstraint annotation){}

    @Override
    public boolean isValid(LaunchRequest value, ConstraintValidatorContext context) {
        Integer maxLeo = environment.getProperty(value.getLauncher() + ".max.leo", Integer.class);
        if (maxLeo == null){
            return false;
        }
        return maxLeo >= value.getPayloadWeight();
    }
}
