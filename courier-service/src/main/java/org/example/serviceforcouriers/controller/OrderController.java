package org.example.serviceforcouriers.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.dto.order.ChangeOrderDTO;
import org.example.serviceforcouriers.dto.order.CreateOrderDTO;
import org.example.serviceforcouriers.dto.order.OrderResponseDTO;
import org.example.serviceforcouriers.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void createOrder(@NotBlank @RequestHeader(name = "AccessToken") String token,
                                            @RequestBody final CreateOrderDTO orderDto) {
        orderService.create(token, orderDto);
    }

    @PostMapping("/{orderId}")
    public void createRequestToChangeStatus() {
        //TODO
    }

    @PutMapping("/{orderId}")
    public OrderResponseDTO putOrder(@RequestBody final ChangeOrderDTO orderDTO) {
        if (nonNull(orderDTO.getStatus())) {
            orderService.changeStatus(orderDTO.getOrderId(), orderDTO.getStatus());
        }
        if (nonNull(orderDTO.getAddress())) {
            orderService.changeAddress(orderDTO.getOrderId(), orderDTO.getAddress());
        }
        if (nonNull(orderDTO.getUser())) {
            orderService.changeUser(orderDTO.getOrderId(), orderDTO.getUser());
        }
        return new OrderResponseDTO(orderService.getById(orderDTO.getOrderId()))
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrder(@PathVariable Long orderId) {
        return new OrderResponseDTO(orderService.getById(orderId));
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAll()
                .stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}