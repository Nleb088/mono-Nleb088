package io.vieira.space.launchers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrbitalLaunchersViewController {

    private final OrbitalLaunchersRepository launchersRepository;

    public OrbitalLaunchersViewController(OrbitalLaunchersRepository launchersRepository) {
        this.launchersRepository = launchersRepository;
    }

    @GetMapping(value = "/launchers")
    ModelAndView getLaunchers() {
        return new ModelAndView("launchers")
                .addObject("launchers", launchersRepository.findAll());
    }
}
