package com.killerbean.shell.commands;

import com.killerbean.shell.Helpers.ApiRequestHandler;
import com.killerbean.shell.Helpers.Requests;
import com.killerbean.shell.Helpers.User;
import com.killerbean.shell.model.SignUpModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;
import java.net.URISyntaxException;
import java.util.Scanner;
import static com.killerbean.shell.Helpers.Requests.redirectUser;

@ShellComponent
public class AccessCommands {

    public static boolean connected = false;
    public static boolean role;
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    private String state;

    @ShellMethod(key="sign-in",value="Log in with github")
    public String authenticate() {

        redirectUser();
        RestTemplate restTemplate = new RestTemplate();
        ApiRequestHandler apiRequestHandler = new ApiRequestHandler(restTemplate);
        try {
            System.out.println(apiRequestHandler.makeApiRequest(Requests.SING_IN_URL));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        role = User.IS_ADMIN;
        connected=true;
        return "\u001B[32mYou have successfully signed in\n\u001B[0m";
    }



}
