package org.example.serviceforcouriers.controller.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderRequestDTO {

    private String product;
    private String customerName;
    private String executorName;
    private String address;
    private BigDecimal purchasesPrice;
    private BigDecimal purchasesSell;



}