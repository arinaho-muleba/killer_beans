package com.killerbeans.server.controllers;

import com.killerbeans.server.models.Order;
import com.killerbeans.server.models.dtos.OrderCreationRequest;
import com.killerbeans.server.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getOrderById(@PathVariable String id) {

        Long orderId;
        try {
            orderId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ID must be a valid Long value");
        }
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        return ResponseEntity.ok(order);
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
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreationRequest orderRequest) {
        Order createdOrder = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}/assignAgent")
    public ResponseEntity<Order> assignAgentToOrder(@PathVariable Long orderId, @RequestParam Long agentId) {
        Order updatedOrder = orderService.assignAgentToOrder(orderId, agentId);
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/{orderId}/progressOrder")
    public ResponseEntity<Order> progressOrder(@PathVariable Long orderId, @RequestParam Long agentId) {
        Order updatedOrder = orderService.progressStatus(orderId, agentId);
        return ResponseEntity.ok(updatedOrder);
    }

}
