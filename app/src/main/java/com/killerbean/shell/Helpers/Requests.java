package com.killerbean.shell.Helpers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

public class Requests {
    public static final String SING_IN_URL = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/";
    public static final String GET_BEANS_URL = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/api/v1/beans";
    public static final String USER_SIGN_UP_URL = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/signup";
    public static String SESSION_TOKEN = "";
    public static RestTemplate restTemplate;


    public static String getAllRecords(String entity) throws URISyntaxException {
        restTemplate = new RestTemplate();
        ApiRequestHandler handler = new ApiRequestHandler(restTemplate);

        String apiUrl = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/api/v1/"+entity;

        return handler.makeApiRequest(apiUrl);
    }

    public static String getRecordsWith1Parameter(String condition, String param1) throws URISyntaxException {

        restTemplate = new RestTemplate();
        ApiRequestHandler handler = new ApiRequestHandler(restTemplate);

        String apiUrl = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/api/v1/"+condition+"/"+param1;

        return  handler.makeApiRequest(apiUrl);
    }



    public static String getRecordsWith2Parameters(String condition, String param1, String param2) throws URISyntaxException {

        restTemplate = new RestTemplate();
        ApiRequestHandler handler = new ApiRequestHandler(restTemplate);
        String apiUrl = "http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/api/v1/"+condition+"?"+param1+"&"+param2;

        return handler.makeApiRequest(apiUrl);
    }
}
