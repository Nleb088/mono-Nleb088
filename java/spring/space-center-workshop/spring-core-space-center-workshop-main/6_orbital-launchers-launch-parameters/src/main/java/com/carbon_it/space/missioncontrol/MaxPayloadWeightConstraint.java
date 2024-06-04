package com.carbon_it.space.missioncontrol;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxPayloadWeightValidator.class)
public @interface MaxPayloadWeightConstraint {
}
