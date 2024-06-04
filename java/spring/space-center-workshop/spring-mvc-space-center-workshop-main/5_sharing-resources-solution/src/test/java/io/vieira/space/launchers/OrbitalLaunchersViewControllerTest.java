package io.vieira.space.launchers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsIn.in;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrbitalLaunchersViewController.class)
@DisplayName("Orbital Launchers View Controller")
class OrbitalLaunchersViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrbitalLaunchersRepository orbitalLaunchersRepository;

    @Test
    @DisplayName("should expose all launchers in an HTML page")
    void shouldExposeAllLaunchersAsHTML() throws Exception {
        final List<String> launcherNames = Arrays.asList("energia", "falcon-heavy", "ariane-5", "soyuz");
        final List<String> launcherLeos = Arrays.asList(
                "Max payload weight for LEO : 100000",
                "Max payload weight for LEO : 63800",
                "Max payload weight for LEO : 16000",
                "Max payload weight for LEO : 7020"
        );

        final List<OrbitalLauncher> launchers = Arrays.asList(
                new OrbitalLauncher("", "energia", 100000),
                new OrbitalLauncher("", "falcon-heavy", 63800),
                new OrbitalLauncher("", "ariane-5", 16000),
                new OrbitalLauncher("", "soyuz", 7020)
        );
        when(orbitalLaunchersRepository.findAll()).thenReturn(launchers);

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

        verify(orbitalLaunchersRepository).findAll();
        verifyNoMoreInteractions(orbitalLaunchersRepository);
    }
}
