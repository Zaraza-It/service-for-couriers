package org.example.serviceforcouriers.dto.request;

import org.example.serviceforcouriers.enums.Status;

public record ResponseChangeStatusDTO(Long requestId,
                                      Long orderId,
                                      Status nowStatus,
                                      Status desiredStatus,
                                      boolean accept) {

}
