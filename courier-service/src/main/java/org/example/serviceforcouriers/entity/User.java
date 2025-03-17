package org.example.serviceforcouriers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.serviceforcouriers.dto.CreateOrderDTO;
import org.example.serviceforcouriers.dto.RequestUserDTO;
import org.springframework.lang.Nullable;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Set<Order> order;

}

