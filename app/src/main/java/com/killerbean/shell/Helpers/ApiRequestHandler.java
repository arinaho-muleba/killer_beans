package com.killerbean.shell.Helpers;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

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

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            responseBody = response.getBody();

            // Process the response
            //System.out.println("API response: " + responseBody);
        } else if (response.getStatusCodeValue() == 302) {
            String responseHeader = response.getHeaders().getFirst("Location");
            System.out.println("\nFollow the link to log-in." + "\n" + "Location :" + responseHeader);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Copy and paste the Token Here :");
            String input = scanner.nextLine();
            User.SESSION_TOKEN = input.split(":")[0];
            User.USER_ID = Integer.parseInt(input.split(":")[1]);
            User.IS_ADMIN = Integer.parseInt(input.split(":")[2]);
        } else {
            System.err.println("\nRequest Failed with code : " + response.getStatusCodeValue());
        }
        return responseBody;
    }
}
