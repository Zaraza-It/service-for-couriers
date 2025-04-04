package org.example.serviceforcouriers.dto.order;

import org.example.serviceforcouriers.enums.Status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record OrderResponseDTO(
        Long orderId,
        String product,
        String customerName,
        String executorName,
        String address,
        OffsetDateTime offsetDateTime,
        BigDecimal price,
        Status status
) {
}