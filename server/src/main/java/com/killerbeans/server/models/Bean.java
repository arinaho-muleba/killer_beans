package com.killerbeans.server.models;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Beans")
public class Bean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "time_to_kill")
    private int timeToKill;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "bean")
    private List<OrderLine> orderLines;
    @Transient
    private BigDecimal currentPrice;


    // Constructor, getters, and setters...

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

//    public void setCurrentPrice(BigDecimal currentPrice) {
//        this.currentPrice = currentPrice;
//    }

    public void setCurrentPrice(Price price) {
        this.currentPrice = price.getPrice();
    }

    public Bean() {
    }

    public Bean(Long id, String name, int timeToKill, int quantity) {
        this.id = id;
        this.name = name;
        this.timeToKill = timeToKill;
        this.quantity = quantity;
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
        return "Bean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeToKill=" + timeToKill +
                ", quantity=" + quantity +
                '}';
    }
}
