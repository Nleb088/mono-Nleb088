package com.carbon_it.space.launchers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OrbitalLaunchers {

    @Bean("Ariane 5")
    @Primary
    public OrbitalLauncher arianeFive() {
        return new ArianeFive();
    }

    @Bean
    public OrbitalLauncher energia() {
        return new Energia();
    }

    @Bean
    public OrbitalLauncher soyuz() {
        return new Soyuz();
    }

    @Bean({
            "BFR",
            "Tesla's Preferred Launcher"
    })
    public OrbitalLauncher falconHeavy() {
        return new FalconHeavy();
    }
}
