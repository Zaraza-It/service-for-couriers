package org.example.serviceforcouriers.controller;

import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.controller.dto.OrderRequestStatus;
import org.example.serviceforcouriers.controller.dto.OrderResponse;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReceivingOrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> getOrdersResponse () {
        List<Order> orders = orderService.getAllOrders();

        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/orders/{productId}")
    public OrderResponse getOrderById (@PathVariable Long productId) {
        return new OrderResponse(orderService.getOrderById(productId));
    }

    @PostMapping("/orders/{productId}")
    public String orderRequestStatus (@RequestBody OrderRequestStatus orderRequestStatus) {
        Order order = orderService.getOrderById(orderRequestStatus.getProductId());
        order.setSoldStatus(orderRequestStatus.isSoldStatus());
        return "success";
    }

}
