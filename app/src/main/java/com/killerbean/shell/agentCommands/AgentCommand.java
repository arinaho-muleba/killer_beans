package com.killerbean.shell.agentCommands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@ShellComponent
public class AgentCommand {
    private static final String ORDERS_API_URL = "http://your-api-url/orders";
    private static final String MY_ORDERS_API_URL = "http://your-api-url/orders";
    private Map<Integer, Integer> indexToOrderIdMap = new HashMap<>();

    @ShellMethod(key="get-orders", value="List available orders from the API.")
    public void getOrders() {
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, Object>> orders = restTemplate.getForObject(ORDERS_API_URL, List.class);

        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            System.out.println("Available Orders:");
            int index = 1;
            for (Map<String, Object> order : orders) {
                int orderId = (int) order.get("id");
                indexToOrderIdMap.put(index, orderId);

                String customerPhoneNumber = (String) order.get("customerPhoneNumber");
                String dateTime = (String) order.get("dateTime");
                String address = (String) order.get("address");

                System.out.println("Order Index: " + index);
                System.out.println("Customer Phone Number: " + customerPhoneNumber);
                System.out.println("Date Time: " + dateTime);
                System.out.println("Address: " + address);
                System.out.println();

                index++;
            }

            selectOrder(index);
        }
    }

    @ShellMethod(key="my-orders", value="List available orders from the API.")
    public void myOrders() {
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, Object>> orders = restTemplate.getForObject(MY_ORDERS_API_URL, List.class);

        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            System.out.println("Available Orders:");
            int index = 1;
            for (Map<String, Object> order : orders) {
                int orderId = (int) order.get("id");
                indexToOrderIdMap.put(index, orderId);

                String customerPhoneNumber = (String) order.get("customerPhoneNumber");
                String dateTime = (String) order.get("dateTime");
                String address = (String) order.get("address");

                System.out.println("Order Index: " + index);
                System.out.println("Customer Phone Number: " + customerPhoneNumber);
                System.out.println("Date Time: " + dateTime);
                System.out.println("Address: " + address);
                System.out.println();

                index++;
            }

            selectOrder(index);
        }
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
