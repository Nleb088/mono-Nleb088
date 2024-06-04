package io.vieira.space.launchpad;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

// TODO 5 : mark this class as a REST controller
@RestController
public class LaunchesController {

    @PostMapping("/api/launches")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void launch(@RequestBody @Valid LaunchRequest launchRequest) {

    }

    // TODO 6 : create a method handling POST requests to enqueue a launch request (there is no business logic to implement).
    //  As the processing is asynchronous, a custom HTTP code (202, Accepted) must be created.
    // TODO 7 : the endpoint MUST validate the json model passed as a body through bean validation.
}
