package org.example.serviceforcouriers.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.example.serviceforcouriers.enums.Status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String product;

    private String customerName;

    @Nullable
    private String executorName;

    private String address;

    private OffsetDateTime offsetDateTime;

    private BigDecimal price;

    private Status status;


}