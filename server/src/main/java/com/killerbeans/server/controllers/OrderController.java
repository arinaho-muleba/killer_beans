package com.killerbeans.server.controllers;

import com.killerbeans.server.models.Order;
import com.killerbeans.server.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
    }

    // Add more endpoints as needed
    @GetMapping("/byAgent/{agentId}")
    public List<Order> getOrdersByAgentId(@PathVariable Long agentId) {
        return orderService.getOrdersByAgentId(agentId);
    }

    @GetMapping("/byUser/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/available")
    public List<Order> getOrdersByStatusId() {
        return orderService.getOrdersByStatusId(1L);
    }
}
