package org.example.serviceforcouriers.controller.dto;

import lombok.Data;

@Data
public class OrderRequestStatus {

    private Long productId;

    private boolean soldStatus;


}
