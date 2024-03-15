package com.killerbeans.server.controllers;

import com.killerbeans.server.models.Order;
import com.killerbeans.server.models.OrderLine;
import com.killerbeans.server.models.Price;
import com.killerbeans.server.repositories.OrderLineRepository;
import com.killerbeans.server.repositories.OrderRepository;
import com.killerbeans.server.repositories.PriceRepository;
import com.killerbeans.server.services.OrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/orderLines")
public class OrderLineController {

    private final OrderLineService orderLineService;
    private final PriceRepository priceRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderLineController(OrderLineService orderLineService, PriceRepository priceRepository,OrderRepository orderRepository, OrderLineRepository orderLineRepository) {
        this.orderLineService = orderLineService;
        this.priceRepository = priceRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{id}")
    public OrderLine getOrderLineById(@PathVariable Long id) {
        return orderLineService.getOrderLineById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order Line not found with ID: " + id));
    }

    @GetMapping("/all")
    public List<OrderLine> getAllOrderLines() {
        List<OrderLine> orderLines = orderLineService.getAllOrderLines();
        for(OrderLine orderLine : orderLines){
            Optional<Price> currentPrice = priceRepository.findCurrentPriceByBeanId(orderLine.getBean().getId());
            currentPrice.ifPresent(orderLine.getBean()::setCurrentPrice);
        }
        return orderLines;
    }

    @GetMapping("/byOrder/{orderId}")
    public List<OrderLine> getOrderLinesByOrderId(@PathVariable Long orderId) {
                List <OrderLine> orderLines = orderLineService.getOrderLinesByOrderId(orderId);
        for (OrderLine orderLine : orderLines) {
            Optional<Price> currentPrice = priceRepository.findCurrentPriceByBeanId(orderLine.getBean().getId());
            currentPrice.ifPresent(orderLine.getBean()::setCurrentPrice);
        }
        return orderLines;

    }

    @PostMapping("/create")
    public OrderLine createOrderLine(@RequestBody OrderLine orderLine) {
        return orderLineService.saveOrderLine(orderLine);
    }

    @PutMapping("/{id}")
    public OrderLine updateOrderLine(@PathVariable Long id, @RequestBody OrderLine orderLine) {
        if (!orderLineService.getOrderLineById(id).isPresent()) {
            throw new IllegalArgumentException("Order Line not found with ID: " + id);
        }
        orderLine.setId(id);
        return orderLineService.saveOrderLine(orderLine);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderLine(@PathVariable Long id) {
        orderLineService.deleteOrderLineById(id);
    }
}
