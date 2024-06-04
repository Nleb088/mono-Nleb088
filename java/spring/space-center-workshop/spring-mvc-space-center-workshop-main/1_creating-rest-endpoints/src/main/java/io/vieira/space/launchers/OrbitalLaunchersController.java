package io.vieira.space.launchers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// TODO 2 : mark this class to be considered as a REST controller. This controller is supposed to serve as an API, so it should be mapped to the /api path
@RestController()
public class OrbitalLaunchersController {


    private final OrbitalLaunchersRepository orbitalLaunchersRepository;

    public OrbitalLaunchersController(OrbitalLaunchersRepository orbitalLaunchersRepository) {
        this.orbitalLaunchersRepository = orbitalLaunchersRepository;
    }

    // TODO 3 : create a method handling get requests for all orbital launchers
    @GetMapping("api/launchers")
    public ResponseEntity<Iterable<OrbitalLauncher>> getLaunchers(){
        return ResponseEntity.ok(orbitalLaunchersRepository.findAll());
    }

    // TODO 4 : create a method handling get requests for a specific orbital launcher, using its codename
    @GetMapping("api/launchers/{codeName}")
    public ResponseEntity<OrbitalLauncher> getLauncherByCodeName(@PathVariable String codeName){
        Optional<OrbitalLauncher> launcher  = orbitalLaunchersRepository.findByCodeName(codeName);
        return launcher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
