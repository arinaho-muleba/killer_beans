package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can define custom query methods here if needed
    Optional<Customer> findByAlias(String alias);

}