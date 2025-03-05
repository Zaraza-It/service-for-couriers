package org.example.serviceforcouriers.service;

import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.controller.dto.OrderResponse;
import org.example.serviceforcouriers.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivingAnOrderService {

    private final OrderRepository orderRepository;


    public OrderResponse getOrderResponse () {

    }


}
