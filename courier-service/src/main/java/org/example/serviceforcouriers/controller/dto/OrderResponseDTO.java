package org.example.serviceforcouriers.controller.dto;


import lombok.*;
import org.example.serviceforcouriers.entity.Order;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private OffsetDateTime offsetDateTime;

    private BigDecimal purchasesPrice;

    private BigDecimal purchasesSell;

    public OrderResponseDTO (Order order) {
        id = order.getOrderId();
        product = order.getProduct();
        customerName = order.getCustomerName();
        executorName = order.getExecutorName();
        address = order.getAddress();
        offsetDateTime = order.getOffsetDateTime();
        purchasesPrice = order.getPurchasesPrice();
        purchasesSell = order.getPurchasesSell();
    }

}