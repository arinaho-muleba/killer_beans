package com.killerbean.shell.Helpers;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

public class Requests {
    public static final String SING_IN_URL = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/";
    public static final String GET_ENTITY_URL = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/api/v1/";
    public static final String USER_SIGN_UP_URL = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/signup";
    public static String SESSION_TOKEN = "";
    public static RestTemplate restTemplate;


    public static String getEntity(String path) throws URISyntaxException {
        restTemplate = new RestTemplate();
        ApiRequestHandler handler = new ApiRequestHandler(restTemplate);
        String apiUrl = GET_ENTITY_URL+path;
        return handler.makeApiRequest(apiUrl);
    }

    public static void  postEntity(String path, JSONObject entityDetails) throws URISyntaxException {
        restTemplate = new RestTemplate();
        ApiRequestHandler handler = new ApiRequestHandler(restTemplate);
        String apiUrl = GET_ENTITY_URL+path;
        handler.makeApiPostRequest(apiUrl, entityDetails);
    }



    public static void redirectUser(){
        try {
            String url = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/login";

            String command = "";
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("win")) {
                command = "cmd.exe /c start " + url;
            } else if (osName.contains("mac")) {
                command = "open " + url;
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
                command = "xdg-open " + url;
            }

            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
