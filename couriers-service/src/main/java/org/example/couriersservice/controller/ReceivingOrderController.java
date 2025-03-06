package org.example.couriersservice.controller;

import lombok.RequiredArgsConstructor;

import org.example.couriersservice.controller.dto.OrderRequestStatus;
import org.example.couriersservice.controller.dto.OrderResponse;
import org.example.couriersservice.entity.Order;
import org.example.couriersservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        orderService.saveSoldStatus(
                orderRequestStatus.getProductId(),
                orderRequestStatus.isSoldStatus()
        );
        return "success";
    }

    @PostMapping("/orders")
    public Order createOrder (String product, String customerName, String executorName, String address, BigDecimal purchasesPrice, BigDecimal purchasesSell) {
        return orderService.newOrder(product, customerName, executorName, address, purchasesPrice, purchasesSell);
    }

}
