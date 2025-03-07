package org.example.market.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {

        private Long productId;

        private String productName;

        private Long quantity;

        private BigDecimal productPrice;

        public ProductResponseDTO(ProductResponseDTO productResponseDTO) {

        }
}
