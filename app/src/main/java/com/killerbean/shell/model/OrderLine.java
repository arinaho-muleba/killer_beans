package com.killerbean.shell.model;

public class OrderLine {


    private String name;
    private int timeToKillInDays;
    private double price;
    private int quantityBought;
    private double totalCost;


    public OrderLine(String name, int timeToKill, double price, int quantityBought,double totalCost ) {
        this.name = name;
        this.timeToKillInDays = timeToKill;
        this.price = price;
        this.quantityBought = quantityBought;
        this.totalCost = totalCost;
    }


    public String getName() {
        return name;
    }

    public int getTimeToKill() {
        return timeToKillInDays;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityBought() {
        return quantityBought;
    }

    @Override
    public String toString() {
        return String.format("| %-15s | %-15d | %-20.2f | %-25d | %-15.2f |",
                name, timeToKillInDays, price, quantityBought, totalCost);
    }


}


