package org.example.market.controller;

import lombok.RequiredArgsConstructor;
import org.example.market.controller.dto.ProductResponseDTO;
import org.example.market.service.MarketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final MarketService marketService;

    @GetMapping("/products")
    public List<ProductResponseDTO> getAllProducts() {
        List<ProductResponseDTO> products = marketService.getAllProducts();

        if (products == null) {
            return Collections.emptyList();
        }

        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }


}