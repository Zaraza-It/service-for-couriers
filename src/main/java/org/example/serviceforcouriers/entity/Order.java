package org.example.serviceforcouriers.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private Character purchasesPrice;

    private Character purchasesSell;

    @Nullable
    private boolean soldStatus;

}


