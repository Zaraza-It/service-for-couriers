package org.example.serviceforcouriers.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private BigDecimal purchasesPrice;

    private BigDecimal purchasesSell;

    @Nullable
    private Boolean soldStatus;

    public Order(String product, String customerName, String executorName, String address, BigDecimal purchasesPrice, BigDecimal purchasesSell) {
        this(product, customerName, executorName, address, purchasesPrice, purchasesSell, null);
    }

    public Order(String product, String customerName, String executorName, String address, BigDecimal purchasesPrice, BigDecimal purchasesSell, Boolean soldStatus) {
        this.product = product;
        this.customerName = customerName;
        this.executorName = executorName;
        this.address = address;
        this.purchasesPrice = purchasesPrice;
        this.purchasesSell = purchasesSell;
        this.soldStatus = soldStatus;
    }
}