package org.example.serviceforcouriers.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.serviceforcouriers.enums.Status;

@Entity
@Data
@NoArgsConstructor
@Table(name = "requests_status")
public class RequestChangeStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "now_status")
    private Status nowStatus;

    @Column(name = "desired_status")
    private Status desiredStatus;

    @Column(name = "accept")
    private boolean accept; //по хорошему нужно назвать accepted

    public RequestChangeStatus(Long orderId, Status nowStatus, Status desiredStatus, boolean accept) {
        this.orderId = orderId;
        this.nowStatus = nowStatus;
        this.desiredStatus = desiredStatus;
        this.accept = accept;
    }
}
