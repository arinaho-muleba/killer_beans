package com.killerbean.shell.commands;

import com.killerbean.shell.Helpers.Requests;
import com.killerbean.shell.model.Bean;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

import static com.killerbean.shell.Helpers.BeanProcessor.printBeans;
import static com.killerbean.shell.Helpers.BeanProcessor.processBeanJSON;
import static com.killerbean.shell.Helpers.Requests.*;

@ShellComponent
public class HomeCommands {

    private static final Map<Integer, String> CONFIRMATION = new HashMap<>();
    static {
        CONFIRMATION.put(1, "Yes");
        CONFIRMATION.put(0, "No");

    }


    // FAKE DATA
    private static final Map<Integer, String> OPTIONS = new HashMap<>();
    static {
        OPTIONS.put(1, "Any");
        OPTIONS.put(2, "1-3 days");
        OPTIONS.put(3, "4-6 days");
        OPTIONS.put(4, "7-10 days");
        OPTIONS.put(5, "11+ days");
        OPTIONS.put(6, "Cancel\n");
    }


    private static final Map<Integer, ArrayList<String>> BEANS = new HashMap<>();

    static {







        // Sample data for each bean: name, price, stock quantity, time to kill
//        BEANS.put(1, new ArrayList<>(List.of("Deadly Nightshade Bean", "R5.99", "100", "2")));
//        BEANS.put(2, new ArrayList<>(List.of("Hemlock Bean", "R8.99", "50", "5")));
//        BEANS.put(3, new ArrayList<>(List.of("Castor Bean", "R12.99", "30", "3")));
//        BEANS.put(4, new ArrayList<>(List.of("Aconite Bean", "R15.99", "20", "4")));
//        BEANS.put(5, new ArrayList<>(List.of("Rosary Pea Bean", "R19.99", "10", "7")));
//        BEANS.put(6, new ArrayList<>(List.of("Jimsonweed Bean", "R25.99", "5", "10")));
//        BEANS.put(7, new ArrayList<>(List.of("Monkshood Bean", "R18.50", "8", "6")));
//        BEANS.put(8, new ArrayList<>(List.of("Belladonna Bean", "R22.50", "15", "8")));
    }

    private static final Map<Integer, Function<String, Map<Integer, Bean>>> RANGE_MAP = new HashMap<>();

    static {
        RANGE_MAP.put(1, choice -> getBeanOptions());
        RANGE_MAP.put(2, choice -> getBeanOptions("1", "3"));
        RANGE_MAP.put(3, choice -> getBeanOptions("4", "6"));
        RANGE_MAP.put(4, choice -> getBeanOptions("7", "9"));
        RANGE_MAP.put(5, choice -> getBeanOptions("10"));
    }
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



