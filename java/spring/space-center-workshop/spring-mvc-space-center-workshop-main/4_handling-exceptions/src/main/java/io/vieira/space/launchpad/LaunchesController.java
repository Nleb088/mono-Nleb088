package io.vieira.space.launchpad;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    void launch(@RequestBody @Valid LaunchRequest launchRequest) {
        // TODO 1 : use a Spring MVC exception handler to handle properly UnavailableLaunchpadException,
        //  returning a 503 "Service unavailable" status code
        launchPad.launch(launchRequest);
    }

    @GetMapping(value = "/last-transcript", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getLastLaunchTranscript() {
        return lastLaunchTranscript;
    }

    // TODO 2 : use a Spring MVC exception handler to handle properly FlawedORingException, returning
    //  a 428 "Precondition Required" status code
}
