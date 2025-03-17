package org.example.serviceforcouriers.dto;

import org.example.serviceforcouriers.entity.Order;

import java.util.Set;

public class RequestUserDTO {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Set<Order> order;

}
