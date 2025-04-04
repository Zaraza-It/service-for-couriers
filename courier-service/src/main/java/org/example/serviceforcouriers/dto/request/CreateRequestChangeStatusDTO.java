package org.example.serviceforcouriers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.example.serviceforcouriers.enums.Status;

public record CreateRequestChangeStatusDTO(
        @Positive Long requestId,
        @Positive Long orderId,
        @NotBlank Status nowStatus,
        Status desiredStatus,
        boolean accept
) {
}
