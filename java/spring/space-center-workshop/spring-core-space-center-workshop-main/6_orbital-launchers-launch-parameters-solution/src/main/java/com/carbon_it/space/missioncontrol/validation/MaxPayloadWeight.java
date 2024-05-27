package com.carbon_it.space.missioncontrol.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxPayloadWeightValidator.class)
public @interface MaxPayloadWeight {

    String message() default "Invalid max payload weight";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
