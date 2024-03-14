package com.killerbeans.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias")
    private String alias;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<CustomerPhoneNumber> phoneNumbers;

    // Constructors, getters, and setters

    public Customer(Long id, String alias, List<Order> orders, List<CustomerPhoneNumber> phoneNumbers) {
        this.id = id;
        this.alias = alias;
        this.orders = orders;
        this.phoneNumbers = phoneNumbers;
    }

    public Customer(String alias, List<Order> orders, List<CustomerPhoneNumber> phoneNumbers) {
        this.alias = alias;
        this.orders = orders;
        this.phoneNumbers = phoneNumbers;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<CustomerPhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<CustomerPhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
