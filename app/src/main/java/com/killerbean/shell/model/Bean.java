package com.killerbean.shell.model;

import java.math.BigDecimal;
import java.util.List;



public class Bean {
    private Long id;
    private String name;

    private int timeToKill;
    private int quantity;
    private BigDecimal currentPrice;
    public BigDecimal getCurrentPrice() {
        return currentPrice;
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

    public String getName() {
        return name;
    }

    public int getTimeToKill() {
        return timeToKill;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String toString(int index) {
        return String.format("| %-5d| %-16s| %-12d| %-9d| %-13s  |\n", index, name, timeToKill, quantity, currentPrice) +
                "+------+-----------------+-------------+----------+----------------+\n";
    }



}
