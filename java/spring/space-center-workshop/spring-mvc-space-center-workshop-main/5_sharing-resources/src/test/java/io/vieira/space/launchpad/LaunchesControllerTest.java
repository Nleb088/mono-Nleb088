package io.vieira.space.launchpad;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vieira.space.exceptions.FlawedORingException;
import io.vieira.space.exceptions.UnavailableLaunchpadException;
import io.vieira.space.launchers.OrbitalLauncher;
import io.vieira.space.launchers.OrbitalLaunchersRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LaunchesController.class)
@DisplayName("Launches controller")
class LaunchesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrbitalLaunchersRepository launchersRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:launch-transcripts/last.txt")
    private Resource lastLaunchTranscript;

    @MockBean
    private LaunchPad launchPad;

    @Test
    @DisplayName("should allow launch of a launcher")
    void shouldAllowValidLaunches() throws Exception {
        final String launcherCodeName = "falcon-heavy";
        when(launchersRepository.findByCodeName(launcherCodeName)).thenReturn(Optional.of(
                new OrbitalLauncher(1, "Falcon Heavy", launcherCodeName, 63800)
        ));

        final LaunchRequest launchRequest = new LaunchRequest(launcherCodeName, 2000);

        mockMvc.perform(post("/api/launches")
                        .content(objectMapper.writeValueAsString(launchRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted());

        final InOrder inOrder = inOrder(launchersRepository, launchPad);
        inOrder.verify(launchersRepository).findByCodeName(launcherCodeName);
        inOrder.verify(launchPad).launch(launchRequest);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("should reject launch of a non-existing launcher")
    void shouldRejectLaunchGivenANonExistingLauncher() throws Exception {
        mockMvc
                .perform(
                        post("/api/launches")
                                .content(objectMapper.writeValueAsString(
                                        new LaunchRequest("toto", 12354)
                                ))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(isMethodArgumentNotValidExceptionResult("launcher", "The provided launcher does not exist"));

        verify(launchersRepository).findByCodeName("toto");
        verifyNoMoreInteractions(launchersRepository);
        verifyNoInteractions(launchPad);
    }

    @Test
    @DisplayName("should reject launch of launcher with an invalid name")
    void shouldRejectLaunchGivenAnInvalidLauncherName() throws Exception {
        mockMvc
                .perform(
                        post("/api/launches")
                                .content(objectMapper.writeValueAsString(
                                        new LaunchRequest("", 12354)
                                ))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(isMethodArgumentNotValidExceptionResult("launcher", "must not be empty", "The provided launcher does not exist"));

        verify(launchersRepository).findByCodeName("");
        verifyNoMoreInteractions(launchersRepository);
        verifyNoInteractions(launchPad);
    }

    @Test
    @DisplayName("should reject launch of with a negative payload weight")
    void shouldRejectLaunchGivenANegativePayloadWeight() throws Exception {
        when(launchersRepository.findByCodeName("falcon-heavy")).thenReturn(Optional.of(
                new OrbitalLauncher(1, "Falcon Heavy", "falcon-heavy", 63800)
        ));

        mockMvc
                .perform(
                        post("/api/launches")
                                .content(objectMapper.writeValueAsString(
                                        new LaunchRequest("falcon-heavy", -12)
                                ))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(isMethodArgumentNotValidExceptionResult("payloadWeight", "must be greater than or equal to 1"));

        verify(launchersRepository).findByCodeName("falcon-heavy");
        verifyNoMoreInteractions(launchersRepository);
        verifyNoInteractions(launchPad);
    }

    @Test
    @DisplayName("should reject launch with an existing launcher and a too heavy payload")
    void shouldRejectLaunchGivenAnExistingLauncherAndAPayloadTooHeavy() throws Exception {
        when(launchersRepository.findByCodeName("falcon-heavy")).thenReturn(Optional.of(
                new OrbitalLauncher(1, "Falcon Heavy", "falcon-heavy", 63800)
        ));

        mockMvc
                .perform(
                        post("/api/launches")
                                .content(objectMapper.writeValueAsString(
                                        new LaunchRequest("falcon-heavy", 65000)
                                ))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(isMethodArgumentNotValidExceptionResult("payloadWeight", "The payload is heavier than the accepted max payload weight on the launcher"));

        verify(launchersRepository).findByCodeName("falcon-heavy");
        verifyNoMoreInteractions(launchersRepository);
        verifyNoInteractions(launchPad);
    }

    private ResultMatcher isMethodArgumentNotValidExceptionResult(String invalidField, String... errorMessages) {
        final List<Tuple> erroredFieldsWithMessages = Arrays
                .stream(errorMessages)
                .map(message -> tuple(invalidField, message))
                .collect(Collectors.toList());

        return result -> {
            final Exception resolvedException = result.getResolvedException();
            assertThat(resolvedException)
                    .isNotNull()
                    .isInstanceOf(MethodArgumentNotValidException.class);
            assertThat(((MethodArgumentNotValidException) resolvedException).getBindingResult().getFieldErrors())
                    .extracting(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage)
                    .containsExactlyInAnyOrderElementsOf(erroredFieldsWithMessages);
        };
    }

    @Test
    @DisplayName("should expose last launch transcript as a binary payload")
    void shouldExposeLastLaunchTranscriptBinaries() throws Exception {
        final ByteArrayOutputStream expectedBytes = new ByteArrayOutputStream();
        StreamUtils.copy(lastLaunchTranscript.getInputStream(), expectedBytes);
        mockMvc.perform(get("/api/launches/last-transcript"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().bytes(expectedBytes.toByteArray()));
    }

    @Test
    @DisplayName("should return HTTP 428 on flawed o-rings on launchers")
    void shouldHandleFlawedORingException() throws Exception {
        final String launcherCodeName = "soyuz";
        when(launchersRepository.findByCodeName(anyString())).thenReturn(Optional.of(
                new OrbitalLauncher(1, "Soyuz", launcherCodeName, 7020)
        ));
        doThrow(FlawedORingException.class).when(launchPad).launch(any());

        final LaunchRequest launchRequest = new LaunchRequest(launcherCodeName, 3450);

        mockMvc
                .perform(
                        post("/api/launches")
                                .content(objectMapper.writeValueAsString(launchRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isPreconditionRequired());

        final InOrder inOrder = inOrder(launchersRepository, launchPad);
        inOrder.verify(launchersRepository).findByCodeName(launcherCodeName);
        inOrder.verify(launchPad).launch(launchRequest);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("should return HTTP 503 on unavailable launchpad")
    void shouldHandleUnavailableLaunchPadException() throws Exception {
        final String launcherCodeName = "soyuz";

        when(launchersRepository.findByCodeName(anyString())).thenReturn(Optional.of(
                new OrbitalLauncher(1, "Soyuz", launcherCodeName, 7020)
        ));
        doThrow(UnavailableLaunchpadException.class).when(launchPad).launch(any());

        final LaunchRequest launchRequest = new LaunchRequest(launcherCodeName, 3450);

        mockMvc
                .perform(
                        post("/api/launches")
                                .content(objectMapper.writeValueAsString(launchRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isServiceUnavailable());

        final InOrder inOrder = inOrder(launchersRepository, launchPad);
        inOrder.verify(launchersRepository).findByCodeName(launcherCodeName);
        inOrder.verify(launchPad).launch(launchRequest);
        inOrder.verifyNoMoreInteractions();
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
            "app-a.nasa.gov",
            "tracking.roscosmos.ru",
            "internal.spacex.com"
    })
    @DisplayName("should have CORS configured for origin")
    void shouldHaveCORSConfigured(String origin) throws Exception {
        mockMvc
                .perform(
                        options("/api/launches")
                                .header(HttpHeaders.ORIGIN, origin)
                                .header("Access-Control-Request-Method", "GET")
                )
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, containsString(origin)))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, containsString("GET")));
    }
}
