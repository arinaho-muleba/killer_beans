package com.killerbeans.server.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MinOrderLine {

    @JsonProperty("itemId")
    private Long itemId;

    @JsonProperty("quantity")
    private int quantity;

    // Constructors, getters, and setters

    public MinOrderLine() {
    }

    public MinOrderLine(Long itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
