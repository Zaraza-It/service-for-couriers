package org.example.serviceforcouriers.service;

import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.dto.order.CreateOrderDTO;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.entity.User;
import org.example.serviceforcouriers.enums.Status;
import org.example.serviceforcouriers.exceptions.OrderNotFoundException;
import org.example.serviceforcouriers.exceptions.UserNotFoundException;
import org.example.serviceforcouriers.repository.OrderRepository;
import org.example.serviceforcouriers.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.OffsetDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final JwtService jwtService;

    public void create(String token, @RequestBody CreateOrderDTO order) {
        if (nonNull(jwtService.getAccessClaims(token).getSubject())) {
            orderRepository.save(new Order(
                    order.getOrderId(),
                    order.getProduct(),
                    order.getCustomerName(),
                    order.getAddress(),
                    OffsetDateTime.now(),
                    order.getPrice(),
                    order.getStatus()));
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Аккаунт с данным id не был найден"));
    }

    @Transactional
    public void changeStatus(Long orderId, Status status) {
        getById(orderId).setStatus(status);
    }

    @Transactional
    public void changeAddress(Long orderId, String address) {
        getById(orderId).setAddress(address);
    }

    @Transactional
    public void changeUser(Long orderId, User user) {
        getById(orderId).setUser(user);
    }

}
