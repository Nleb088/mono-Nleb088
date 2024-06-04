package io.vieira.space.launchers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/launchers")
// TODO 2 : allow CORS requests only on GET methods
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
