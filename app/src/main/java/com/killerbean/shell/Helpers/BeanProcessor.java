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
            double currentPrice = jsonObject.getDouble("currentPrice");

            Bean bean = new Bean(id,name,timeToKill,quantity,BigDecimal.valueOf(currentPrice));

            resultMap.put(Math.toIntExact(index+1), bean);
        }

        return resultMap;
    }

    public static void printBeans(Map<Integer,Bean> beanMap){

        System.out.print("+------+-----------------+-------------+----------+----------------+\n");
        System.out.print("|  ID  |       Name      |  DaysToKill | Quantity | Current Price  |\n");
        System.out.print("+------+-----------------+-------------+----------+----------------+\n");

        beanMap.forEach((key, value) -> {
            System.out.println(value.toString(key));
        });
    }
}