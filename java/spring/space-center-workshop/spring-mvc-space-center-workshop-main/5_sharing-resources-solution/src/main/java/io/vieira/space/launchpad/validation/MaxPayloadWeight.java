package io.vieira.space.launchpad.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxPayloadWeightValidator.class)
public @interface MaxPayloadWeight {

    String message() default "The payload is heavier than the accepted max payload weight on the launcher";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
