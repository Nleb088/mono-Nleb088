package io.vieira.space.launchpad;

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

    @Value("classpath:launch-transcripts/last.txt")
    private Resource lastTranscript;

    @PostMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    void launch(@RequestBody @Valid LaunchRequest launchRequest) {
        // do nothing
    }

    @GetMapping(value = "/last-transcript", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getLastTranscript(){
        return lastTranscript;
    }
}
