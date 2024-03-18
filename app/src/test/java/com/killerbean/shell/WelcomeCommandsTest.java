package com.killerbean.shell;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethod;

import com.killerbean.shell.commands.AccessCommands;
import com.killerbean.shell.commands.WelcomeCommands;

@ExtendWith(MockitoExtension.class)
public class WelcomeCommandsTest {

    @Mock
    private AccessCommands accessCommands;

    @InjectMocks
    private WelcomeCommands welcomeCommands;

    @BeforeEach
    public void setup() {
        // Stubbing the connected field in AccessCommands
        AccessCommands.connected = true;
    }

    @Test
    public void testWelcome() {
        String name = "John";
        String expectedWelcomeMessage = "Welcome to Killer Beans " + name;

        String welcomeMessage = welcomeCommands.welcome(name);

        assertThat(welcomeMessage).isEqualTo(expectedWelcomeMessage);
    }

    @Test
    public void testGoodbye() {
        String expectedGoodbyeMessage = "Goodbye";

        String goodbyeMessage = welcomeCommands.goodbye();

        assertThat(goodbyeMessage).isEqualTo(expectedGoodbyeMessage);
    }

    @Test
    public void testAvailabilityCheckWhenConnected() {
        // Stubbing the connected field in AccessCommands
        AccessCommands.connected = true;

        // Testing availability when connected
        Availability availability = welcomeCommands.availabilityCheck();

        assertThat(availability.isAvailable()).isTrue();
    }

    @Test
    public void testAvailabilityCheckWhenDisconnected() {
        // Stubbing the connected field in AccessCommands
        AccessCommands.connected = false;

        // Testing availability when disconnected
        Availability availability = welcomeCommands.availabilityCheck();

        assertThat(availability.isAvailable()).isFalse();
        assertThat(availability.getReason()).isEqualTo("you are not connected. Type sign-in to sign into the system");
    }
}
