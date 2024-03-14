package com.killerbeans.server.services;

import com.killerbeans.server.models.*;
import com.killerbeans.server.models.dtos.MinOrderLine;
import com.killerbeans.server.models.dtos.OrderCreationRequest;
import com.killerbeans.server.repositories.*;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final OrderLineRepository orderLineRepository;
    private  final BeanRepository beanRepository;
    private final StatusService statusService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        AddressRepository addressRepository,
                        CustomerRepository customerRepository,
                        OrderLineRepository orderLineRepository,
                        BeanRepository beanRepository,
                        StatusService statusService) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.orderLineRepository = orderLineRepository;
        this.beanRepository = beanRepository;
        this.statusService = statusService;
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

    public Order createOrder(OrderCreationRequest orderRequest) {
        // Retrieve customer and address from request
        Customer customer = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Address address = orderRequest.getAddress();

        // Save address if it doesn't exist
        address = addressRepository.save(address);

        // Create order
        Order order = new Order();
        order.setCustomer(customer);
        order.setDateTime(LocalDateTime.now());
        order.setAddress(address);
        // Optionally set status, agent, etc.
        statusService.initialStatus().ifPresent(order::setStatus);

        // Save order
        order = orderRepository.save(order);

        // Add order lines
        for (MinOrderLine minOrderLine : orderRequest.getMinOrderLines()) {
            Bean bean = beanRepository.findById(minOrderLine.getItemId()).orElseThrow(() -> new IllegalArgumentException("Item not found"));
            OrderLine orderLine = new OrderLine();
            orderLine.setOrder(order);
            orderLine.setBean(bean);
            orderLine.setQuantity(minOrderLine.getQuantity());
            // Save order line
            orderLineRepository.save(orderLine);
            // Optionally handle quantity, bean existence, etc.
        }

        return order;
    }

}
