package com.killerbeans.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @OneToMany(mappedBy = "agent")
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "agent")
    @JsonIgnore
    private List<AgentPhoneNumber> phoneNumbers;

    // Constructors, getters, and setters

    public Agent() {
    }

    public Agent(String alias, String firstname, String lastname, List<Order> orders, List<AgentPhoneNumber> phoneNumbers) {
        this.alias = alias;
        this.firstname = firstname;
        this.lastname = lastname;
        this.orders = orders;
        this.phoneNumbers = phoneNumbers;
    }

    public Agent(Long id, String alias, String firstname, String lastname, List<Order> orders, List<AgentPhoneNumber> phoneNumbers) {
        this.id = id;
        this.alias = alias;
        this.firstname = firstname;
        this.lastname = lastname;
        this.orders = orders;
        this.phoneNumbers = phoneNumbers;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<AgentPhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<AgentPhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}