package com.killerbeans.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "statuses")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Order> orders;

    // Constructors, getters, and setters

    public Status() {
    }

    public Status(String status, List<Order> orders) {
        this.status = status;
        this.orders = orders;
    }

    public Status(Long id, String status, List<Order> orders) {
        this.id = id;
        this.status = status;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
