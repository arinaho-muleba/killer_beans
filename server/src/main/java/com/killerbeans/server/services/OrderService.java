package com.killerbeans.server.services;

import com.killerbeans.server.models.Order;
import com.killerbeans.server.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    // Add more methods as needed
    public List<Order> getOrdersByAgentId(Long agentId) {
        return orderRepository.findByAgentId(agentId);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        // Assuming user ID is represented by customerId
        return orderRepository.findByCustomerId(userId);
    }

    public List<Order> getOrdersByStatusId(Long statusId) {
        return orderRepository.findByStatusId(statusId);
    }
}
