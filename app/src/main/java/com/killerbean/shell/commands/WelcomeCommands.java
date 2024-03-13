package com.killerbean.shell.commands;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class WelcomeCommands {

//THIS IS JUST A TEST CLASS
    @ShellMethod(key = "hello",value="We will welcome you")
    public String welcome(@ShellOption(defaultValue = "Stranger") String name){
        return "Welcome to Killer Beans " + name;
    }
    @ShellMethod(key="goodbye",value="We will bid you goodbye")
    public String goodbye(){
        return "Goodbye";
    }

    @ShellMethodAvailability({"hello", "goodbye"})
    public Availability availabilityCheck() {
        return AccessCommands.connected
                ? Availability.available()
                : Availability.unavailable("you are not connected. Type sign-in to sign into the system");
    }
}
