package com.killerbean.shell.commands;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AccessCommands {

    public static boolean connected;

    @ShellMethod(key="sign-in ",value="You will be redirected to sign in with Github.")
    public String signin(){
        connected = true;
        return "signed in";
    }


}
