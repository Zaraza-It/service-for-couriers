package org.example.serviceforcouriers.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.serviceforcouriers.controller.dto.OrderRequest;
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
    private static final Logger logger = LogManager.getLogger(ReceivingOrderController.class);
    private final OrderService orderService;

    // Работает
    @GetMapping("/products")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }


    // Работает
    @GetMapping("/products/{productId}")
    public OrderResponse getOrderById(@PathVariable Long productId) {
        try {
            logger.info("Requested product ID: {}", productId); // Логирование
            Order order = orderService.getOrderById(productId);
            logger.info("Order from DB: {}", order); // Логирование
            return new OrderResponse(order);
        } catch (Exception e) {
            logger.error(e);
        }

        return null;
    }


    // Не работает
    @PostMapping("/products/{productId}/status")
    public String updateOrderStatus(
            @PathVariable Long productId,
            @RequestBody OrderRequestStatus orderRequestStatus
    ) {
        try {
            orderService.saveSoldStatus(productId, orderRequestStatus.isSoldStatus());
            return "Статус заказа успешно обновлен";
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    // Работает // Изменил код
    @PostMapping("/create")
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.createOrder(orderRequest);
        } catch (Exception e) {
            logger.error(e);
        }
    }

}