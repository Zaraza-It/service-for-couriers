package org.example.serviceforcouriers.dto.order;

import lombok.Getter;
import lombok.Setter;
import org.example.serviceforcouriers.entity.User;
import org.example.serviceforcouriers.enums.Status;

@Setter
@Getter
public class ChangeOrderDTO {

    private Long orderId;

    private String executorName;

    private String address;

    private Status status;

    private User user;

}
