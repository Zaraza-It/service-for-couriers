package org.example.market.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ProductRequest {
    @NotEmpty
    private String productName;
    @NotEmpty
    private String categoryProduct;
    private Long quantity;
    private BigDecimal productPrice;
}
