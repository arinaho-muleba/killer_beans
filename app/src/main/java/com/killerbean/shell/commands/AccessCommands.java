package com.killerbean.shell.commands;

import com.killerbean.shell.Helpers.Requests;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ShellComponent
public class AccessCommands {

    public static boolean connected;


    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    private String state; // State for CSRF protection

    @ShellMethod(key="log-in",value="Log in with github")
    public String authenticate() {
        state = "your-state"; // Generate a random state (optional, used for CSRF protection)

        String authorizationUrl = "https://github.com/login/oauth/authorize";
        String url = UriComponentsBuilder.fromHttpUrl(authorizationUrl)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", state)
                .build().toUriString();

        return "Please visit the following URL to authenticate with GitHub:\n" + url;
    }

    @ShellMethod(key="sign-in ",value="You will be redirected to sign in with Github.")
    public String signin(){
/*        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(Requests.SING_IN_URL, String.class);
        System.out.println("Sending request to : "+Requests.SING_IN_URL);
        System.out.println("RESPONSE : "+response);
        connected = true;

        try {
            // Open the browser with the authorization URL
            Desktop.getDesktop().browse(new URI(Requests.SING_IN_URL));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return "Failed to open the browser";
        }*/
        connected = true;
        return "You are signed in";
    }


}
