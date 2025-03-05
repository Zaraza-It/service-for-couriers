package org.example.serviceforcouriers.service;

import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.exceptions.ProductNotFoundException;
import org.example.serviceforcouriers.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order getOrderById (Long productId) {

        return orderRepository
                .findById(productId)
                .orElseThrow(() ->  new ProductNotFoundException("Товар не был найден"));
    }

    public List<Order> getAllOrders () {
        return orderRepository.findAll();
    }

}
