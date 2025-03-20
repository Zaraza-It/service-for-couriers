package org.example.market.dto.request;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {

    private String productName;
    private String categoryProduct;
    @Positive
    private Integer quantity;

    @Positive
    @Null
    private BigDecimal price;


}
