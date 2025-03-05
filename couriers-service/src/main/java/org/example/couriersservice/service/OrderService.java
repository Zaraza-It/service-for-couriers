package org.example.couriersservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.couriersservice.entity.Order;
import org.example.couriersservice.exceptions.ProductNotFoundException;
import org.example.couriersservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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

    public Order saveSoldStatus(Long productId, boolean status) {
        Order order = getOrderById(productId);
        order.setSoldStatus(status);
        return orderRepository.save(order);
    }

    public Order newOrder (String product, String customerName, String executorName, String address, BigDecimal purchasesPrice, BigDecimal purchasesSell) {
        return orderRepository.save(new Order(product, customerName, executorName, address, purchasesPrice, purchasesSell));
    }



}
