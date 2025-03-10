package org.example.serviceforcouriers.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
public class OrderRequestDTO {

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private OffsetDateTime offsetDateTime;

    private BigDecimal purchasesPrice;

    private BigDecimal purchasesSell;

    public OrderRequestDTO(String product, String customerName, String executorName, String address, OffsetDateTime offsetDateTime, BigDecimal purchasesPrice, BigDecimal purchasesSell) {
        this.product = product;
        this.customerName = customerName;
        this.executorName = executorName;
        this.address = address;
        this.offsetDateTime = offsetDateTime;
        this.purchasesPrice = purchasesPrice;
        this.purchasesSell = purchasesSell;
    }
}