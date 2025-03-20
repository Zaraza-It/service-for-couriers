package org.example.serviceforcouriers.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.serviceforcouriers.enums.Status;

@Entity
@Data
@Table(name = "requests_status")
public class RequestChangeStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    private Long orderId;

    private Status nowStatus;

    private Status desiredStatus;

    private boolean accept;

    public RequestChangeStatus(Long orderId, Status nowStatus, Status desiredStatus, boolean accept) {
        this.orderId = orderId;
        this.nowStatus = nowStatus;
        this.desiredStatus = desiredStatus;
        this.accept = accept;
    }
}
