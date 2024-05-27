package com.carbon_it.space.launchers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

// TODO 1 : this class must be considered as a bean definition configuration for Spring
@Configuration
public class OrbitalLaunchers {


    // TODO 2 : instantiate ArianeFive. The bean must have the name "Ariane 5" inside the application context. This bean must be a primary bean inside the application's context.
    @Bean(value = "Ariane 5")
    @Primary
    ArianeFive arianeFive(){
        return new ArianeFive();
    }

    // TODO 3 : instantiate Energia. The bean must have the name "energia" inside the application context, but not using the @Bean's annotation name attribute.
    @Bean
    Energia energia(){
        return new Energia();
    }
    // TODO 4 : instantiate Soyuz. The bean must have the name "soyuz" inside the application context, but not using the @Bean's annotation name attribute.
    @Bean
    Soyuz soyuz(){
        return new Soyuz();
    }
    // TODO 5 : instantiate FalconHeavy. The bean must have the names "BFR" or "Tesla's Preferred Launcher" inside the application context.
    @Bean(value = {"BFR", "Tesla's Preferred Launcher"})
    FalconHeavy falconHeavy(){
        return new FalconHeavy();
    }
}
