package com.killerbeans.server.repositories;

import com.killerbeans.server.models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    // You can add custom query methods here if needed
    List<OrderLine> findAllByOrderId(Long orderId);
}
