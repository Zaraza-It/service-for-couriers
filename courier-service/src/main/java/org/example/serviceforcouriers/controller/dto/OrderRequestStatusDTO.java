package org.example.serviceforcouriers.controller.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class OrderRequestStatusDTO {

    @Getter
    private boolean soldStatus;
}
