package com.carbon_it.space.launchers.inspection;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO 1 : Any beans marked with this annotation should be available when the profile "inspection" is activated. They should still be available using any default execution profile.
//  Make sure that this annotation discoverable by Spring at runtime
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Profile("default | inspection")
public @interface Launchable {
}
