package org.example.market.dto.response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ResponseSoldProduct {

    @Column(name = "quantity")
    private int quantity;

}
