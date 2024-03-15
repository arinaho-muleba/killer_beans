package com.killerbean.shell.commands;

import com.killerbean.shell.Helpers.ApiRequestHandler;
import com.killerbean.shell.Helpers.Requests;
import com.killerbean.shell.Helpers.User;
import com.killerbean.shell.model.OrderClientView;
import com.killerbean.shell.model.OrderLine;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import com.killerbean.shell.Helpers.Requests;
import com.killerbean.shell.model.Bean;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

import static com.killerbean.shell.Helpers.BeanProcessor.printBeans;
import static com.killerbean.shell.Helpers.BeanProcessor.processBeanJSON;
import static com.killerbean.shell.Helpers.OrderLineProcessor.printOrderLines;
import static com.killerbean.shell.Helpers.OrderLineProcessor.processOrderLineJSON;
import static com.killerbean.shell.Helpers.OrderProcessor.printOrders;
import static com.killerbean.shell.Helpers.OrderProcessor.processOrderJSON;
import static com.killerbean.shell.Helpers.Requests.*;

@ShellComponent
public class HomeCommands {
    @Autowired
    private HttpServletRequest request;
    private RestTemplate restTemplate;

    public HomeCommands() {
        this.restTemplate = new RestTemplate();
    }
    private static final Map<Integer, String> CONFIRMATION = new HashMap<>();
    static {
        CONFIRMATION.put(1, "Yes");
        CONFIRMATION.put(0, "No");

    }

    private static final Map<Integer, String> OPTIONS = new HashMap<>();
    static {
        OPTIONS.put(1, "Any");
        OPTIONS.put(2, "1-3 days");
        OPTIONS.put(3, "4-6 days");
        OPTIONS.put(4, "7-10 days");
        OPTIONS.put(5, "11+ days");
        OPTIONS.put(6, "Cancel\n");
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

    private static Map<Integer, Bean> getChosenSetOfBeans(int choice) throws URISyntaxException {
        switch (choice) {
            case 1:
                return getBeanOptions(); // Or adjust the range as needed
            case 2:
                return getBeanOptions("1", "3");
            case 3:
                return getBeanOptions("4", "6");
            case 4:
                return getBeanOptions("7", "9");
            case 5:
                return getBeanOptions("10");
            default:
                throw new IllegalArgumentException("Invalid choice: " + choice);
        }

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


    public static  Map<Integer, Bean>  getBeanOptions() throws URISyntaxException {
        String beanJSON= getAllRecords("bean");
        return processBeanJSON(beanJSON);
    }


    public static  Map<Integer, Bean>  getBeanOptions(String minDays, String maxDays) throws URISyntaxException {

        String beanJSON= getRecordsWith2Parameters("bean/getByTimeToKillRange","minTimeToKill=" +minDays,"maxTimeToKill="+maxDays);
        return processBeanJSON(beanJSON);
    }

    public static Map<Integer, Bean> getBeanOptions(String minDays) throws URISyntaxException {
        String beanJSON= getRecordsWith1Parameter("bean/timeToKill",minDays);
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
    public String OrderBeans() throws URISyntaxException {

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


    private List<String> takeAddress() {
        System.out.println("\nWe will now take your address.");
        List<String> address = new ArrayList<>();

        String streetAddress = askForAddress("Street Address (e.g., 5 Smith Street): ");
        String suburb = askForAddress("Suburb (e.g., Sandton): ");
        String city = askForAddress("City: ");

        address.add(streetAddress);
        address.add(suburb);
        address.add(city);

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


        System.out.println("We will now continuously ask you to pick a product until you type 'end'");

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

        overallTotalCost = overallTotalCost.setScale(2, RoundingMode.HALF_UP);
        System.out.println("Overall Total Cost: R" + overallTotalCost);
    }

    @ShellMethod(key="check-orders",value="we display any orders made")
    public String checkOrders() throws URISyntaxException {

        restTemplate = new RestTemplate();
        ApiRequestHandler handler = new ApiRequestHandler(restTemplate);

        String url= "http://localhost:8080/api/v1/orders/byUser/1";

        String response = handler.makeApiRequest(url);


        System.out.println( "Here are your orders:\n");
        Map<Integer, OrderClientView> orderMap= processOrderJSON(response);
        printOrders(orderMap);

        System.out.println("\nPlease type the reference of the order for which you'd like to view your order details. ");
        Scanner scanner = new Scanner(System.in);
        int orderChoice = scanner.nextInt();
        if(orderMap.containsKey(orderChoice)){

            RestTemplate restTemplate = new RestTemplate();
            ApiRequestHandler handler2 = new ApiRequestHandler(restTemplate);

            String url2= "http://localhost:8080/api/v1/orderLines/byOrder/" + Integer.toString(orderChoice);
            String response2 = handler2.makeApiRequest(url2);
            Map<Integer, OrderLine> orderLineMap= processOrderLineJSON(response2);
            System.out.println();
            printOrderLines(orderLineMap);

        }
        else {
            System.out.println("Invalid order choice");
        }
        return "\n";
    }

    @ShellMethod(key="Sign-in",value="we will log you in")
    public String testRequest() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        ApiRequestHandler apiRequestHandler = new ApiRequestHandler(restTemplate);

        try {
            System.out.println(apiRequestHandler.makeApiRequest(Requests.SING_IN_URL));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "\n";
    }
    @ShellMethodAvailability({"order-beans", "check-orders"})
    public Availability availabilityCheck() {
        return AccessCommands.connected
                ? Availability.available()
                : Availability.unavailable("you are not connected");
    }

    private static class OrderDetail {
        int quantity;
        BigDecimal totalCost;

        public OrderDetail(int quantity, BigDecimal totalCost) {
            this.quantity = quantity;
            this.totalCost = totalCost;
        }
    }
}
