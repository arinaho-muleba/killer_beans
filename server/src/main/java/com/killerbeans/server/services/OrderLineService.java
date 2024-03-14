package com.killerbeans.server.services;

import com.killerbeans.server.models.OrderLine;
import com.killerbeans.server.repositories.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public List<OrderLine> getAllOrderLines() {
        return orderLineRepository.findAll();
    }
    public List<OrderLine> getOrderLinesByOrderId(Long orderId){
        return  orderLineRepository.findAllByOrderId(orderId);
    }

    public Optional<OrderLine> getOrderLineById(Long id) {
        return orderLineRepository.findById(id);
    }

    public OrderLine saveOrderLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    public void deleteOrderLineById(Long id) {
        orderLineRepository.deleteById(id);
    }

    // Add more methods as needed
}
