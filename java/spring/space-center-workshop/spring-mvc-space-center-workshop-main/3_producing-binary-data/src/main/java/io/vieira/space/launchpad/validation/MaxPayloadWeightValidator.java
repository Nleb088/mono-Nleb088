package io.vieira.space.launchpad.validation;

import io.vieira.space.launchers.OrbitalLauncher;
import io.vieira.space.launchers.OrbitalLaunchersRepository;
import io.vieira.space.launchpad.LaunchRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class MaxPayloadWeightValidator implements ConstraintValidator<MaxPayloadWeight, LaunchRequest> {

    private final OrbitalLaunchersRepository repository;

    public MaxPayloadWeightValidator(OrbitalLaunchersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(MaxPayloadWeight constraint) {
    }

    @Override
    public boolean isValid(LaunchRequest value, ConstraintValidatorContext context) {
        final Optional<OrbitalLauncher> orbitalLauncher = repository.findByCodeName(value.getLauncher());

        if (!orbitalLauncher.isPresent()) {
            context.buildConstraintViolationWithTemplate("The provided launcher does not exist")
                    .addPropertyNode("launcher")
                    .addConstraintViolation();
            return false;
        }

        final boolean payloadWeightLowerThanLauncherMaxLeo = orbitalLauncher
                .map(OrbitalLauncher::getMaxLeo)
                .filter(maxPayloadWeight -> maxPayloadWeight >= value.getPayloadWeight())
                .isPresent();

        if (!payloadWeightLowerThanLauncherMaxLeo) {
            context
                    .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("payloadWeight")
                    .addConstraintViolation();
        }

        return payloadWeightLowerThanLauncherMaxLeo;
    }
}
