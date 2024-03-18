package com.killerbean.shell.Helpers;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import static com.killerbean.shell.Helpers.Requests.redirectUser;

public class ApiRequestHandler {

    private final RestTemplate restTemplate;

    public ApiRequestHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String makeApiRequest(String apiUrl) throws URISyntaxException {
        String responseBody = "";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, User.SESSION_TOKEN);

        URI uri = new URI(apiUrl);
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
        ResponseEntity<String> response = ResponseEntity.ok().body("");

        try {
            response = restTemplate.exchange(requestEntity, String.class);
        }
        catch (Exception e) {
            return responseBody;
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();

        } else if (response.getStatusCode().value()== 302) {
            String responseHeader = response.getHeaders().getFirst("Location");
            System.out.println("\nFollow the link to log-in." + "\n" + "Location if you are not redirected :" + responseHeader);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Copy and paste the Token Here :");

            String input = scanner.nextLine();
            User.SESSION_TOKEN = input.split(":")[0];
            User.USER_ID = Integer.parseInt(input.split(":")[1]);
            User.IS_ADMIN = Boolean.parseBoolean(input.split(":")[2]);
        } else {
            System.err.println("\nRequest Failed with code : " + response.getStatusCode().value());
        }
        return responseBody;
    }

    public String makeApiPostRequest(String apiUrl, Object requestBody) throws URISyntaxException {
        String responseBody = "";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, User.SESSION_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        URI uri = new URI(apiUrl);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();

        } else if (response.getStatusCode().value() == 302) {
            String responseHeader = response.getHeaders().getFirst("Location");
            System.out.println("\nFollow the link to log-in." + "\n" + "Location :" + responseHeader);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Copy and paste the Token Here :");
            String input = scanner.nextLine();
            User.SESSION_TOKEN = input.split(":")[0];
            User.USER_ID = Integer.parseInt(input.split(":")[1]);
            User.IS_ADMIN = Boolean.parseBoolean(input.split(":")[2]);
        } else {
            System.err.println("\nRequest Failed with code : " + response.getStatusCodeValue());
        }

        return responseBody;
    }
    public String makeApiPutRequest(String apiUrl) throws URISyntaxException {
        String responseBody = "";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, User.SESSION_TOKEN);

        URI uri = new URI(apiUrl);
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.PUT, uri);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();

        } else if (response.getStatusCode().value() == 302) {
            String responseHeader = response.getHeaders().getFirst("Location");
            System.out.println("\nFollow the link to log-in." + "\n" + "Location :" + responseHeader);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Copy and paste the Token Here :");
            String input = scanner.nextLine();
            User.SESSION_TOKEN = input.split(":")[0];
            User.USER_ID = Integer.parseInt(input.split(":")[1]);
            User.IS_ADMIN = Boolean.parseBoolean(input.split(":")[2]);
        } else {
            System.err.println("\nRequest Failed with code : " + response.getStatusCode().value());
        }
        return responseBody;
    }
}

