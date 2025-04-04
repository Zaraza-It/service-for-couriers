package org.example.serviceforcouriers.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.dto.kafka.KafkaOrderDTO;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.enums.Status;
import org.example.serviceforcouriers.exceptions.OrderNotFoundException;
import org.example.serviceforcouriers.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "topic2",
            groupId = "group1",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenOrder(@Valid KafkaOrderDTO dto) {
        create(dto);
    }

    public void create(KafkaOrderDTO dto) {
        Order order = new Order();
        order.setProduct(dto.getProduct());
        order.setPrice(dto.getPrice());
        order.setStatus(Status.NOT_TAKEN);
        order.setAddress(dto.getAddress());
        order.setOffsetDateTime(OffsetDateTime.now());
        order.setCustomerName(dto.getCustomerName());
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save order", e);
        }
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> getById(Long orderId) {
        return Optional.ofNullable(orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Аккаунт с данным id не был найден")));
    }

    @Transactional
    public void changeStatus(Long orderId, Status status) {
        Optional<Order> order = getById(orderId);
        if (order.isPresent()) {
            order.get()
                    .setStatus(Status.valueOf(String.valueOf(status)));
        } else throw new OrderNotFoundException("Заказ не найден!");
    }

    @Transactional
    public void changeAddress(Long orderId, String address) {
        Optional<Order> order = getById(orderId);
        if (order.isPresent()) {
            order.get().setAddress(address);
            try {
                orderRepository.save(order.get());
            } catch (Exception e) {
                throw new RuntimeException("Failed to save order");
            }
        }
    }

    @Transactional
    public void changeUser(@Positive Long orderId, String userName) {
        Optional<Order> order = getById(orderId);
        if (order.isPresent()) {
            order.get().setExecutorName(userName);
            try {
                orderRepository.save(order.get());
            } catch (Exception e) {
                throw new RuntimeException("Failed to save order");
            }
        }
    }
}