package org.example.serviceforcouriers.dto.order;

import org.example.serviceforcouriers.entity.User;
import org.example.serviceforcouriers.enums.Status;

public record ChangeOrderDTO(
        Long orderId,
        String executorName,
        String address,
        Status status,
        User user) {
}
