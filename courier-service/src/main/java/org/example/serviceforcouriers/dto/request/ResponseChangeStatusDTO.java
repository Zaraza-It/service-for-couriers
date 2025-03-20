package org.example.serviceforcouriers.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.example.serviceforcouriers.entity.RequestChangeStatus;
import org.example.serviceforcouriers.enums.Status;

@Getter
@Setter
public class ResponseChangeStatusDTO {

    private Long requestId;

    private Long orderId;

    private Status nowStatus;

    private Status desiredStatus;

    private boolean accept;

    public ResponseChangeStatusDTO(RequestChangeStatus requestChangeStatus) {
        this.requestId = requestChangeStatus.getRequestId();
        this.orderId = requestChangeStatus.getOrderId();
        this.nowStatus = requestChangeStatus.getNowStatus();
        this.desiredStatus = requestChangeStatus.getDesiredStatus();
        this.accept = requestChangeStatus.isAccept();
    }
}
