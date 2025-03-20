package org.example.serviceforcouriers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

