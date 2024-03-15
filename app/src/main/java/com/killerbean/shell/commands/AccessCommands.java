package com.killerbean.shell.commands;

import com.killerbean.shell.Helpers.ApiRequestHandler;
import com.killerbean.shell.Helpers.Requests;
import com.killerbean.shell.model.SignUpModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;


import java.net.URISyntaxException;
import java.util.Scanner;

@ShellComponent
public class AccessCommands {

    public static boolean connected = true;


    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    private String state; // State for CSRF protection

    @ShellMethod(key="log-in",value="Log in with github")
    public String authenticate() {
/*        state = "your-state"; // Generate a random state (optional, used for CSRF protection)

        String authorizationUrl = "https://github.com/login/oauth/authorize";
        String url = UriComponentsBuilder.fromHttpUrl(authorizationUrl)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", state)
                .build().toUriString();

        return "Please visit the following URL to authenticate with GitHub:\n" + url;*/
        RestTemplate restTemplate = new RestTemplate();
        ApiRequestHandler apiRequestHandler = new ApiRequestHandler(restTemplate);

        try {
            // Call the makeApiRequest method to make the API request
            System.out.println(apiRequestHandler.makeApiRequest(Requests.SING_IN_URL));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "\n";
    }

    @ShellMethod(key="sign-up",value="You will create an account")
    public String signup(){
        Scanner input = new Scanner(System.in);
        System.out.println("Eneter phone number");
        SignUpModel signupModel = new SignUpModel();
        signupModel.setPhoneNumber(input.nextLine());

        return "You are signed in";
    }


}
