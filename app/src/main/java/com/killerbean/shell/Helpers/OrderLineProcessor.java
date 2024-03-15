package com.killerbean.shell.Helpers;
import com.killerbean.shell.model.OrderLine;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class OrderLineProcessor {

    public static Map<Integer, OrderLine> processOrderLineJSON(String orderJSON) {
        Map<Integer, OrderLine> resultMap = new HashMap<>();

        JSONArray jsonArray = new JSONArray(orderJSON);

        for (int index = 0; index < jsonArray.length(); index++) {

            JSONObject jsonObject = jsonArray.getJSONObject(index);

            JSONObject beanObject = jsonObject.getJSONObject("bean");
            String name = beanObject.getString("name");
            int timeToKill = beanObject.getInt("timeToKill");
            double price = beanObject.getDouble("currentPrice");
            int quantityBought = jsonObject.getInt("quantity");

            OrderLine orderLine = new OrderLine(name, timeToKill, price, quantityBought,price*quantityBought);
            resultMap.put(index, orderLine);
        }
        return resultMap;
    }

    public static void printOrderLines(Map<Integer, OrderLine> orderMap) {

        System.out.println("name\t\ttimeToKill\t\t\t\tprice\t\t\t\tQuantity Bought\t\t\tTotal Cost");
        System.out.println("------------------------------------------------------------------------------------------");
        for (Map.Entry<Integer, OrderLine> entry : orderMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

}
