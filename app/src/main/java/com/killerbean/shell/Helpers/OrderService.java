package com.killerbean.shell.Helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killerbean.shell.enums.Urls;
import com.killerbean.shell.model.Order;
import com.killerbean.shell.model.OrderLine;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final String ORDERS_URL = Urls.BASE_URL.getUrl()+"/orders/available";
    private final String MY_ORDERS_URL = Urls.BASE_URL.getUrl()+"/orders/byAgent/";
    private final String ORDER_LINE_URL = Urls.BASE_URL.getUrl()+"/orderLines/byOrder/";
    private final RestTemplate restTemplate = new RestTemplate();

    public OrderService() {
    }

    public List<Order> fetchOrders() {
        ApiRequestHandler apiRequestHandler = new ApiRequestHandler(this.restTemplate);
        String json ="";
        try {
            json = apiRequestHandler.makeApiRequest(ORDERS_URL);

        }catch (URISyntaxException e){

            return new ArrayList<>();
        }

        try {
            System.out.println(json);
            ObjectMapper objectMapper = new ObjectMapper();
            Order[] orders = objectMapper.readValue(json, Order[].class);

            return List.of(orders);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of();
    }
    public List<Order> fetchOrdersAssignedToMe() {
        ApiRequestHandler apiRequestHandler = new ApiRequestHandler(this.restTemplate);
        String json ="";
        try {
            json = apiRequestHandler.makeApiRequest(MY_ORDERS_URL+String.valueOf(User.USER_ID));

        }catch (URISyntaxException e){

            return new ArrayList<>();
        }

        try {
            System.out.println(json);
            ObjectMapper objectMapper = new ObjectMapper();
            Order[] orders = objectMapper.readValue(json, Order[].class);

            return List.of(orders);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of();
    }
    public List<OrderLine> getOrderLine(Long id){
        ApiRequestHandler apiRequestHandler = new ApiRequestHandler(this.restTemplate);
        String json ="";
        try {
            json = apiRequestHandler.makeApiRequest(ORDER_LINE_URL+String.valueOf(id));

        }catch (URISyntaxException e){
            return new ArrayList<>();
        }
        return OrderLineProcessor.fromJson(json);
    }



}