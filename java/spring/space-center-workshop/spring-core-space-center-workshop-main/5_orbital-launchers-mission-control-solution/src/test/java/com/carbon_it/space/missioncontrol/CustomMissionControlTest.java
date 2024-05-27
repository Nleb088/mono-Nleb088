package com.carbon_it.space.missioncontrol;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ReflectionUtils.findMethod;

@SpringBootTest
@DisplayName("Custom mission control bean")
class CustomMissionControlTest {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private MissionControl missionControl;

    private AssertableApplicationContext context;

    @BeforeEach
    void setup() {
        context = AssertableApplicationContext.get(() -> applicationContext);
        assertThat(this.context).hasNotFailed();
    }

    @DisplayName("instance method")
    @ParameterizedTest(name = "{0} should be decorated with {1} lifecycle annotation")
    @MethodSource("annotationsToCheck")
    <A extends Annotation> void shouldBeHookedWithAnnotations(String methodName, Class<A> annotationClass) {
        assertThat(AnnotationUtils.findAnnotation(findMethod(missionControl.getClass(), methodName), annotationClass))
                .isNotNull();
    }

    private static Stream<Arguments> annotationsToCheck() {
        return Stream.of(
                Arguments.of("onInit_Annotated", PostConstruct.class),
                Arguments.of("onDestroy_Annotated", PreDestroy.class)
        );
    }

    @Test
    @DisplayName("startup and shutdown hooks should be configured in its bean definition")
    void shouldBeHookedToBeanDefinition() {
        assertThat(context.getBeanFactory().getBeanDefinition("missionControl"))
                .extracting(
                        BeanDefinition::getInitMethodName,
                        BeanDefinition::getDestroyMethodName
                )
                .containsExactly("onInit_JavaConfig", "onDestroy_JavaConfig");
    }

    @Test
    @DisplayName("startup and shutdown hooks should be implemented using Spring's callbacks")
    void shouldBeAwareOfLifecycleHooks() {
        assertThat(missionControl)
                .isInstanceOf(InitializingBean.class)
                .isInstanceOf(DisposableBean.class);
    }
}
