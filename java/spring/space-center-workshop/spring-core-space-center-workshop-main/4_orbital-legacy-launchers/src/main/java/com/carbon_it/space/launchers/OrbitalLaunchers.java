package com.carbon_it.space.launchers;

import com.carbon_it.space.launchers.inspection.Grounded;
import com.carbon_it.space.launchers.inspection.Launchable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySources({
        @PropertySource("classpath:ariane-launchers.properties"),
        @PropertySource("classpath:rkk_energia-launchers.properties"),
        @PropertySource("classpath:spacex-launchers.properties")
})
// TODO 2 : use the Launchable annotation you just created to mark still used launchers as "Launchable". FYI, these are Ariane Five, Soyuz, and Falcon Heavy.
// TODO 3 : use the Grounded annotation you just created to mark obsolete launchers as "Grounded". FYI, this is the case of Energia.
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
