package org.example.serviceforcouriers.controller.dto;

import lombok.Data;
import org.example.serviceforcouriers.entity.Order;

import java.math.BigDecimal;

@Data
public class OrderResponse {

    private Long productId;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private BigDecimal purchasesSell;

    private boolean soldStatus;

    public OrderResponse(Order orderById) {
    }
}
