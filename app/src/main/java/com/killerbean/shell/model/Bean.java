package com.killerbean.shell.model;

import java.math.BigDecimal;
import java.util.List;



public class Bean {
    private Long id;

    private String name;


    private int timeToKill;


    private int quantity;
//    private List<OrderLine> orderLines;
    private BigDecimal currentPrice;


    // Constructor, getters, and setters...

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setCurrentPrice(Price price) {
        this.currentPrice = price.getPrice();
    }

    public Bean() {
    }

    public Bean(Long id, String name, int timeToKill, int quantity, BigDecimal currentPrice) {
        this.id = id;
        this.name = name;
        this.timeToKill = timeToKill;
        this.quantity = quantity;
        this.currentPrice=currentPrice;
    }

    public Bean(String name, int timeToKill, int quantity) {
        this.name = name;
        this.timeToKill = timeToKill;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeToKill() {
        return timeToKill;
    }

    public void setTimeToKill(int timeToKill) {
        this.timeToKill = timeToKill;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("| %-5d| %-16s| %-12d| %-9d| %-13s  |\n", id, name, timeToKill, quantity, currentPrice));
        sb.append("+------+-----------------+-------------+----------+----------------+\n");
        return sb.toString();
    }



}
