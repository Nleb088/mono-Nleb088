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

    public LaunchesController(@Value("classpath:launch-transcripts/last.txt") Resource lastLaunchTranscript) {
        this.lastLaunchTranscript = lastLaunchTranscript;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    void launch(@RequestBody @Valid LaunchRequest launchRequest) {
        // do nothing
    }

    @GetMapping(value = "/last-transcript", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getLastLaunchTranscript() {
        return lastLaunchTranscript;
    }
}
