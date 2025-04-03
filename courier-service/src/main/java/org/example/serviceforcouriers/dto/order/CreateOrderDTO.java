package org.example.serviceforcouriers.dto.order;

import lombok.Getter;
import lombok.Setter;
import org.example.serviceforcouriers.enums.Status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class CreateOrderDTO {
    private Long orderId;
    private String product;
    private String customerName;
    private String address;
    private OffsetDateTime offsetDateTime;
    private BigDecimal price;
    private Status status;
}