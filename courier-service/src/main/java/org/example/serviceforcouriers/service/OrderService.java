package org.example.serviceforcouriers.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.controller.dto.OrderResponseDTO;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.exceptions.OrderNotFoundException;
import org.example.serviceforcouriers.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public Long createOrder(String product, String customerName, String executorName, String address,
                            BigDecimal purchasesPrice, BigDecimal purchasesSell) {

        if (product == null || customerName == null || executorName == null || address == null || purchasesPrice == null || purchasesSell == null) {
            throw new IllegalArgumentException("Не все параметры были переданы.");
        }

        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String soldStatus = "Не взят";


        return orderRepository.save( new Order (
                    product,
                    customerName,
                    executorName,
                    address,
                    offsetDateTime,
                    purchasesPrice,
                    purchasesSell,
                    soldStatus
                )
        ).getOrderId();
    }

    public List<Order> getAllOrders () {
        return orderRepository.findAll();
    }


    public Order getOrder (Long orderId) {
        if (orderId == null) {
            throw  new IllegalArgumentException("ID не был передан.");
        }

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с id: " + orderId + " не был найден."));
    }

    public Order updateSoldStatus (Long orderId, String soldStatus) {
        if (orderId == null || soldStatus == null) {
            throw new IllegalArgumentException("Не все параметры были переданы.");
        }

        Order order = getOrder(orderId);
        order.setSoldStatus(soldStatus);
        return order;
    }

    public Order deleteOrderById (Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("ID не был передан.");
        }

        Order deletedOrder = getOrder(orderId);
        orderRepository.deleteById(orderId);
        return deletedOrder;
    }

}
