package org.example.serviceforcouriers.controller;

import org.example.serviceforcouriers.controller.dto.OrderRequestDTO;
import org.example.serviceforcouriers.controller.dto.OrderResponseDTO;
import org.example.serviceforcouriers.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<OrderResponseDTO> getAllOrders () {
        return orderService.getAllOrders().stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
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