package io.vieira.space.launchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrbitalLaunchersController.class)
@DisplayName("Orbital Launchers REST Controller")
class OrbitalLaunchersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrbitalLaunchersRepository launchersRepository;

    @Test
    @DisplayName("should get all launchers")
    void shouldGetAllLaunchers() throws Exception {
        final List<OrbitalLauncher> orbitalLaunchers = Collections.singletonList(new OrbitalLauncher(1, "Ariane 5", "ariane-5", 16000));
        when(launchersRepository.findAll()).thenReturn(orbitalLaunchers);

        mockMvc.perform(get("/api/launchers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(orbitalLaunchers), true));

        verify(launchersRepository).findAll();
        verifyNoMoreInteractions(launchersRepository);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("should get launcher")
    @ValueSource(strings = {
            "ariane-5",
            "energia",
            "soyuz",
            "falcon-heavy"
    })
    void shouldGetLauncher(String codeName) throws Exception {
        final OrbitalLauncher orbitalLauncher = new OrbitalLauncher(1, "The launcher that has been asked", codeName, 16000);
        when(launchersRepository.findByCodeName(anyString())).thenReturn(Optional.of(orbitalLauncher));

        mockMvc.perform(get("/api/launchers/{codeName}", codeName).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(orbitalLauncher), true));

        verify(launchersRepository).findByCodeName(codeName);
        verifyNoMoreInteractions(launchersRepository);
    }

    @Test
    @DisplayName("should return 404 Not Found when trying to get an unknown launcher")
    void shouldReturnNotFoundWhenGettingUnknownLauncher() throws Exception {
        mockMvc.perform(get("/api/launchers/not-existing").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(launchersRepository).findByCodeName("not-existing");
        verifyNoMoreInteractions(launchersRepository);
    }
}
