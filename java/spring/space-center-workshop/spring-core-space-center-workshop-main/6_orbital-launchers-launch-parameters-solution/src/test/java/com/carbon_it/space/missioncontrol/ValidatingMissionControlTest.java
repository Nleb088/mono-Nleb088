package com.carbon_it.space.missioncontrol;

import com.carbon_it.space.exception.ImpossibleLaunchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@DisplayName("Validating mission control")
class ValidatingMissionControlTest {

    @Autowired
    private MissionControl missionControl;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private AssertableApplicationContext context;

    @BeforeEach
    void setup() {
        this.context = AssertableApplicationContext.get(() -> applicationContext);
        assertThat(this.context).hasNotFailed();
    }

    @Test
    @DisplayName("should have bean validation configured")
    void shouldSupportBeanValidation() {
        assertThat(context).hasBean("defaultValidator");
    }

    @Test
    @DisplayName("should allow valid launch requests")
    void shouldAllowValidLaunchRequests() {
        missionControl.launch(new LaunchRequest("energia", 500));
    }

    @DisplayName("should deny launch requests because of")
    @MethodSource("invalidLaunchRequests")
    @ParameterizedTest(name = "{0}")
    void shouldDisallowInvalidLaunchRequestsForReason(String reason, LaunchRequest request) {
        Throwable thrown = catchThrowable(() -> missionControl.launch(request));

        assertThat(thrown).isInstanceOf(ImpossibleLaunchException.class);
    }

    private static Stream<Arguments> invalidLaunchRequests() {
        return Stream.of(
                Arguments.of("negative max payload weight", new LaunchRequest("energia", -1)),
                Arguments.of("not found max payload weight", new LaunchRequest("Energia", -1)),
                Arguments.of("not supplied launcher", new LaunchRequest(null, 5000)),
                Arguments.of("empty launcher name", new LaunchRequest("", 5000)),
                Arguments.of("launch max payload weight greater than the launcher's max payload weight", new LaunchRequest("energia", 1000000))
        );
    }
}
