package com.killerbean.shell.commands;

import com.killerbean.shell.Helpers.User;
import com.killerbean.shell.model.Address;
import com.killerbean.shell.model.OrderClientView;
import com.killerbean.shell.model.OrderLine;
import org.json.JSONArray;
import org.json.JSONObject;

import com.killerbean.shell.model.Bean;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.net.URISyntaxException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.killerbean.shell.Helpers.BeanProcessor.printBeans;
import static com.killerbean.shell.Helpers.BeanProcessor.processBeanJSON;
import static com.killerbean.shell.Helpers.OrderLineProcessor.printOrderLines;
import static com.killerbean.shell.Helpers.OrderLineProcessor.processOrderLineJSON;
import static com.killerbean.shell.Helpers.OrderProcessor.printOrders;
import static com.killerbean.shell.Helpers.OrderProcessor.processOrderJSON;
import static com.killerbean.shell.Helpers.Requests.*;

@ShellComponent
public class HomeCommands {

    public static final String PROMPT_ENTER_QUANTITY = "Enter the quantity: ";
    private static final String PROMPT_QUANTITY_CHOICE = "Please indicate the quantity of this product you wish to purchase:";
    private static final String PROMPT_PRODUCT_CHOICE= "Please input the product index you wish to purchase, or type 'end' to complete your cart:";
    public static final String NOTICE_NO_STOCK = "\u001B[33m" + "At the moment, there are no items available in this time period collection. However, we plan to restock shortly." + "\u001B[0m\n";
    public static final String NOTICE_NO_ORDERS= "\u001B[38;5;208m" + "You have not placed any orders." + "\u001B[0m\n";
    private static final String PROMPT_CONFIRM_ORDER = "\u001B[34m\nWould you like to confirm this order?\u001B[0m";
    private static final String PROMPT_ADDRESS_STREET = "Street Address (e.g., 5 Smith Street): ";
    private static final String PROMPT_ADDRESS_SUBURB = "Suburb (e.g., Sandton): ";
    private static final String PROMPT_ADDRESS_CITY = "City: ";
    private static final String PROMPT_ORDER_REFERENCE ="\n\u001B[36mPlease provide the order reference number to access the details of your order: \u001B[0m";
    private static final String QUANTITY_EXCEEDS_STOCK = "\u001B[38;5;208m\nError: Requested Quantity Exceeds Available Stock\u001B[0m\n";
    private static final String ASK_PRODUCT_SET = "\u001B[34m\nDo you have a preference for the estimated time frame in which you'd like our product to take effect?\u001B[0m";
    private static final String ORDER_PLACED_MESSAGE = "\u001B[32m\nThank you! Your order has been placed. \u001B[0m" +
            "\u001B[35m\n\nAn agent will attend to it and contact you on your phone number.\n\u001B[0m" +
            "\u001B[36mYou may also check the status of your order by typing 'check-orders'.\n\u001B[0m";
    public static final String ORDER_CANCELLED_MESSAGE = "\u001B[38;5;208mYou have cancelled the ordering process. Thank you for your consideration.\u001B[0m";


    private static final Map<Integer, String> CONFIRMATION = new HashMap<>();
    public static final String ERROR_INVALID_NUMBER = "Quantity must be a positive integer. Please try again.";

    static {
        CONFIRMATION.put(1, "\u001B[32mYes\u001B[0m");
        CONFIRMATION.put(2, "\u001B[31mNo\u001B[0m");

    }

    private static final Map<Integer, String> OPTIONS = new HashMap<>();
    static {
        OPTIONS.put(1, "Any");
        OPTIONS.put(2, "1-3 days");
        OPTIONS.put(3, "4-6 days");
        OPTIONS.put(4, "7-9 days");
        OPTIONS.put(5, "10+ days");
        OPTIONS.put(6, "Cancel\n");
    }


    public static int askToProceed(String question){

        System.out.println(question);
        CONFIRMATION.forEach((index, period) -> System.out.println(index + ". " + period));
        Scanner scanner = new Scanner(System.in);
        return readChoice(CONFIRMATION,scanner);
    }

    public static int askFatalityPeriod(){
        System.out.println(ASK_PRODUCT_SET);
        System.out.println("Please select one of the following options:\n");
        OPTIONS.forEach((index, period) -> System.out.println(index + ". " + period));

        Scanner scanner = new Scanner(System.in);
        return readChoice(OPTIONS,scanner);
    }

    private static Map<Integer, Bean> getChosenSetOfBeans(int choice) throws URISyntaxException {
        return switch (choice) {
            case 1 -> getBeanOptions();
            case 2 -> getBeanOptions("1", "3");
            case 3 -> getBeanOptions("4", "6");
            case 4 -> getBeanOptions("7", "9");
            case 5 -> getBeanOptions("10");
            default -> throw new IllegalArgumentException("You have chosen an invalid option: " + choice);
        };

    }


