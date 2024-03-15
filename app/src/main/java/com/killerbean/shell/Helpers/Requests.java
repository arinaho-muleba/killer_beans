package com.killerbean.shell.Helpers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

public class Requests {
    public static final String SING_IN_URL = "http://localhost:8080/api/v1";
    public static final String GET_BEANS_URL = "http://localhost:8080/api/v1/bean";
    public static String SESSION_TOKEN = "JSESSIONID=1E5C0A9AC724125EC749D4248A7CDE18";
    public static RestTemplate restTemplate;


    public static String getAllRecords(String entity) throws URISyntaxException {
        restTemplate = new RestTemplate();
        ApiRequestHandler handler = new ApiRequestHandler(restTemplate);

        String apiUrl = "http://localhost:8080/api/v1/"+entity;

        return handler.makeApiRequest(apiUrl);
    }

    public static String getRecordsWith1Parameter(String condition, String param1) throws URISyntaxException {

        restTemplate = new RestTemplate();
        ApiRequestHandler handler = new ApiRequestHandler(restTemplate);

        String apiUrl = "http://localhost:8080/api/v1/"+condition+"/"+param1;

        return  handler.makeApiRequest(apiUrl);
    }



    public static String getRecordsWith2Parameters(String condition, String param1, String param2) throws URISyntaxException {

        restTemplate = new RestTemplate();
        ApiRequestHandler handler = new ApiRequestHandler(restTemplate);
        String apiUrl = "http://localhost:8080/api/v1/"+condition+"?"+param1+"&"+param2;

        return handler.makeApiRequest(apiUrl);
    }
}
