package org.example.serviceforcouriers.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.dto.kafka.KafkaOrderDTO;
import org.example.serviceforcouriers.dto.order.CreateOrderDTO;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.entity.User;
import org.example.serviceforcouriers.enums.Status;
import org.example.serviceforcouriers.exceptions.OrderNotFoundException;
import org.example.serviceforcouriers.exceptions.UserNotFoundException;
import org.example.serviceforcouriers.repository.OrderRepository;
import org.example.serviceforcouriers.repository.UserRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final JwtService jwtService;


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
                order.get().setStatus(status);
            } else throw new OrderNotFoundException("Заказ не найден!");
    }

    @Transactional
    public void changeAddress(Long orderId, String address) {
        orderRepository.save(getById(getById(orderId).setAddress(address)));
    }

    @Transactional
    public void changeUser(@Positive Long orderId, String userName) {
        Optional<Order> order = getById(orderId);
            if (order.isPresent()) {
                order.get().setExecutorName(userName);
                orderRepository.save(order.get());
            }
    }

}
