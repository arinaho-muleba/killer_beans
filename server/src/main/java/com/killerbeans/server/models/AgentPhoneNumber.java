package com.killerbeans.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "agent_phone_numbers")
public class AgentPhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    @JsonIgnore
    private Agent agent;

    @Column(name = "phone_number")
    private String phoneNumber;

    // Constructors, getters, and setters

    public AgentPhoneNumber() {
    }

    public AgentPhoneNumber(Agent agent, String phoneNumber) {
        this.agent = agent;
        this.phoneNumber = phoneNumber;
    }

    public AgentPhoneNumber(Long id, Agent agent, String phoneNumber) {
        this.id = id;
        this.agent = agent;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
