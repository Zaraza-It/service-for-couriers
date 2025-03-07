package org.example.serviceforcouriers.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.serviceforcouriers.controller.dto.OrderRequestDTO;
import org.example.serviceforcouriers.controller.dto.OrderRequestStatusDTO;
import org.example.serviceforcouriers.controller.dto.OrderResponseDTO;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductsController {

    private static final Logger logger = LogManager.getLogger(ProductsController.class);
    private final OrderService orderService;

    // Работает
    @GetMapping("/products")
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }


    // Работает
    @GetMapping("/products/{productId}")
    public OrderResponseDTO getOrderById(@PathVariable Long productId) {

        logger.info("Requested product ID: {}", productId); // Логирование

        Order order = orderService.getOrderById(productId);

        logger.info("Order from DB: {}", order); // Логирование

        return new OrderResponseDTO(order);
    }


    // Работает
    @PostMapping("/products/{productId}/status")
    public String updateOrderStatus(
            @PathVariable Long productId,
            @RequestBody OrderRequestStatusDTO soldStatus
    ) {
        if (soldStatus == null) {
            return "Статус равен значению null";
        }

        if (soldStatus.isSoldStatus() == true || soldStatus.isSoldStatus() == false ) {
            orderService.saveSoldStatus(productId, soldStatus.isSoldStatus());
            return "Статус заказа успешно обновлен";
        } else {
            return "Значение не является boolean";
        }
    }

//    // Работает // Изменил код
//    @PostMapping("/create")
//    public void createOrder(@RequestBody OrderRequestDTO orderRequest) {
//            orderService.createOrder(orderRequest);
//    }

}