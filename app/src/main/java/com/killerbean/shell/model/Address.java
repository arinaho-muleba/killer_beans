package com.killerbean.shell.model;
public class Address {

    private Long id;
    private String streetAddress;
    private String suburb;
    private String city;

    public Address(String streetAddress, String suburb, String city) {
        this.streetAddress = streetAddress;
        this.suburb = suburb;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }
}