package com.killerbeans.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private List<Order> orders;

    public Address(Long id, String streetAddress, String suburb, String city, List<Order> orders) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.suburb = suburb;
        this.city = city;
        this.orders = orders;
    }

    public Address() {
    }

    public Address(String streetAddress, String suburb, String city, List<Order> orders) {
        this.streetAddress = streetAddress;
        this.suburb = suburb;
        this.city = city;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}