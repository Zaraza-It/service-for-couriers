package org.example.serviceforcouriers.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.dto.kafka.KafkaOrderDTO;
import org.example.serviceforcouriers.dto.order.CreateOrderDTO;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.enums.Status;
import org.example.serviceforcouriers.exceptions.OrderNotFoundException;
import org.example.serviceforcouriers.mapper.CreateOrderMapper;
import org.example.serviceforcouriers.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CreateOrderMapper createOrderMapper;

    @KafkaListener(topics = "topic2",
            groupId = "group1",
            containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void listenOrder(@Valid CreateOrderDTO dto) {
        create(dto);
    }

    @Transactional
    public void create(CreateOrderDTO dto) {
        Order order = createOrderMapper.toEntity(dto);
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Аккаунт с данным id не был найден"));
    }

//    @Transactional
//    public void changeStatus(Long orderId, Status status) {
//        Order order = getById(orderId);
//        order.setStatus(status);
//    }
//
//    @Transactional
//    public void changeAddress(Long orderId, String address) {
//        Order order = getById(orderId);
//        order.setAddress(address);
//    }
//
//    @Transactional
//    public void changeUser(@Positive Long orderId, String username) {
//        Order order = getById(orderId);
//        order.setExecutorName(username);
//    }
}