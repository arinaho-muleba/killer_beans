package com.killerbeans.server.models;
import jakarta.persistence.*;

@Entity
@Table(name = "Beans")
public class Bean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "time_to_kill")
    private int timeToKill;

    @Column(name = "quantity")
    private int quantity;


}
