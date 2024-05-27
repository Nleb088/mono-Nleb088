package com.carbon_it.space.launchers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySources({
        @PropertySource("classpath:ariane-launchers.properties"),
        @PropertySource("classpath:rkk_energia-launchers.properties"),
        @PropertySource("classpath:spacex-launchers.properties")
})
public class OrbitalLaunchers {

    @Bean("Ariane 5")
    @Primary
    public OrbitalLauncher arianeFive(@Value("${ariane.max.leo}") int maxLeoPayloadWeight) {
        return new ArianeFive(maxLeoPayloadWeight);
    }

    @Bean
    public OrbitalLauncher energia(@Value("${energia.max.leo}") int maxLeoPayloadWeight) {
        return new Energia(maxLeoPayloadWeight);
    }

    @Bean
    public OrbitalLauncher soyuz(@Value("${soyuz.max.leo}") int maxLeoPayloadWeight) {
        return new Soyuz(maxLeoPayloadWeight);
    }

    @Bean({
            "BFR",
            "Tesla's Preferred Launcher"
    })
    public OrbitalLauncher falconHeavy(@Value("${fh.max.leo}") int maxLeoPayloadWeight) {
        return new FalconHeavy(maxLeoPayloadWeight);
    }
}
