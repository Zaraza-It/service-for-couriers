package org.example.serviceforcouriers.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.example.serviceforcouriers.enums.Status;

@Getter
@Setter
public class CreateRequestChangeStatusDTO {

    private Long requestId;

    private Long orderId;

    private Status nowStatus;

    private Status desiredStatus;

    private boolean accept;

}
