package com.killerbean.shell.commands;

import com.killerbean.shell.Helpers.Requests;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;

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
        BEANS.put(1, new ArrayList<>(List.of("Deadly Nightshade Bean", "R5.99", "100", "2")));
        BEANS.put(2, new ArrayList<>(List.of("Hemlock Bean", "R8.99", "50", "5")));
        BEANS.put(3, new ArrayList<>(List.of("Castor Bean", "R12.99", "30", "3")));
        BEANS.put(4, new ArrayList<>(List.of("Aconite Bean", "R15.99", "20", "4")));
        BEANS.put(5, new ArrayList<>(List.of("Rosary Pea Bean", "R19.99", "10", "7")));
        BEANS.put(6, new ArrayList<>(List.of("Jimsonweed Bean", "R25.99", "5", "10")));
        BEANS.put(7, new ArrayList<>(List.of("Monkshood Bean", "R18.50", "8", "6")));
        BEANS.put(8, new ArrayList<>(List.of("Belladonna Bean", "R22.50", "15", "8")));
    }


    private static final Map<Integer, Function<Integer, Map<Integer, ArrayList<String>>>> RANGE_MAP = new HashMap<>();

    static {
        RANGE_MAP.put(1, choice -> getBeanOptions());
        RANGE_MAP.put(2, choice -> getBeanOptions(1, 3));
        RANGE_MAP.put(3, choice -> getBeanOptions(4, 6));
        RANGE_MAP.put(4, choice -> getBeanOptions(7, 10));
        RANGE_MAP.put(5, choice -> getBeanOptions(11));
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

    private static int askPurchaseIntent(){
        System.out.println("Would you like to buy from this set?\n");
        CONFIRMATION.forEach((index, period) -> System.out.println(index + ". " + period));

        Scanner scanner = new Scanner(System.in);
        int  choice = readChoice(CONFIRMATION,scanner);

        return choice;
    }

    private static int askFatalityPeriod(){
        System.out.println("\nAre you interested in any estimated time period in which you'd like our product to take fatal effect?");
        System.out.println("Please select one of the following options:\n");
        OPTIONS.forEach((index, period) -> System.out.println(index + ". " + period));

        Scanner scanner = new Scanner(System.in);
        int choice = readChoice(OPTIONS,scanner);
        return choice;
    }

    private static Map<Integer, ArrayList<String>>getChosenSetOfBeans(int choice){
        return RANGE_MAP.getOrDefault(choice, HomeCommands::getBeanOptions).apply(choice);
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


    public static  Map<Integer, ArrayList<String>>  getBeanOptions() {

        return BEANS;
    }

    public static  Map<Integer, ArrayList<String>>  getBeanOptions(int minDays, int maxDays) {
        Map<Integer, ArrayList<String>> beanOptions = new HashMap<>();
        int beanIndex = 1;

        for (Map.Entry<Integer, ArrayList<String>> entry : BEANS.entrySet()) {

            int beanId = entry.getKey();
            ArrayList<String> beanInfo = entry.getValue();
            String timeToKill = beanInfo.get(3);
            int days = Integer.parseInt(timeToKill);
            if (days >= minDays && days <= maxDays) {

                beanOptions.put(beanIndex,entry.getValue());
                beanIndex++;
            }
        }

        return beanOptions;
    }

    public static  Map<Integer, ArrayList<String>>  getBeanOptions(int minDays) {
        Map<Integer, ArrayList<String>> beanOptions = new HashMap<>();
        int beanIndex = 1;
        for (Map.Entry<Integer, ArrayList<String>> entry : BEANS.entrySet()) {

            int beanId = entry.getKey();
            ArrayList<String> beanInfo = entry.getValue();
            String timeToKill = beanInfo.get(3);
            int days = Integer.parseInt(timeToKill);
            if (days >= minDays) {

                beanOptions.put(beanIndex,entry.getValue());
                beanIndex++;
            }
        }
        return beanOptions;
    }
    public static void showBeanOptions( Map<Integer, ArrayList<String>> beanOptions) {

        for (Map.Entry<Integer, ArrayList<String>> entry : beanOptions.entrySet()) {
            int beanId = entry.getKey();
            ArrayList<String> beanInfo = entry.getValue();
            printBeanChoices(beanId, beanInfo);
        }
    }

    private static void printBeanChoices(int beanId, ArrayList<String> beanInfo){
        System.out.println( beanId + "." + beanInfo );
    }
    private static void printNoStock(){
      System.out.println("No beans currently in stock within this effective period.");
    }

    private static void showOrders() {

        for (Map.Entry<Integer, ArrayList<String>> entry : ORDERS.entrySet()) {
            int orderId = entry.getKey();
            ArrayList<String> orderDetails = entry.getValue();
            System.out.println("Order ID: " + orderId + ", Order Details: " + orderDetails);
        }
    }


    int getQuantity(){
        //create a scanner
        //declare an int
        int q =0;
        while(q==0){
            //keep asking for input
            //if invalid? continue : set q and break;
        }
        return q;
    }


    @ShellMethod(key="order-beans",value="The ordering process will be initiatied")
    public String OrderBeans(){

        int periodChosen= askFatalityPeriod();
        Map<Integer, ArrayList<String>> chosenBeanSet = getChosenSetOfBeans(periodChosen);
        showBeanOptions(chosenBeanSet);
        int purchaseIntent = askPurchaseIntent();
        boolean firstPick= true;
        if(purchaseIntent==1){
            if(firstPick){
                System.out.println("We will now continuously ask you to pick a product until you type 'end' ");
                firstPick=false;
            }
            System.out.println("please type the index of the product you would like to purchase?\nType 'end' if you would like to finalise your cart");

            Scanner scanner = new Scanner(System.in);
            String productChoice = scanner.nextLine();

            if(productChoice == "end"){
                return "We see you have not picked any product, thank you for your consideration.\n";
            }

//            while (!chosenBeanSet.containsKey(productChoice)  ) {
//                System.out.println("Invalid choice. Please try again.\n");
//                System.out.print("Enter the product number of your choice: ");
//                productChoice = scanner.nextLine();
//                if(productChoice == "end") {
//                    System.out.println("Here is your Cart:\n\n");
//
//                    break;
//                }

//                System.out.print("Please enter the quantity you would like to buy of this product: ");
//
//              string quantity = scanner.nextLine();
//              System.out.print();
//
//
//
//
//            }
//
//
//        }
//        else return "\n";
//
//
}

        return "";
    }

    @ShellMethod(key="check-orders",value="we display any orders made")
    public String checkOrders(){
        System.out.println( "Here are your orders:\n");
        showOrders();
        return "\n";
    }

    @ShellMethod(key="send",value="we display any orders made")
    public String testRequest() throws URISyntaxException {
        String apiUrl = "http://localhost:3000/api/v1/bean/test";
        //ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        // Create HttpHeaders and add the Cookie header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, Requests.SESSION_TOKEN);
        headers.add(HttpHeaders.COOKIE, Requests.SESSION_TOKEN);
        headers.add(HttpHeaders.AUTHORIZATION, Requests.SESSION_TOKEN);

        // Create a RequestEntity with the headers and HttpMethod.GET
        URI uri = new URI(apiUrl);
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
        System.out.println(requestEntity.getHeaders());
        // Send the request and retrieve the response
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            // Process the response
            System.out.println("API response: " + responseBody);
        } else {
            System.err.println("Failed to fetch data from API. Status code: " + response.getStatusCodeValue());
        }
        return "\n";
    }
    @ShellMethodAvailability({"order-beans", "check-orders"})
    public Availability availabilityCheck() {
        return AccessCommands.connected
                ? Availability.available()
                : Availability.unavailable("you are not connected");
    }
}
