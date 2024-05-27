package com.carbon_it.space.launchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Orbital Launchers configuration")
class OrbitalLaunchersConfigurationTest {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private AbstractEnvironment environment;

    private AssertableApplicationContext context;

    @BeforeEach
    void setup() {
        this.context = AssertableApplicationContext.get(() -> applicationContext);
        assertThat(this.context).hasNotFailed();
    }

    @DisplayName("should contain configuration property")
    @ParameterizedTest(name = "{0} with value {1}")
    @MethodSource("maxLeosConfigurations")
    void shouldPopulateEnvironment(String configurationKey, int expectedMaxLeoWeight) {
        assertThat(environment.containsProperty(configurationKey)).isTrue();
        assertThat(environment.getProperty(configurationKey, Integer.class)).isEqualTo(expectedMaxLeoWeight);
    }

    private static Stream<Arguments> maxLeosConfigurations() {
        return Stream.of(
                Arguments.of("ariane.max.leo", 16000),
                Arguments.of("soyuz.max.leo", 7020),
                Arguments.of("energia.max.leo", 100000),
                Arguments.of("fh.max.leo", 63800)
        );
    }

    @DisplayName("should configure space launcher")
    @ParameterizedTest(name = "{0} with max LEO weight {1}")
    @MethodSource("launchersAndMaxLeos")
    void shouldConfigureProperlyAllLaunchers(String launcherName, int expectedMaxLeoWeight) {
        assertThat(context)
                .getBean(launcherName)
                .extracting("maxLeoPayloadWeight")
                .isEqualTo(expectedMaxLeoWeight);
    }

    private static Stream<Arguments> launchersAndMaxLeos() {
        return Stream.of(
                Arguments.of("Ariane 5", 16000),
                Arguments.of("soyuz", 7020),
                Arguments.of("energia", 100000),
                Arguments.of("BFR", 63800)
        );
    }
}
