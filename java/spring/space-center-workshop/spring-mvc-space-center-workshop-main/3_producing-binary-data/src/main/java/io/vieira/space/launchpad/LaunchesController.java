package io.vieira.space.launchpad;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class LaunchesController {

    @PostMapping("/api/launches")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void launch(@RequestBody @Valid LaunchRequest launchRequest) {
        // do nothing
    }

    // TODO 1 : add an endpoint mapped by the /launches/last-transcript in order to make available to download the last launch transcript (available in your classpath, in the launch-transcripts folder).
    //  This endpoint must be available with the get method and produce a binary payload downloadable by your browser.
    //  Make sure that the RequestMappings don't repeat the same base !
}
