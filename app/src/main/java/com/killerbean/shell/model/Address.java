package com.killerbean.shell.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    @JsonProperty("streetAddress")
    private String streetAddress;

    public Address(String streetAddress, String suburb, String city) {
        this.streetAddress = streetAddress;
        this.suburb = suburb;
        this.city = city;
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

    @JsonProperty("suburb")
    private String suburb;
    @JsonProperty("city")
    private String city;

    // Other fields such as id, suburb, city can be added here if needed.

    // Getters and setters
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "Address{" +
                "streetAddress='" + streetAddress + '\'' +
                '}';
    }
}