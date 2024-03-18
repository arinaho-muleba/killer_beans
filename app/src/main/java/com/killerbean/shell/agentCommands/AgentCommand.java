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
            if(orderService.takeOrder(selectedOrder.getId())) {
                System.out.println("Order confirmed.\nUse 'my-orders' command  to view orders assigned to you");
                return;
            }else {
                System.out.println("Failed to assign order to you, try again later");
                return;
            }
        } else {
            System.out.println("Order canceled.");
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

        System.out.println("Are you sure you want to progress the status of this order?\n1. Yes\n2. No");
        int choice;
        do {
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.println("Enter your choice: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            if (choice < 1 || choice > 2) {
                System.out.println("Invalid input. Please enter 1 or 2.");
            }
        } while (choice < 1 || choice > 2);

        if (choice == 1) {
            if (orderService.progressOrder(selectedOrder.getId())) {
                System.out.println("Order Progressed");
                orders = orderService.fetchOrdersAssignedToMe();
                OrderProcessor.viewOrders(orders);
                return;
            } else {
                System.out.println("Failed to progress order to you, try again later");
                return;
            }
        } else {
            System.out.println("Order progress canceled.");
            return;
        }
    }



}
