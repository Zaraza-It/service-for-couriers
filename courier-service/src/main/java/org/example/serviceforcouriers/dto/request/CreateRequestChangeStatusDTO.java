package org.example.serviceforcouriers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.example.serviceforcouriers.enums.Status;

@Getter
@Setter
public class CreateRequestChangeStatusDTO {

    @Positive
    private Long requestId;

    @Positive
    private Long orderId;

    @NotBlank
    private Status nowStatus;

    private Status desiredStatus;

    private boolean accept;

}
