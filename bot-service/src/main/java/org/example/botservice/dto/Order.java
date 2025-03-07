package org.example.botservice.dto;


import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
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
