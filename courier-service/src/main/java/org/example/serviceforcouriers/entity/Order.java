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

    @Column(name = "product")
    private String product;

    @Column(name = "customer_name")
    private String customerName;

    @Nullable
    @Column(name = "executor_name")
    private String executorName;

    @Column(name = "address")
    private String address;

    @Column(name = "executor_name")
    private OffsetDateTime offsetDateTime;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}