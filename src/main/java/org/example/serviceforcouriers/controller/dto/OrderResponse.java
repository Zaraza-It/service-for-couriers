package org.example.serviceforcouriers.controller.dto;

import lombok.Data;

@Data
public class OrderResponse {

    private Long productId;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private Character purchasesPrice;

    private Character purchasesSell;

    private boolean soldStatus;

}
