package org.example.serviceforcouriers.controller.dto;

import lombok.Data;

@Data
public class OrderRequest {

    private Long productId;

    private boolean soldStatus;

}
