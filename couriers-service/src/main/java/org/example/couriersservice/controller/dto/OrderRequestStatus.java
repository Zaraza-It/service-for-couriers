package org.example.couriersservice.controller.dto;

import lombok.Data;

@Data
public class OrderRequestStatus {

    private Long productId;

    private boolean soldStatus;


}
