package com.killerbean.shell.model;

import java.math.BigDecimal;

public class OrderClientView {

    private Long id;
    private String date;
    private String status;
    private String agent;


    public OrderClientView(Long id, String date, String status, String agent) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.agent = agent;
    }


    public String getStatus() {
        return status;
    }
    public String getAgent() {
        return agent;
    }
    public Long getId() {
        return id;
    }
    public String getDate() {
        return date;
    }

    @Override
    public String toString() {

        String finalDate = date;

        if(date.contains("."))
            finalDate = date.substring(0, date.indexOf('.'));


        return String.format("%-9d\t%-15s\t%-20s\t%-15s", id, finalDate, status, agent);
    }
}
