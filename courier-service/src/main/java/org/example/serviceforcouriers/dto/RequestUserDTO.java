package org.example.serviceforcouriers.dto;

import org.example.serviceforcouriers.entity.Order;

import java.util.Set;

public record RequestUserDTO(
        Long id,
        String username,
        String password,
        String email,
        Set<Order> order
) {

}
