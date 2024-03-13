package com.killerbeans.server.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    public Order(Long id, Long customerId, LocalDateTime dateTime, Long addressId, Long statusId, Long agentId) {
        this.id = id;
        this.customerId = customerId;
        this.dateTime = dateTime;
        this.addressId = addressId;
        this.statusId = statusId;
        this.agentId = agentId;
    }

    public Order(Long customerId, LocalDateTime dateTime, Long addressId, Long statusId, Long agentId) {
        this.customerId = customerId;
        this.dateTime = dateTime;
        this.addressId = addressId;
        this.statusId = statusId;
        this.agentId = agentId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "address_id", nullable = false)
    private Long addressId;

    @Column(name = "status_id", nullable = false)
    private Long statusId;

    @Column(name = "agent_id")
    private Long agentId;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", dateTime=" + dateTime +
                ", addressId=" + addressId +
                ", statusId=" + statusId +
                ", agentId=" + agentId +
                '}';
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
}
