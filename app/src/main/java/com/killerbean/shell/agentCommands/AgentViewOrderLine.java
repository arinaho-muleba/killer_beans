package com.killerbean.shell.agentCommands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.*;

@ShellComponent
public class AgentViewOrderLine {

    private static final Map<Integer, ArrayList<String>> ORDERS = new HashMap<>();

    static {
        // Sample data for each order: order ID, order date, bean name, quantity bought, status, assigned agent
        addOrder(1, "2024-03-12", "Deadly Nightshade Bean", 3, "In Transit", "John");
        addOrder(2, "2024-03-13", "Rosary Pea Bean", 2, "Received", "No Assignment yet");
        addOrder(3, "2024-03-14", "Jimsonweed Bean", 1, "Assigned to agent", "Thabo");
        addOrder(4, "2024-03-15", "Belladonna Bean", 4, "Delivered", "Sipho");
        addOrder(5, "2024-03-16", "Aconite Bean", 2, "In Transit", "David");
        addOrder(6, "2024-03-17", "Hemlock Bean", 3, "Received", "No Assignment yet");
    }
    private static void addOrder(int orderId, String orderDate, String beanName, int quantity, String status, String assignedAgent) {
        ArrayList<String> orderInfo = new ArrayList<>(List.of(
                String.valueOf(orderId), orderDate, beanName, String.valueOf(quantity), status, assignedAgent
        ));
        ORDERS.put(orderId, orderInfo);
    }

    public static  Map<Integer, ArrayList<String>>  getOrders() {
        Map<Integer, ArrayList<String>> orderMap= new HashMap<>();
        int orderIndex = 1;
        for (Map.Entry<Integer, ArrayList<String>> entry : ORDERS.entrySet()) {
            orderMap.put(orderIndex,entry.getValue());
            orderIndex++;
        }
        return orderMap;
    }
    private static void showOrders( Map<Integer, ArrayList<String>> orderMap ) {

        for (Map.Entry<Integer, ArrayList<String>> entry : orderMap.entrySet()) { // API CALL
            int orderId = entry.getKey();
            ArrayList<String> orderDetails = entry.getValue();
            System.out.println(orderId + ". " + orderDetails);
        }
        System.out.println();
    }

    @ShellMethod(key = "view-assigned-orders", value = "You will be able to see the product details of an order assigned to you ")
    public String viewOrderDetails() {
        System.out.println( "Here are the orders assigned to you:\n");
        Map<Integer, ArrayList<String>> orderMap= getOrders();
        showOrders(orderMap);

        System.out.println("Type the index of the order for which you'd like to view the product details.");
        Scanner scanner = new Scanner(System.in);
        int orderChoice = scanner.nextInt();
        if(orderMap.containsKey(orderChoice)){

            // GET OrderLines from API.

        }

        else {
            System.out.println("Invalid order choice");
        }

        return "\n";





    }


}