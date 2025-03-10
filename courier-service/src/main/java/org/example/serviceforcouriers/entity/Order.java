package org.example.serviceforcouriers.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Entity
@ToString
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private OffsetDateTime offsetDateTime;

    private BigDecimal purchasesPrice;

    private BigDecimal purchasesSell;

    private String soldStatus;

    public Order(String product, String customerName, String executorName, String address,
                 OffsetDateTime offsetDateTime, BigDecimal purchasesPrice, BigDecimal purchasesSell, String soldStatus) {
        this.product = product;
        this.customerName = customerName;
        this.executorName = executorName;
        this.address = address;
        this.offsetDateTime = offsetDateTime;
        this.purchasesPrice = purchasesPrice;
        this.purchasesSell = purchasesSell;
        this.soldStatus = soldStatus;
    }

    public Order(Long orderId, String product, String customerName, String executorName, String address, BigDecimal purchasesPrice, BigDecimal purchasesSell, String soldStatus) {
        this.orderId = orderId;
        this.product = product;
        this.customerName = customerName;
        this.executorName = executorName;
        this.address = address;
        this.purchasesPrice = purchasesPrice;
        this.purchasesSell = purchasesSell;
        this.soldStatus = soldStatus;
    }
}