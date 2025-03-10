package org.example.serviceforcouriers.controller;

import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.controller.dto.OrderRequestDTO;
import org.example.serviceforcouriers.controller.dto.OrderResponseDTO;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductsController {

    private final OrderService orderService;

    public ProductsController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders () {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public OrderResponseDTO getOrderById (@PathVariable Long orderId) {
        return new OrderResponseDTO(orderService.getOrder(orderId));
    }

    @PostMapping("/orders")
    public Long createOrder (@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(
                orderRequestDTO.getProduct(),
                orderRequestDTO.getCustomerName(),
                orderRequestDTO.getExecutorName(),
                orderRequestDTO.getAddress(),
                orderRequestDTO.getPurchasesPrice(),
                orderRequestDTO.getPurchasesSell()
        );
    }

    @PutMapping("orders/{orderId}")
    public OrderResponseDTO updateSoldStatus (@PathVariable Long orderId,
                                              String soldStatus) {
        return new OrderResponseDTO(orderService.updateSoldStatus(orderId, soldStatus));
    }

    @DeleteMapping("orders/{orderId}")
    public OrderResponseDTO deleteAccount (@PathVariable Long orderId) {
        return new OrderResponseDTO(orderService.deleteOrderById(orderId));
    }
}