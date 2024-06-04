package io.vieira.space.launchers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrbitalLaunchersViewController {

    private final OrbitalLaunchersRepository launchersRepository;

    public OrbitalLaunchersViewController(OrbitalLaunchersRepository launchersRepository) {
        this.launchersRepository = launchersRepository;
    }

    @GetMapping(value = "/launchers")
    String getLaunchers(Model model) {
        model.addAttribute("launchers", launchersRepository.findAll());
        return "launchers";
    }
}
