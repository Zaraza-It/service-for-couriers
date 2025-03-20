package org.example.serviceforcouriers.dto.order;

import lombok.Data;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.enums.Status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class OrderResponseDTO {

    private Long orderId;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private OffsetDateTime offsetDateTime;

    private BigDecimal price;

    private Status status;

    public OrderResponseDTO(Order order) {
    }

}
