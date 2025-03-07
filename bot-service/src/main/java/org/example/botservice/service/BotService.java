package org.example.botservice.service;

import lombok.RequiredArgsConstructor;
import org.example.botservice.dao.OrderRepository;
import org.example.botservice.dto.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotService {
public final OrderRepository orderRepository;
    public String getAllOrders() {
        try {
           List<Order> order = orderRepository.findAll();
            return order.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

