package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // You can add custom query methods here if needed
    List<Order> findByAgentId(Long agentId);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStatusId(Long statusId);
}
