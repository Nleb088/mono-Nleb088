package com.carbon_it.space.launchers;

import com.carbon_it.space.launchers.inspection.Launchable;
import com.carbon_it.space.launchers.inspection.Grounded;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:ariane-launchers.properties"),
        @PropertySource("classpath:rkk_energia-launchers.properties"),
        @PropertySource("classpath:spacex-launchers.properties")
})
public class OrbitalLaunchers {

    @Bean("Ariane 5")
    @Primary
    @Launchable
    public OrbitalLauncher arianeFive(@Value("${ariane.max.leo}") int maxLeoPayloadWeight) {
        return new ArianeFive(maxLeoPayloadWeight);
    }

    @Bean
    @Grounded
    public OrbitalLauncher energia(@Value("${energia.max.leo}") int maxLeoPayloadWeight) {
        return new Energia(maxLeoPayloadWeight);
    }

    @Bean
    @Launchable
    public OrbitalLauncher soyuz(@Value("${soyuz.max.leo}") int maxLeoPayloadWeight) {
        return new Soyuz(maxLeoPayloadWeight);
    }

    @Bean({
            "BFR",
            "Tesla's Preferred Launcher"
    })
    @Launchable
    public OrbitalLauncher falconHeavy(@Value("${fh.max.leo}") int maxLeoPayloadWeight) {
        return new FalconHeavy(maxLeoPayloadWeight);
    }
}
