package io.vieira.space.launchpad;

import io.vieira.space.exceptions.FlawedORingException;
import io.vieira.space.exceptions.UnavailableLaunchpadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/launches")
public class LaunchesController {

    private final Resource lastLaunchTranscript;

    private final LaunchPad launchPad;

    public LaunchesController(@Value("classpath:launch-transcripts/last.txt") Resource lastLaunchTranscript,
                              LaunchPad launchPad) {
        this.lastLaunchTranscript = lastLaunchTranscript;
        this.launchPad = launchPad;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    void launch(@RequestBody @Valid LaunchRequest launchRequest) throws UnavailableLaunchpadException {
        launchPad.launch(launchRequest);
    }

    @GetMapping(value = "/last-transcript", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getLastLaunchTranscript() {
        return lastLaunchTranscript;
    }

    @ExceptionHandler(UnavailableLaunchpadException.class)
    ResponseEntity<?> handleUnavailableLaunchpad() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .build();
    }

    @ExceptionHandler(FlawedORingException.class)
    ResponseEntity<?> handleFlawedORing() {
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_REQUIRED)
                .build();
    }
}
