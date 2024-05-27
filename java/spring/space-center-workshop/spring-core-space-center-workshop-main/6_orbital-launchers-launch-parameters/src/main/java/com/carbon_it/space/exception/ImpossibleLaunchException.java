package com.carbon_it.space.exception;

import org.springframework.validation.BindException;

public class ImpossibleLaunchException extends RuntimeException {

    public ImpossibleLaunchException(BindException exception) {
        super(exception);
    }
}
