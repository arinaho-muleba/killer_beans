package com.killerbean.shell.Helpers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Requests {
    public static final String SING_IN_URL = "http://localhost:8080/api/v1";
    public static final String GET_BEANS_URL = "http://localhost:8080/api/v1/bean";
    public static String SESSION_TOKEN = "JSESSIONID=1E5C0A9AC724125EC749D4248A7CDE18";
    public static RestTemplate restTemplate;


    public static String getAllRecords(String entity) {
        restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/api/v1/"+entity;
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        if (response.getStatusCode().is2xxSuccessful())
            return response.getBody();
        else System.err.println("Failed to fetch data from API. Status code: " + response.getStatusCodeValue());

        return "";
    }

    public static String getRecordsWith1Parameter(String condition, String param1) {
        String apiUrl = "http://localhost:8080/api/v1/"+param1;
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        if (response.getStatusCode().is2xxSuccessful())
            return response.getBody();
        else System.err.println("Failed to fetch data from API. Status code: " + response.getStatusCodeValue());

        return "";
    }



    public static String getRecordsWith2Parameters(String condition, String param1, String param2) {
        String apiUrl = "http://localhost:8080/api/v1/"+condition+"?"+param1+"&"+param2;
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        if (response.getStatusCode().is2xxSuccessful())
            return response.getBody();
        else System.err.println("Failed to fetch data from API. Status code: " + response.getStatusCodeValue());

        return "";
    }
}
