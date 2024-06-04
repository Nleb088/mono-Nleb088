package io.vieira.space.launchers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/launchers")
@CrossOrigin(methods = {GET})
public class OrbitalLaunchersController {

    private final OrbitalLaunchersRepository orbitalLaunchersRepository;

    public OrbitalLaunchersController(OrbitalLaunchersRepository orbitalLaunchersRepository) {
        this.orbitalLaunchersRepository = orbitalLaunchersRepository;
    }

    @GetMapping
    public Iterable<OrbitalLauncher> getLaunchers() {
        return orbitalLaunchersRepository.findAll();
    }

    @GetMapping("/{codeName}")
    public ResponseEntity<OrbitalLauncher> getLauncher(@PathVariable("codeName") String codeName) {
        return ResponseEntity.of(orbitalLaunchersRepository.findByCodeName(codeName));
    }
}
