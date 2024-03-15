package com.killerbean.shell.Helpers;

import com.killerbean.shell.model.Bean;
import com.killerbean.shell.model.OrderClientView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
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

        System.out.println("name\t\ttimeToKill\t\t\t\tprice\t\t\t\tQuantity Bought");
        System.out.println("------------------------------------------------------------------------------------------");
        for (Map.Entry<Integer, OrderClientView> entry : orderMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

}
