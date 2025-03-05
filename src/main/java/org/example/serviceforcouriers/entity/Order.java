package org.example.serviceforcouriers.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private BigDecimal purchasesPrice;

    private BigDecimal purchasesSell;

    @Nullable
    private boolean soldStatus;

    public Order(String product, String customerName, String executorName, String address, BigDecimal purchasesPrice, BigDecimal purchasesSell) {
        this.product = product;
        this.customerName = customerName;
        this.executorName = executorName;
        this.address = address;
        this.purchasesPrice = purchasesPrice;
        this.purchasesSell = purchasesSell;
    }
}


