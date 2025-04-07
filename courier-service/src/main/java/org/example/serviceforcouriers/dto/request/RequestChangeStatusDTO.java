package org.example.serviceforcouriers.dto.request;

import org.example.serviceforcouriers.enums.Status;

public record RequestChangeStatusDTO(Long orderId,
                                     Status nowStatus,
                                     Status desiredStatus,
                                     boolean accept) {
}
