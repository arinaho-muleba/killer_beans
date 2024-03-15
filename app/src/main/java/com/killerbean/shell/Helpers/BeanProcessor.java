package com.killerbean.shell.Helpers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.killerbean.shell.model.Bean;
import org.json.*;

public class BeanProcessor {

    public static Map<Integer,Bean> processBeanJSON(String beanJSON) {
        Map<Integer, Bean> resultMap = new HashMap<>();

        JSONArray jsonArray = new JSONArray(beanJSON);

        for (int index = 0; index < jsonArray.length(); index++) {
            JSONObject jsonObject = jsonArray.getJSONObject(index);

            long id = jsonObject.getLong("id");
            String name = jsonObject.getString("name");
            int timeToKill = jsonObject.getInt("timeToKill");
            int quantity = jsonObject.getInt("quantity");
            BigDecimal currentPrice = jsonObject.getBigDecimal("currentPrice");

            Bean bean = new Bean(id,name,timeToKill,quantity,currentPrice);

            resultMap.put(index, bean);
        }

        return resultMap;
    }

    public static void printBeans(Map<Integer,Bean> beanMap){

        System.out.print("+------+-----------------+-------------+----------+----------------+\n");
        System.out.print("|  ID  |       Name      |  DaysToKill | Quantity | Current Price  |\n");
        System.out.print("+------+-----------------+-------------+----------+----------------+\n");
        for (Map.Entry<Integer, Bean> entry : beanMap.entrySet()) {
            System.out.print(entry.getValue());
        }
    }

    public static void mai(String[] args) {
        // Example beanJSON string
        String beanJSON = "[{\"id\":1,\"name\":\"Castor Beans\",\"timeToKill\":3,\"quantity\":100,\"currentPrice\":120.00},{\"id\":2,\"name\":\"Lima Beans\",\"timeToKill\":1,\"quantity\":50,\"currentPrice\":100.00},{\"id\":3,\"name\":\"Jequirity Beans\",\"timeToKill\":2,\"quantity\":75,\"currentPrice\":80.00},{\"id\":4,\"name\":\"Velvet Beans\",\"timeToKill\":4,\"quantity\":30,\"currentPrice\":70.00},{\"id\":5,\"name\":\"Lupin Beans\",\"timeToKill\":7,\"quantity\":20,\"currentPrice\":30.00},{\"id\":6,\"name\":\"Raw Kidney Beans\",\"timeToKill\":10,\"quantity\":15,\"currentPrice\":50.00},{\"id\":7,\"name\":\"Ignatius Beans\",\"timeToKill\":9,\"quantity\":10,\"currentPrice\":70.00},{\"id\":8,\"name\":\"Lucky Beans\",\"timeToKill\":1,\"quantity\":25,\"currentPrice\":60.00}]";

        // Process beanJSON
        Map<Integer,Bean> resultMap = processBeanJSON(beanJSON);

        // Print the processed map
        System.out.print("+------+-----------------+-------------+----------+----------------+\n");
        System.out.print("|  ID  |       Name      |  DaysToKill | Quantity | Current Price  |\n");
        System.out.print("+------+-----------------+-------------+----------+----------------+\n");
        for (Map.Entry<Integer, Bean> entry : resultMap.entrySet()) {
            System.out.print(entry.getValue());
        }
    }
}