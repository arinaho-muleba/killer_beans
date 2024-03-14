package com.killerbeans.server.models;

import jakarta.persistence.*;

@Entity
@Table(name = "order_lines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "bean_id")
    private Bean bean;

    @Column(name = "quantity")
    private int quantity;

    // Constructors, getters, and setters

    public OrderLine(Long id, Order order, Bean bean, int quantity) {
        this.id = id;
        this.order = order;
        this.bean = bean;
        this.quantity = quantity;
    }

    public OrderLine(Order order, Bean bean, int quantity) {
        this.order = order;
        this.bean = bean;
        this.quantity = quantity;
    }

    public OrderLine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}