    public static int readChoice(Map<Integer, String> optionMap, Scanner scanner) {
        System.out.print("Enter the number for your choice: ");
        int choice = scanner.nextInt();
        while (!optionMap.containsKey(choice)) {
            System.out.println("Invalid choice. Please try again.\n");
            System.out.print("\nEnter the number of your choice: ");
            choice = scanner.nextInt();
        }
        System.out.println();
        return choice;
    }


    public static  Map<Integer, Bean>  getBeanOptions() throws URISyntaxException {

        String beanJSON=getEntity("bean");
        if(beanJSON.isEmpty())return new HashMap<>();
        return processBeanJSON(beanJSON);
    }


    public static  Map<Integer, Bean>  getBeanOptions(String minDays, String maxDays) throws URISyntaxException {

        String beanJSON= getEntity("bean/getByTimeToKillRange?minTimeToKill=" + minDays +"&maxTimeToKill="+ maxDays);
        if(beanJSON.isEmpty())return new HashMap<>();
        return processBeanJSON(beanJSON);
    }

    public static Map<Integer, Bean> getBeanOptions(String minDays) throws URISyntaxException {

            String beanJSON = getEntity("bean/timeToKill/" + minDays);
            if(beanJSON.isEmpty())return new HashMap<>();
            return processBeanJSON(beanJSON);
    }


    public int getQuantity(Scanner scanner, int stock, int accumulatedQuantity) {
        int quantity = 0;

        while (quantity == 0) {
            System.out.print(PROMPT_ENTER_QUANTITY);
            try {
                quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity < 0) {
                    System.out.println(ERROR_INVALID_NUMBER);
                    quantity = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input" + ERROR_INVALID_NUMBER);
            }

            if(( quantity + accumulatedQuantity ) > stock){
                System.out.println(QUANTITY_EXCEEDS_STOCK);
                return 0;
            }
        }
        System.out.println();
        return quantity;
    }

    public void addOrder(Address address, Map<Long, OrderDetail> orderDetailsMap, Map<Integer, Bean> beanMap ) throws URISyntaxException {

        int userID = User.USER_ID;
        JSONObject orderJSON = new JSONObject();
        JSONArray orderLineJSON = new JSONArray();

        orderDetailsMap.forEach((key, value) -> {
            JSONObject beanJSON = new JSONObject();
            beanJSON.put("itemId",beanMap.get(Math.toIntExact(key)).getId());
            beanJSON.put("quantity",value.getQuantity());
            orderLineJSON.put(beanJSON);
        });

        JSONObject addressJSON = new JSONObject(address);
        orderJSON.put("customerId", userID);
        orderJSON.put("address", addressJSON);
        orderJSON.put("minOrderLines", orderLineJSON);


        System.out.println(orderJSON.toString());
        postEntity("orders/create",orderJSON);
    }

    @ShellMethod(key="order-beans",value="The ordering process will be initiated")
    public String OrderBeans() throws URISyntaxException {
        int periodChosen = askFatalityPeriod();
        Map<Integer, Bean> chosenBeanSet = getChosenSetOfBeans(periodChosen);

        if(chosenBeanSet.isEmpty()) return NOTICE_NO_STOCK;

        printBeans(chosenBeanSet);
        int purchaseIntent = askToProceed("\n\u001B[34mWould you like to buy from this set?\u001B[0m\n");

        if (purchaseIntent == 2)  return ORDER_CANCELLED_MESSAGE;

        Map<Long, OrderDetail> orderDetails = processPurchaseIntent(chosenBeanSet);

        System.out.println("\nWe will now take your address.");
        int orderProceedIntent = askToProceed("\u001B[34mWould you like to continue with your order?\u001B[0m");

        if(orderProceedIntent == 2) return ORDER_CANCELLED_MESSAGE;

        Address address = takeAddress();

        System.out.println("\nWe have noted your cart and address.\n");

        int orderProceedConfirmation = askToProceed(PROMPT_CONFIRM_ORDER);

        if(orderProceedConfirmation == 2) return ORDER_CANCELLED_MESSAGE;

        addOrder(address, orderDetails, chosenBeanSet);

        return ORDER_PLACED_MESSAGE;
    }

    public Address takeAddress() {

        Scanner scanner = new Scanner(System.in);
        String streetAddress = askForAddress(scanner, PROMPT_ADDRESS_STREET);
        String suburb = askForAddress(scanner, PROMPT_ADDRESS_SUBURB);
        String city = askForAddress(scanner, PROMPT_ADDRESS_CITY);

        return new Address(streetAddress, suburb, city);
    }

    private String askForAddress(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\u001B[38;5;208m Address component cannot be empty. Please try again.\u001B[0m");
            }
        } while (input.isEmpty());
        return input;
    }

