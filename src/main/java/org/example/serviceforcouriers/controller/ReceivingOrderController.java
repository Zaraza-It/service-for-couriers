package org.example.serviceforcouriers.controller;

import org.example.serviceforcouriers.controller.dto.OrderRequest;
import org.example.serviceforcouriers.controller.dto.OrderResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceivingOrderController {

    @GetMapping("/orders")
    public OrderResponse getOrderResponse (OrderResponse orderResponse) {

    }

    @PostMapping("/orders/{productId}")
    public OrderRequest OrderCompleted (OrderRequest orderRequest) {
        
    }




}
