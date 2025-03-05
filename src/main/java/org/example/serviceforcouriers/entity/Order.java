package org.example.serviceforcouriers.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    private Long id;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private Character purchasesPrice;

    private Character purchasesSell;

    private boolean soldStatus;


}


