package com.killerbean.shell;

import com.killerbean.shell.commands.HomeCommands;
import com.killerbean.shell.model.Address;
import com.killerbean.shell.model.Bean;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HomeCommandsTest {

    @Test
    public void testGetQuantity() {
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("5\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HomeCommands homeCommands = new HomeCommands();
        int quantity = homeCommands.getQuantity(scanner, 10, 0);

        assertEquals(5, quantity);
        assertTrue(outContent.toString().contains(HomeCommands.PROMPT_ENTER_QUANTITY));
        assertTrue(outContent.toString().contains("\n"));

        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    public void testTakeAddress() {
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("5 Smith Street\nSandton\nJohannesburg\n".getBytes());
        System.setIn(in);

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HomeCommands homeCommands = new HomeCommands();
        Address address = homeCommands.takeAddress();

        assertEquals("5 Smith Street", address.getStreetAddress());
        assertEquals("Sandton", address.getSuburb());
        assertEquals("Johannesburg", address.getCity());

        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    public void testReadChoice() {
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HomeCommands homeCommands = new HomeCommands();
        Map<Integer, String> options = Map.of(1, "Option 1", 2, "Option 2");
        int choice = HomeCommands.readChoice(options, scanner);

        assertEquals(2, choice);
        assertTrue(outContent.toString().contains("Enter the number for your choice: "));

        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);
    }



    @Test
    public void testDisplayOrderDetails() {
        // Create sample order details
        Map<Long, HomeCommands.OrderDetail> orderDetails = Map.of(
                1L, new HomeCommands.OrderDetail("Bean 1", 5, new BigDecimal("25.0")),
                2L, new HomeCommands.OrderDetail("Bean 2", 3, new BigDecimal("15.0"))
        );

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HomeCommands homeCommands = new HomeCommands();
        homeCommands.displayOrderDetails(orderDetails);

        String actualOutput = outContent.toString();
        assertTrue(actualOutput.contains("Order Summary"));
        assertTrue(actualOutput.matches("(?s).*Bean 1\\s+5\\s+R25[,.]0.*")); // Match Bean 1 entry with quantity and cost
        assertTrue(actualOutput.matches("(?s).*Bean 2\\s+3\\s+R15[,.]0.*")); // Match Bean 2 entry with quantity and cost
        assertTrue(actualOutput.contains("Overall Total Cost: R40.00")); // Match overall total cost

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    public void testGetQuantity_InvalidInput() {
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("invalid\n5\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HomeCommands homeCommands = new HomeCommands();
        int quantity = homeCommands.getQuantity(scanner, 10, 0);

        assertEquals(5, quantity);
        assertTrue(outContent.toString().contains(HomeCommands.PROMPT_ENTER_QUANTITY));
        assertTrue(outContent.toString().contains(HomeCommands.ERROR_INVALID_NUMBER));

        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);
    }

}
