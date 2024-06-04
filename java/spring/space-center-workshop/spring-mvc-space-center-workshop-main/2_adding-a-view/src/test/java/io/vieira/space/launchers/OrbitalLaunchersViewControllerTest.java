package io.vieira.space.launchers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Orbital Launchers View Controller")
class OrbitalLaunchersViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should expose all launchers in an HTML page")
    void shouldExposeAllLaunchersAsHTML() {
        // TODO 2 : write the test ! This test should validate that when getting the HTML view on, we should obtain a view of name launchers.
        //  The data used by the controller should not be supplied using the real repository, you'll be using test doubles. You have examples available on the OrbitalLaunchersControllerTest of how to use a mock.
        //  Also, verify that the HTML is filled in properly. In order to do so, you can help yourself from the MissionControlTest's examples.
        fail("Implement this test");
    }
}
