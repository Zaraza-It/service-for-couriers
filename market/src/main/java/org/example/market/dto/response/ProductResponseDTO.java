package org.example.market.dto.response;

import lombok.*;
import org.example.market.entity.Product;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

        private String productName;

        private Long quantity;

        private BigDecimal productPrice;


        public ProductResponseDTO(Product product) {
        }
}
