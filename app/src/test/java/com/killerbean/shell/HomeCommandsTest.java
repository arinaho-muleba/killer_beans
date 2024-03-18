package com.killerbean.shell;

import com.killerbean.shell.commands.HomeCommands;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeCommandsTest {

    @Test
    public void testGetQuantity() {
        // Prepare
        String input = "5\n"; // User input
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Call method under test
        HomeCommands homeCommands = new HomeCommands();
        int result = homeCommands.getQuantity(scanner);

        // Assert
        assertEquals(5, result);
    }

    @Test
    public void testAskToProceed() {
        // Prepare
        String input = "1\n"; // User input for Yes
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Call method under test
        int result = HomeCommands.askToProceed("Would you like to proceed?");

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void testAskFatalityPeriod() {
        // Prepare
        String input = "3\n"; // User input for 4-6 days
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Call method under test
        int result = HomeCommands.askFatalityPeriod();

        // Assert
        assertEquals(3, result);
    }

}
