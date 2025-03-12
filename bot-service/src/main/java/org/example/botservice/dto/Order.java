package org.example.botservice.dto;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product")
    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private OffsetDateTime offsetDateTime;

    private BigDecimal purchasesPrice;

    private BigDecimal purchasesSell;

    private String soldStatus;
}
