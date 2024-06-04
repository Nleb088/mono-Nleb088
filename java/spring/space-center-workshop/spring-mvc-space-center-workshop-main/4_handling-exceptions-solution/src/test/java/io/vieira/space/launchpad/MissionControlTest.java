package io.vieira.space.launchpad;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MissionControlConfiguration.class)
@DisplayName("Mission Control controller")
class MissionControlTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should display mission control page configured from WebMvcConfigurer")
    void shouldDisplayMissionControlPage() throws Exception {
        mockMvc.perform(get("/mission-control"))
                .andExpect(status().isOk())
                .andExpect(view().name("mission-control"))
                .andExpect(xpath("/html/body/h1").string("Hello, this is the mission control."));
    }
}
