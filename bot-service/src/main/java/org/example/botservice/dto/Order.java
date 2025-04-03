package org.example.botservice.dto;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product")
    private String product;

    @Column(name = "customer_name")
    private String customerName;

    @Nullable
    @Column(name = "callback")
    private UUID callback;

    @Column(name = "executor_name")
    private String executorName;

    @Column(name = "address")
    private String address;

    @Column(name = "offset_date_time")
    private OffsetDateTime offsetDateTime; //название не понятное, непонятно за что это время отвечает

    @Column(name = "purchases_price")
    private BigDecimal purchasesPrice;

    @Column(name = "purchases_sell")
    private BigDecimal purchasesSell;

    @Column(name = "sold_status")
    private String soldStatus;
}