package org.example.serviceforcouriers.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.dto.order.CreateOrderDTO;
import org.example.serviceforcouriers.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public void createOrder(@NotBlank @RequestHeader(name = "AccessToken") String token,
                                            @RequestBody final CreateOrderDTO orderDto) {
        orderService.create(orderDto);
    }

    @PostMapping("/{orderId}")
    public void createRequestToChangeStatus() {
        //TODO
    }

//    TODO: никакой бизнес логики не должно быть в контроллере. Нужно переносить этот метод в сервис и поменять название
//    @PutMapping("/{orderId}")
//    public OrderResponseDTO putOrder(@RequestBody final ChangeOrderDTO orderDTO) {
//        if (nonNull(orderDTO.getStatus())) {
//            orderService.changeStatus(orderDTO.getOrderId(), orderDTO.getStatus());
//        }
//        if (nonNull(orderDTO.getAddress())) {
//            orderService.changeAddress(orderDTO.getOrderId(), orderDTO.getAddress());
//        }
//        if (nonNull(orderDTO.getUser())) {
//            orderService.changeUser(orderDTO.getOrderId(), orderDTO.getUser());
//        }
//        return new OrderResponseDTO(orderService.getById(orderDTO.getOrderId()))
//    }

//    @GetMapping("/{orderId}")
//    public OrderResponseDTO getOrder(@PathVariable Long orderId) {
//        return orderService.getById(orderId);
//    }

//    TODO: НИКАКОЙ БИЗНЕС ЛОГИКИ В КОНТРОЛЛЕРЕ. Даже преобразование в dto должно быть в service
//    @GetMapping("/order")
//    public List<OrderResponseDTO> getAllOrders() {
//        return orderService.getAll()
//                .stream()
//                .map(OrderResponseDTO::new)
//                .collect(Collectors.toList());
//    }
}