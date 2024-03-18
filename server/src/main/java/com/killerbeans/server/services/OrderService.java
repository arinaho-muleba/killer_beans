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
    private  final AgentRepository agentRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        AddressRepository addressRepository,
                        CustomerRepository customerRepository,
                        OrderLineRepository orderLineRepository,
                        BeanRepository beanRepository,
                        StatusService statusService,
                        AgentRepository agentRepository,
                        StatusRepository statusRepository
                        ) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.orderLineRepository = orderLineRepository;
        this.beanRepository = beanRepository;
        this.statusService = statusService;
        this.agentRepository = agentRepository;

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

        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Address address = orderRequest.getAddress();

        address = addressRepository.save(address);

        Order order = new Order();
        order.setCustomer(customer);
        order.setDateTime(LocalDateTime.now());
        order.setAddress(address);
        statusService.initialStatus().ifPresent(order::setStatus);

        order = orderRepository.save(order);

        for (MinOrderLine minOrderLine : orderRequest.getMinOrderLines()) {
            Bean bean = beanRepository.findById(minOrderLine.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("Item not found"));

            if (bean.getQuantity() < minOrderLine.getQuantity()) {
                throw new IllegalArgumentException("Not enough quantity available for item: " + bean.getName());
            }

            OrderLine orderLine = new OrderLine();
            orderLine.setOrder(order);
            orderLine.setBean(bean);
            orderLine.setQuantity(minOrderLine.getQuantity());

            orderLineRepository.save(orderLine);

            int remainingQuantity = bean.getQuantity() - minOrderLine.getQuantity();
            bean.setQuantity(remainingQuantity);
            beanRepository.save(bean);
        }

        return order;
    }

    public Order assignAgentToOrder(Long orderId, Long agentId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new IllegalArgumentException("Agent not found with ID: " + agentId));

        order.setAgent(agent);
        order.setStatus(statusService.getStatusById(2L));

        return orderRepository.save(order);
    }

    public Order progressStatus(Long orderId, Long agentId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new IllegalArgumentException("Agent not found with ID: " + agentId));

        Status currentStatus =  order.getStatus();
        if(currentStatus.getId()<statusService.getNumberOfStatuses()){
            order.setStatus(statusService.getStatusById(currentStatus.getId()+1L));
        }

        return orderRepository.save(order);
    }

}
