package com.killerbeans.server.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.killerbeans.server.models.Address;

import java.util.List;

public class OrderCreationRequest {

    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("minOrderLines")
    private List<MinOrderLine> minOrderLines;

    // Constructors, getters, and setters

    public OrderCreationRequest() {
    }

    public OrderCreationRequest(Long customerId, Address address, List<MinOrderLine> minOrderLines) {
        this.customerId = customerId;
        this.address = address;
        this.minOrderLines = minOrderLines;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<MinOrderLine> getMinOrderLines() {
        return minOrderLines;
    }

    public void setMinOrderLines(List<MinOrderLine> minOrderLines) {
        this.minOrderLines = minOrderLines;
    }
}