    private Map<Long, OrderDetail> processPurchaseIntent(Map<Integer, Bean> chosenBeanSet) {

        Map<Long, OrderDetail> productDetails = new HashMap<>();

        System.out.println("\nWe'll continue prompting you to select a product until you enter 'end'.");

        System.out.println( PROMPT_PRODUCT_CHOICE);
        Scanner scanner = new Scanner(System.in);
        String productChoice = scanner.nextLine().trim();

        while (!productChoice.equalsIgnoreCase("end")) {
            try {
                long productId = Integer.parseInt(productChoice);
                if (chosenBeanSet.containsKey(Math.toIntExact(productId))) {

                    System.out.println(PROMPT_QUANTITY_CHOICE);

                    Bean productInfo = chosenBeanSet.get(Math.toIntExact(productId));

                    long productID = productInfo.getId();
                    String productName = productInfo.getName();
                    int quantity=0;

                    if( productDetails.get(productId) == null)
                         quantity = getQuantity(scanner, productInfo.getQuantity(),0);
                    else
                         quantity = getQuantity(scanner, productInfo.getQuantity(), productDetails.get(productId).getQuantity());

                    BigDecimal productPrice = productInfo.getCurrentPrice();
                    BigDecimal totalCost = BigDecimal.valueOf( quantity ).multiply(productPrice);

                    int finalQuantity = quantity;
                    productDetails.compute(productId, (key, value) -> {
                        if (value == null) {
                            return new OrderDetail(productName, finalQuantity, totalCost);
                        } else {
                            int updatedQuantity = value.quantity + finalQuantity;
                            BigDecimal updatedCost = value.totalCost.add(totalCost);
                            return new OrderDetail(productName, updatedQuantity, updatedCost);
                        }
                    });
                } else {
                    System.out.println("\u001B[38mInvalid product choice. Please try again.\u001B[0m");
                }
            } catch (NumberFormatException e) {
                System.out.println("\u001B[38mInvalid input. Please enter a valid product ID or 'end'.\u001B[0m");
            }
            System.out.println("Please type the index of the product you would like to purchase, or 'end' to finalize your cart:");
            productChoice = scanner.nextLine().trim();
        }

        displayOrderDetails(productDetails);

        return productDetails;
    }

    public void displayOrderDetails(Map<Long, OrderDetail> productDetails) {


        System.out.println("\u001B[38;5;208mOrder Summary:\u001B[0m\n");
        System.out.println("\u001B[36mProduct          Quantity            Total Cost\u001B[0m\n");
        BigDecimal overallTotalCost= BigDecimal.valueOf(0.0);
        for (Map.Entry<Long, OrderDetail> entry : productDetails.entrySet()) {
            long productID= entry.getKey();
            OrderDetail detail = entry.getValue();
            overallTotalCost = overallTotalCost.add(detail.totalCost);

            System.out.printf("\u001B[32m%-20s%-20dR%-10.1f\n\u001B[0m%n", detail.productName, detail.quantity, detail.totalCost);
        }

        overallTotalCost = overallTotalCost.setScale(2, RoundingMode.HALF_UP);
        System.out.println("\u001B[33mOverall Total Cost: R" + overallTotalCost + "\u001B[0m");
    }

    @ShellMethod(key="check-orders",value="we display any orders made")
    public String checkOrders() throws URISyntaxException {

        int userID = User.USER_ID;

        String orderJSON = getEntity("orders/byUser/1");
        if(orderJSON.equals("[]")) return NOTICE_NO_ORDERS ;

        System.out.println( "Here are your orders:\n");
        Map<Integer, OrderClientView> orderMap= processOrderJSON(orderJSON);
        printOrders(orderMap);

        System.out.println(PROMPT_ORDER_REFERENCE);
        Scanner scanner = new Scanner(System.in);
        int orderChoice = scanner.nextInt();
        if(orderMap.containsKey(orderChoice)){

            String orderlineJSON = getEntity("orderLines/byOrder/"+ Integer.toString(orderChoice));
            Map<Integer, OrderLine> orderLineMap= processOrderLineJSON(orderlineJSON);
            System.out.println();
            printOrderLines(orderLineMap);
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
                : Availability.unavailable("\u001B[38;5;208m you are not signed in. Please use the 'sign-in' command to gain access.\u001B[0m");
    }

 public static class OrderDetail {


        private  String productName;
        private int quantity;
        private BigDecimal totalCost;


        public OrderDetail(String productName, int quantity, BigDecimal totalCost) {
            this.productName = productName;
            this.quantity = quantity;
            this.totalCost = totalCost;
        }

     public String getProductName() {
         return productName;
     }

     public int getQuantity() {
         return quantity;
     }

     public String toString() {
         return "\n"+productName+" "+ quantity +" "+totalCost;
     }
 }
}
