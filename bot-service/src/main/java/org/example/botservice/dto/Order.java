package org.example.botservice.dto;


import jakarta.annotation.Nullable;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Order {

    private Long id;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private BigDecimal purchasesPrice;

    private BigDecimal purchasesSell;

    @Nullable
    private Boolean soldStatus;
}
