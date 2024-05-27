package com.carbon_it.space.launchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Orbital Launchers")
class OrbitalLaunchersTest {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private AssertableApplicationContext context;

    @BeforeEach
    void setup() {
        this.context = AssertableApplicationContext.get(() -> applicationContext);
        assertThat(this.context).hasNotFailed();
    }

    @DisplayName("should contain launcher")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
            "Ariane 5",
            "energia",
            "soyuz",
            "BFR",
            "Tesla's Preferred Launcher"
    })
    void shouldFindLauncher(String launcherName) {
        assertThat(context).hasBean(launcherName);
    }

    @Test
    @DisplayName("should have Ariane 5 as the primary space launcher")
    void shouldFindPrimaryLauncher() {
        assertThat(applicationContext.getBeanFactory().getBeanDefinition("Ariane 5").isPrimary()).isTrue();
        assertThat(context)
                .getBean(OrbitalLauncher.class)
                .isInstanceOf(ArianeFive.class);
    }
}
