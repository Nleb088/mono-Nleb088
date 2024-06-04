package io.vieira.space.launchers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Orbital Launchers View Controller")
@WebMvcTest(OrbitalLaunchersViewController.class)
class OrbitalLaunchersViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrbitalLaunchersRepository launchersRepository;

    @Test
    @DisplayName("should expose all launchers in an HTML page")
    void shouldExposeAllLaunchersAsHTML() throws Exception {
        List<String> launcherNames = Arrays.asList( "ariane-5",
                "energia",
                "soyuz",
                "falcon-heavy");
        
        List<OrbitalLauncher> launchers = Arrays.asList(new OrbitalLauncher("ariane-5","ariane-5",16000),
                new OrbitalLauncher("energia","energia",100000),
                new OrbitalLauncher("soyuz","soyuz",7020),
                new OrbitalLauncher("falcon-heavy","falcon-heavy",63800));

        List<String> launcherLeos = Arrays.asList( "Max payload weight for LEO : 16000",
                "Max payload weight for LEO : 100000",
                "Max payload weight for LEO : 7020",
                "Max payload weight for LEO : 63800");


        when(launchersRepository.findAll()).thenReturn(launchers);


        mockMvc
                .perform(get("/launchers").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("launchers"))
                .andExpect(model().attributeExists("launchers"))
                .andExpect(model().attribute("launchers", equalTo(launchers)))
                .andExpect(xpath("/html/body/h1").string("There is actually 4 launchers registered."))
                .andExpect(xpath("/html/body/h2").nodeCount(4))
                .andExpect(xpath("/html/body/h2").string(in(launcherNames)))
                .andExpect(xpath("/html/body/p").string(in(launcherLeos)));
    }
}
