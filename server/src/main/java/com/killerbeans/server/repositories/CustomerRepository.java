package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can define custom query methods here if needed
}