package com.killerbean.shell.commands;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;

@ShellComponent
public class HomeCommands {

    ArrayList<String> periods;


    @ShellMethod(key="order-beans",value="The ordering process will be initiatied")
    public String OrderBeans(/*@ShellOption(defaultValue = "Stranger") String name*/){

        periods = new ArrayList<>();

        periods.add("A. Any");
        periods.add("B. 1-3 days");
        periods.add("C. 4-6 days");
        periods.add("D. 7-10 days");

        System.out.println("Are you interested in any approximate time period in which you'd prefer out product to take fatal effect?");

        for (String period : periods) {
            System.out.println(period);
        }
        return "\nPick an option above by typing in a corresponding letter.";
    }


    @ShellMethod(key="check-orders",value="we display any orders made")
    public String checkOrders(){
        return "Here are your orders:\n";
    }

    @ShellMethodAvailability({"order-beans", "check-orders"})
    public Availability availabilityCheck() {
        return AccessCommands.connected
                ? Availability.available()
                : Availability.unavailable("you are not connected");
    }
}
