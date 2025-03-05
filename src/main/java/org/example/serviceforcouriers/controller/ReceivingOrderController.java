package org.example.serviceforcouriers.controller;

import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.controller.dto.OrderRequestStatus;
import org.example.serviceforcouriers.controller.dto.OrderResponse;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.service.OrderService;
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
