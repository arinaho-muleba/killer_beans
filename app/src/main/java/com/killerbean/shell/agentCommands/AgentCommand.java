package com.killerbean.shell.agentCommands;

import com.killerbean.shell.Helpers.OrderProcessor;
import com.killerbean.shell.Helpers.OrderService;
import com.killerbean.shell.enums.Urls;
import com.killerbean.shell.model.Order;
import com.killerbean.shell.model.OrderLine;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@ShellComponent
public class AgentCommand {
    private static final String ORDERS_API_URL = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/api/v1/orders/available";
    private static final String MY_ORDERS_API_URL = "http://your-api-url/orders";
    private Map<Integer, Integer> indexToOrderIdMap = new HashMap<>();

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @ShellMethod(key="get-orders", value="List available orders from the API.")
    public void getOrders() {
        OrderService orderService = new OrderService();
        List<Order> orders = orderService.fetchOrders();
        OrderProcessor.viewOrders(orders);
        Scanner scanner = new Scanner(System.in);
        int selectedIndex;
        while (true) {
            System.out.print("Which order would you like to take care of (Enter the index): ");
            if (scanner.hasNextInt()) {
                selectedIndex = scanner.nextInt();
                if (selectedIndex >= 1 && selectedIndex <= orders.size()) {
                    // Valid index, break the loop
                    break;
                } else {
                    System.out.println("Invalid index. Please enter a number between 1 and " + orders.size());
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }

        Order selectedOrder = orders.get(selectedIndex - 1);
//        scanner.close();
        System.out.println("Here are the order details...");
        List<OrderLine> orderLines = orderService.getOrderLine(selectedOrder.getId());

        System.out.println("+-----------------+-----------------+----------------------+-------------------------+-------------------+");
        System.out.println("|      Name       | Time to Kill(D) |       Price(R)       |   Quantity Bought       |   Total Cost(R)   |");
        System.out.println("+-----------------+-----------------+----------------------+-------------------------+-------------------+");


        for (OrderLine orderLine : orderLines) {
            System.out.println(orderLine);
        }
        System.out.println("+-----------------+-----------------+----------------------+-------------------------+-------------------+");
        System.out.println("Confirm order?\n1. Yes \n2. No");

        //Scanner scanner2 = new Scanner(System.in);

        int choice;
        do {
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) { // Check if input is an integer
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.print("Enter your choice: ");
                scanner.next(); // Consume the invalid input
            }
            choice = scanner.nextInt();
            if (choice < 1 || choice > 2) { // Validate the range of input
                System.out.println("Invalid input. Please enter 1 or 2.");
            }
        } while (choice < 1 || choice > 2);

        // Process user's choice
        if (choice == 1) {
            System.out.println("Order confirmed. ");
            // Close the scanner
            //scanner2.close();
            return;
        } else {
            System.out.println("Order canceled.");
            // Close the scanner
           // scanner2.close();
            return;
        }



    }

    @ShellMethod(key="my-orders", value="List available orders from the API.")
    public void myOrders() {
        OrderService orderService = new OrderService();
        List<Order> orders = orderService.fetchOrdersAssignedToMe();
        OrderProcessor.viewOrders(orders);
        Scanner scanner = new Scanner(System.in);
        int selectedIndex;
        while (true) {
            System.out.print("Which order would to progress status (Enter the index): ");
            if (scanner.hasNextInt()) {
                selectedIndex = scanner.nextInt();
                if (selectedIndex >= 1 && selectedIndex <= orders.size()) {
                    // Valid index, break the loop
                    break;
                } else {
                    System.out.println("Invalid index. Please enter a number between 1 and " + orders.size());
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }

        Order selectedOrder = orders.get(selectedIndex - 1);
//        scanner.close();
    }

    private void selectOrder(int number_of_orders) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the index of the order you want to select: ");
        int selectedIndex = scanner.nextInt();


        if (selectedIndex > number_of_orders) {
            System.out.println("Invalid index. Please enter a valid index.");
            selectOrder(number_of_orders);
            return;
        }

        System.out.println("You have selected the following order:");
        System.out.println("Order Number: " + selectedIndex);
        System.out.println();

        Integer orderId = indexToOrderIdMap.get(selectedIndex);
        // Add your code here to perform actions on the selected order
        // For example, you can update the order status to "Assigned to Agent" using the order ID
        updateOrderStatus(orderId);
    }

    private void updateOrderStatus(int selectedIndex) {
        Integer orderId = indexToOrderIdMap.get(selectedIndex);
        if (orderId == null) {
            System.out.println("Invalid index. Please enter a valid index.");
            return;
        }

        // Make a call to your API to update the order status to "Assigned to Agent" using the order ID
        // You need to replace "your-api-url" with the actual URL of your API
        String updateStatusUrl = "http://your-api-url/update-status";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(updateStatusUrl + "?orderId=" + orderId, null);

        System.out.println("Order status updated for order Number: " + selectedIndex);
    }

}