  //________________________________________________________________________________________________


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
    }


    private static int askToProceed(String question){

        System.out.println(question);
        CONFIRMATION.forEach((index, period) -> System.out.println(index + ". " + period));
        Scanner scanner = new Scanner(System.in);
        return readChoice(CONFIRMATION,scanner);
    }


    private static int askFatalityPeriod(){
        System.out.println("\nAre you interested in any estimated time period in which you'd like our product to take fatal effect?");
        System.out.println("Please select one of the following options:\n");
        OPTIONS.forEach((index, period) -> System.out.println(index + ". " + period));

        Scanner scanner = new Scanner(System.in);
        int choice = readChoice(OPTIONS,scanner);
        return choice;
    }

    private static Map<Integer, Bean> getChosenSetOfBeans(int choice){
        return RANGE_MAP.getOrDefault(choice, HomeCommands::getBeanOptions).apply(String.valueOf(choice));
    }


    public static  Map<Integer, Bean>  getBeanOptions() {
        String beanJSON= getAllRecords("bean");
        return processBeanJSON(beanJSON);
    }


    private static int readChoice(Map<Integer, String> optionMap,Scanner scanner ) {
      System.out.print("Enter the number of your choice: ");
      int choice = scanner.nextInt();
      while (!optionMap.containsKey(choice)) {
          System.out.println("Invalid choice. Please try again.\n");
          System.out.print("\nEnter the number of your choice: ");
          choice = scanner.nextInt();
      }
      return choice;
  }




    public static  Map<Integer, Bean>  getBeanOptions(String minDays, String maxDays) {

        String beanJSON= getRecordsWith2Parameters("bean/getByTimeToKillRange","minTimeToKill=" +minDays,"maxTimeToKill="+maxDays);
        return processBeanJSON(beanJSON);
    }

    public static Map<Integer, Bean> getBeanOptions(String minDays) {
        String beanJSON= getRecordsWith1Parameter("bean/getByTimeToKillRange",minDays);
        return processBeanJSON(beanJSON);
    }
    public static void showBeanOptions( Map<Integer, Bean> beanOptions) {
        printBeans(beanOptions);
    }

    private static void printBeanChoices(int beanId, ArrayList<String> beanInfo){
        System.out.println( beanId + "." + beanInfo );
    }
    private static void printNoStock(){
      System.out.println("No beans currently in stock within this effective period.");
    }






    private int getQuantity(Scanner scanner) {
        int quantity = 0;
        while (quantity == 0) {
            System.out.print("Enter the quantity: ");
            try {
                quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity <= 0) {
                    System.out.println("Quantity must be a positive integer. Please try again.");
                    quantity = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Quantity must be a positive integer. Please try again.");
            }
        }
        System.out.println();
        return quantity;
    }

    @ShellMethod(key="order-beans",value="The ordering process will be initiated")
    public String OrderBeans() {

        int periodChosen = askFatalityPeriod();
        Map<Integer,Bean> chosenBeanSet = getChosenSetOfBeans(periodChosen);

        printBeans(chosenBeanSet);
        int purchaseIntent = askToProceed("Would you like to buy from this set?\n");

        if (purchaseIntent == 1) {
            processPurchaseIntent(chosenBeanSet);
        }
        else{
            return "\nWe have canceled the ordering process. Thank you for your consideration.\n";
        }

        System.out.println("\nWe will now take your address.");
        int orderProceedIntent = askToProceed("would you like to continue with your order?\n");

        if(orderProceedIntent==0) return "\nYou have ended the ordering process. Thank you for your consideration.\n";

        List<String> address = takeAddress();

        System.out.println("\nWe have noted you cart and address.\n");

        int orderProceedConfirmation = askToProceed("Would you like to confirm this order?");

        if(orderProceedConfirmation == 0) return "\nYou have ended the ordering process. Thank you for your consideration.\n";

        System.out.println("\nThank you! Your order has been placed. " +
                "\nAn agent will attend to it and contact you on your phone number.\n\n" +
                "you may also check the status of your order by typing 'check-orders'.\n");

        return "";
    }

    @ShellMethod(key="blah",value="The ordering process will be initiated")
    public String SeeBeans() {

        String beanJSON = Requests.getAllRecords("bean");
        System.out.println(beanJSON);




        return "";
    }




    private List<String> takeAddress() {
        System.out.println("\nWe will now take your address.");
        List<String> address = new ArrayList<>();

        String streetAddress = askForAddress("Street Address (e.g., 5 Smith Street): ");
        String suburb = askForAddress("Suburb (e.g., Sandton): ");
        String city = askForAddress("City: ");

        address.add(streetAddress);
        address.add(suburb);
        address.add(city);

        // Validate the address here if needed

        return address;
    }

    private String askForAddress(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private void processPurchaseIntent(Map<Integer, Bean> chosenBeanSet) {
        boolean firstPick = true;
        Map<String, OrderDetail> productDetails = new HashMap<>();

        if (firstPick) {
            System.out.println("We will now continuously ask you to pick a product until you type 'end'");
            firstPick = false;
        }

        System.out.println("Please type the index of the product you would like to purchase, or 'end' to finalize your cart:");
        Scanner scanner = new Scanner(System.in);
        String productChoice = scanner.nextLine().trim();

        while (!productChoice.equalsIgnoreCase("end")) {
            try {
                int productId = Integer.parseInt(productChoice);
                if (chosenBeanSet.containsKey(productId)) {
                    System.out.println("How many of this product would you like to purchase?");
                    int quantity = getQuantity(scanner);

                    Bean productInfo = chosenBeanSet.get(productId);
                    String productName = productInfo.getName();
                    BigDecimal productPrice = productInfo.getCurrentPrice(); // Extracting price without 'R'
                    BigDecimal totalCost = BigDecimal.valueOf( quantity ).multiply(productPrice);

                    // Create or update product details in the map
                    productDetails.compute(productName, (key, value) -> {
                        if (value == null) {
                            return new OrderDetail(quantity, totalCost);
                        } else {
                            int updatedQuantity = value.quantity + quantity;
                            BigDecimal updatedCost = value.totalCost.add(totalCost);
                            return new OrderDetail(updatedQuantity, updatedCost);
                        }
                    });
                } else {
                    System.out.println("Invalid product choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid product index or 'end'.");
            }
            System.out.println("Please type the index of the product you would like to purchase, or 'end' to finalize your cart:");
            productChoice = scanner.nextLine().trim();
        }

        // Display order details
        displayOrderDetails(productDetails);
    }

    private void displayOrderDetails(Map<String, OrderDetail> productDetails) {
        // Displaying order summary and calculating overall total cost
        System.out.println("\nOrder Summary:");
        BigDecimal overallTotalCost= BigDecimal.valueOf(0.0);
        for (Map.Entry<String, OrderDetail> entry : productDetails.entrySet()) {
            String productName = entry.getKey();
            OrderDetail detail = entry.getValue();
            overallTotalCost = overallTotalCost.add(detail.totalCost);
            System.out.println("Product: " + productName + ", Quantity: " + detail.quantity + ", Total Cost: R" +  detail.totalCost);
        }

        // Round off the overall total cost to two decimal places
        overallTotalCost = overallTotalCost.setScale(2, RoundingMode.HALF_UP);
        System.out.println("Overall Total Cost: R" + overallTotalCost);
    }


    private String askForInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }




    @ShellMethod(key="check-orders",value="we display any orders made")
    public String checkOrders(){



        System.out.println( "Here are your orders:\n");
        Map<Integer, ArrayList<String>> orderMap= getOrders();
        showOrders(orderMap);

        System.out.println("\nPlease type the index of the order for which you'd like to view your order details. ");
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
    @ShellMethodAvailability({"order-beans", "check-orders"})
    public Availability availabilityCheck() {
        return AccessCommands.connected
                ? Availability.available()
                : Availability.unavailable("you are not connected");
    }


// Helper class to store order detail (quantity and total cost)

private static class OrderDetail {
    int quantity;
    BigDecimal totalCost;

    public OrderDetail(int quantity, BigDecimal totalCost) {
        this.quantity = quantity;
        this.totalCost = totalCost;
    }


}

}
