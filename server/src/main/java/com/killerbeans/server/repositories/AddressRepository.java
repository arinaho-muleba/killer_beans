package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // You can define custom query methods here if needed
}