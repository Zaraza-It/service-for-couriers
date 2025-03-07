package org.example.serviceforcouriers.controller.dto;

import lombok.Data;
import org.example.serviceforcouriers.entity.Order;

import java.math.BigDecimal;

@Data
public class OrderResponseDTO {

    private Long productId;
    private String product;
    private String customerName;
    private String executorName;
    private String address;
    private BigDecimal purchasesSell;
    private boolean soldStatus;

    public OrderResponseDTO(Long productId, String product, String customerName, String executorName, String address, BigDecimal purchasesSell, boolean soldStatus) {
        this.productId = productId;
        this.product = product;
        this.customerName = customerName;
        this.executorName = executorName;
        this.address = address;
        this.purchasesSell = purchasesSell;
        this.soldStatus = soldStatus;
    }

    public OrderResponseDTO(Order order) {
        this.productId = order.getId();
        this.product = order.getProduct();
        this.customerName = order.getCustomerName();
        this.executorName = order.getExecutorName();
        this.address = order.getAddress();
        this.purchasesSell = order.getPurchasesSell();
        this.soldStatus = order.getSoldStatus() != null && order.getSoldStatus();
    }
}