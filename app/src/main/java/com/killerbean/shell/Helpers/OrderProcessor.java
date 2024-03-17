package com.killerbean.shell.Helpers;

import com.killerbean.shell.model.Bean;
import com.killerbean.shell.model.Order;
import com.killerbean.shell.model.OrderClientView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessor {


    public static Map<Integer, OrderClientView> processOrderJSON(String orderJSON) {
        Map<Integer, OrderClientView> resultMap = new HashMap<>();

        JSONArray jsonArray = new JSONArray(orderJSON);

        for (int index = 0; index < jsonArray.length(); index++) {

            JSONObject jsonObject = jsonArray.getJSONObject(index);


            long id = jsonObject.getLong("id");
            String date = jsonObject.getString("dateTime");
            JSONObject statusObject = jsonObject.getJSONObject("status");
            String status = statusObject.getString("status");

            String agent = "No Agent Assigned yet";

            if (statusObject.getInt("id") > 1) {
                JSONObject agentObject = jsonObject.getJSONObject("agent");
                agent = agentObject.getString("alias");
            }

            OrderClientView order = new OrderClientView(id, date, status, agent);
            resultMap.put((int)id, order);
        }
        return resultMap;
    }


    public static void printOrders(Map<Integer, OrderClientView> orderMap) {

        System.out.println("\u001B[32mReference\t\tDate Created\t\t\t\tStatus\t\t\t\tAgent Assigned\u001B[0m");
        System.out.println("\u001B[33m------------------------------------------------------------------------------------------\u001B[0m");
        for (Map.Entry<Integer, OrderClientView> entry : orderMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public static void viewOrders(List<Order> orderList) {
        System.out.println("+---------+---------------------+-------------------+-------------------+");
        System.out.println("|  INDEX  |       Customer      |      Address      |      STATUS       |");
        System.out.println("+---------+---------------------+-------------------+-------------------+");
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            String customerAlias = order.getCustomer().getAlias();
            String streetAddress = order.getAddress().getStreetAddress();
            String status = order.getStatus().getStatus();
            System.out.printf("| %7d | %-19s | %-17s | %-17s |\n", i+1, customerAlias, streetAddress,status);
        }
        System.out.println("+---------+---------------------+-------------------+-------------------+");
    }

}
