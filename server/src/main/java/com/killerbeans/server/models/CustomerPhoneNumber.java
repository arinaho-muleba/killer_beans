package com.killerbeans.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "customer_phone_numbers")
public class CustomerPhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @Column(name = "phone_number")
    private String phoneNumber;

    // Constructors, getters, and setters

    public CustomerPhoneNumber() {
    }

    public CustomerPhoneNumber(Customer customer, String phoneNumber) {
        this.customer = customer;
        this.phoneNumber = phoneNumber;
    }

    public CustomerPhoneNumber(Long id, Customer customer, String phoneNumber) {
        this.id = id;
        this.customer = customer;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
