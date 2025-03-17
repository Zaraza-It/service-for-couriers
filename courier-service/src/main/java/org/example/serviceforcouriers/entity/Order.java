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

    private String address;

    private OffsetDateTime offsetDateTime;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Nullable
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    @JoinColumn(name = "username",referencedColumnName = "username")
    private User user;

    public Order(Long orderId, String product, String customerName, String address, OffsetDateTime offsetDateTime,
                 BigDecimal price, Status status) {
        this.orderId = orderId;
        this.product = product;
        this.customerName = customerName;
        this.address = address;
        this.offsetDateTime = offsetDateTime;
        this.price = price;
        this.status = status;
    }
}